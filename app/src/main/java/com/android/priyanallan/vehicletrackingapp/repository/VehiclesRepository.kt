package com.android.priyanallan.vehicletrackingapp.repository

import android.util.Log
import com.android.priyanallan.vehicletrackingapp.model.Vehicle
import com.android.priyanallan.vehicletrackingapp.remote.RetrofitService
import com.android.priyanallan.vehicletrackingapp.utils.Constants
import org.koin.core.KoinComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VehiclesRepository : KoinComponent {

    private val TAG = VehiclesRepository::class.java.simpleName
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.VEHICLES_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var retrofitService: RetrofitService

    init {
        retrofitService = retrofit.create(RetrofitService::class.java)

    }

    fun getVehicles(callback: NetworkStatusCallback) {

        retrofitService.getVehicles().enqueue(object : Callback<List<Vehicle>> {
            override fun onFailure(call: Call<List<Vehicle>>, t: Throwable) {
                callback.onDataLoadingFailure(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<List<Vehicle>>,
                response: Response<List<Vehicle>>
            ) {
                if (response.isSuccessful) {
                    callback.onDataLoadingSuccess(response.body())
                } else {
                    Log.e(TAG, "Error getting response!")
                }
            }
        })
    }

    interface NetworkStatusCallback {
        fun onDataLoadingSuccess(vehiclesList: List<Vehicle>?)
        fun onDataLoadingFailure(networkState: String)
    }
}