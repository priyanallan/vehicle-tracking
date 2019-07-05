package com.vimcar.priyanallan.vehicletrackingapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.vimcar.priyanallan.vehicletrackingapp.R
import com.vimcar.priyanallan.vehicletrackingapp.adapter.VehicleOnClickListener
import com.vimcar.priyanallan.vehicletrackingapp.adapter.VehiclesAdapter
import com.vimcar.priyanallan.vehicletrackingapp.model.Vehicle
import com.vimcar.priyanallan.vehicletrackingapp.utils.Constants.Companion.VEHICLE_LOCATION
import com.vimcar.priyanallan.vehicletrackingapp.utils.VehicleComparator
import com.vimcar.priyanallan.vehicletrackingapp.viewmodel.VehiclesViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class VehiclesActivity : AppCompatActivity(), VehicleOnClickListener {

    private val vehiclesViewModel by viewModel<VehiclesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        vehicles_recyclerview.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        vehiclesViewModel.vehiclesList.observe(this,
            Observer<List<Vehicle>> { listOfVehicles ->
                listOfVehicles?.let { vehicles ->
                    vehicles_recyclerview.adapter =
                        VehiclesAdapter(
                            vehicles.sortedWith(VehicleComparator()),
                            this@VehiclesActivity
                        )
                }
            })
    }

    override fun onVehicleClickListener(selectedVehicle: Vehicle) {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra(VEHICLE_LOCATION, selectedVehicle)
        startActivity(intent)
    }
}