package com.vimcar.priyanallan.vehicletrackingapp.ui

import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vimcar.priyanallan.vehicletrackingapp.R
import com.vimcar.priyanallan.vehicletrackingapp.model.Vehicle
import com.vimcar.priyanallan.vehicletrackingapp.utils.Constants.Companion.VEHICLE_LOCATION
import com.vimcar.priyanallan.vehicletrackingapp.utils.UtilFunctions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var vehicle: Vehicle
    private val ZOOM_LEVEL = 15.0f
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val TAG = MapsActivity::class.java.simpleName
    private var lastLocation: Location? = null
    private var isCurrentDeviceLocation = false
    private var isFirstMapOpen = true

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val bundle = intent.extras
        vehicle = bundle?.getSerializable(VEHICLE_LOCATION) as Vehicle

        //On Show vehicle Info button click listener
        showVehicleInfoButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vehicleName.visibility = View.VISIBLE
                vehicleLicensePlateNr.visibility = View.VISIBLE
                vehicleName.text = getString(R.string.brand_model, vehicle.brand, vehicle.model)
                vehicleLicensePlateNr.text = vehicle.licensePlateNumber
            } else {
                vehicleName.visibility = View.GONE
                vehicleLicensePlateNr.visibility = View.GONE
            }
        }

        //On Return to Device Location button click listener
        goToVehicleLocation.setOnClickListener {

            displayVehicleOnMap()
            goToVehicleLocation.visibility = View.GONE
            isCurrentDeviceLocation = false
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Display location of vehicle
        displayVehicleOnMap()

        //Request permissions for current device
        requestDeviceLocationPermissions()

        //On show current location button listener
        mMap.setOnMyLocationButtonClickListener(object : GoogleMap.OnMyLocationButtonClickListener {
            override fun onMyLocationButtonClick(): Boolean {

                if (!isCurrentDeviceLocation) {
                    lastLocation?.apply {
                        mMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(latitude, longitude),
                                ZOOM_LEVEL
                            )
                        )
                        goToVehicleLocation.visibility = View.VISIBLE
                        isCurrentDeviceLocation = true
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        } else {
            Log.e(TAG, "Permission Denied, device location cannot be shown")
        }
    }

    private fun requestDeviceLocationPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            } else {
                getLastLocation()
            }
        }
    }

    private fun displayVehicleOnMap() {
        vehicle.apply {
            val locationOfVehicle = LatLng(location.latitude, location.longitude)
            //Don't initialize marker on click of return button to vehicle location
            if (isFirstMapOpen) {
                mMap.addMarker(
                    MarkerOptions().position(locationOfVehicle).title(nickname).icon(
                        BitmapDescriptorFactory.fromBitmap(
                            UtilFunctions.getBitmapFromVectorDrawable(
                                this@MapsActivity,
                                R.drawable.icon_car
                            )
                        )
                    )
                )
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationOfVehicle, ZOOM_LEVEL))
            isFirstMapOpen = false
        }
    }

    private fun getLastLocation() {
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        fusedLocationClient.lastLocation.addOnCompleteListener(
            this
        ) { task ->
            if (task.isSuccessful) {
                lastLocation = task.result
                Log.d(TAG, "Current location is $lastLocation")
            }
        }
    }
}