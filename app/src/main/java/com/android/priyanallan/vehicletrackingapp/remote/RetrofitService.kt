package com.android.priyanallan.vehicletrackingapp.remote

import com.android.priyanallan.vehicletrackingapp.model.Vehicle
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {

    @GET("vehicles.json")
    fun getVehicles(): Call<List<Vehicle>>
}