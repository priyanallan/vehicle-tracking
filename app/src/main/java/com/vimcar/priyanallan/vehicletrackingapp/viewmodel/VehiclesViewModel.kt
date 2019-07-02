package com.vimcar.priyanallan.vehicletrackingapp.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.vimcar.priyanallan.vehicletrackingapp.model.Vehicle
import com.vimcar.priyanallan.vehicletrackingapp.repository.VehiclesRepository

class VehiclesViewModel : ViewModel() {

    private var vehiclesRepository = VehiclesRepository()

    val vehiclesList: LiveData<List<Vehicle>> = vehiclesRepository.getVehicles()
}