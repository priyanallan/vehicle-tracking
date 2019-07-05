package com.vimcar.priyanallan.vehicletrackingapp.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var vehicle: Vehicle
    private val ZOOM_LEVEL = 15.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val bundle = intent.extras
        vehicle = bundle?.getSerializable(VEHICLE_LOCATION) as Vehicle

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
        vehicle.apply {
            val locationOfVehicle = LatLng(location.latitude, location.longitude)
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
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationOfVehicle))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationOfVehicle, ZOOM_LEVEL))
        }
    }
}