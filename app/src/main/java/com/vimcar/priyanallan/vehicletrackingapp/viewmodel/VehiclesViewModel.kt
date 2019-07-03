package com.vimcar.priyanallan.vehicletrackingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vimcar.priyanallan.vehicletrackingapp.model.Vehicle
import com.vimcar.priyanallan.vehicletrackingapp.repository.VehiclesRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class VehiclesViewModel : ViewModel(), KoinComponent {

    private val vehiclesRepository by inject<VehiclesRepository>()

    val vehiclesList: LiveData<List<Vehicle>> = vehiclesRepository.getVehicles()
}