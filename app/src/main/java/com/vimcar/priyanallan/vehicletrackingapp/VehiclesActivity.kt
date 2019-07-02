package com.vimcar.priyanallan.vehicletrackingapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.vimcar.priyanallan.vehicletrackingapp.adapter.VehiclesAdapter
import com.vimcar.priyanallan.vehicletrackingapp.model.Vehicle
import com.vimcar.priyanallan.vehicletrackingapp.viewmodel.VehiclesViewModel
import kotlinx.android.synthetic.main.activity_home.*

class VehiclesActivity : AppCompatActivity() {

    lateinit var vehiclesViewModel: VehiclesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        vehicles_recyclerview.layoutManager = LinearLayoutManager(this)

        vehiclesViewModel = ViewModelProviders.of(this).get(VehiclesViewModel::class.java)

        vehiclesViewModel.vehiclesList.observe(this,
            Observer<List<Vehicle>> { listOfVehicles ->
                listOfVehicles?.let { vehicles ->
                    vehicles_recyclerview.adapter = VehiclesAdapter(vehicles)
                }
            })
    }
}