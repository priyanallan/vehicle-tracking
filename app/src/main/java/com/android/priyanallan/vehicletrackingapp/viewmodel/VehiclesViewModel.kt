package com.android.priyanallan.vehicletrackingapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.priyanallan.vehicletrackingapp.model.Vehicle
import com.android.priyanallan.vehicletrackingapp.repository.VehiclesRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class VehiclesViewModel : ViewModel(), KoinComponent, VehiclesRepository.NetworkStatusCallback {

    private val vehiclesRepository by inject<VehiclesRepository>()

    var vehiclesList: MutableLiveData<List<Vehicle>> = MutableLiveData()
    var onDataLoadingStatus: MutableLiveData<String> = MutableLiveData()

    override fun onDataLoadingSuccess(vehicles: List<Vehicle>?) {
        vehiclesList.postValue(vehicles)
    }

    override fun onDataLoadingFailure(networkState: String) {
        onDataLoadingStatus.value = networkState
    }

    fun getVehicles() {
        vehiclesRepository.getVehicles(this)
    }

}