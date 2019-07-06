package com.vimcar.priyanallan.vehicletrackingapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vimcar.priyanallan.vehicletrackingapp.model.Vehicle
import com.vimcar.priyanallan.vehicletrackingapp.repository.VehiclesRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class VehiclesViewModel : ViewModel(), KoinComponent, VehiclesRepository.NetworkStatusCallback {

    private val vehiclesRepository by inject<VehiclesRepository>()

    var vehiclesList: MutableLiveData<List<Vehicle>> = MutableLiveData()
    var onDataLoadingStatus: MutableLiveData<String> = MutableLiveData()

    override fun onDataLoading(vehicles: List<Vehicle>?) {
        vehiclesList.postValue(vehicles)
    }

    override fun onNetworkStatus(networkState: String) {
        onDataLoadingStatus.value = networkState
    }

    fun getVehicles() {
        vehiclesRepository.getVehicles(this)
    }

}