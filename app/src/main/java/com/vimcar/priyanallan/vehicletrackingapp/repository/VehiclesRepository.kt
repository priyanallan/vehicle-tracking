package com.vimcar.priyanallan.vehicletrackingapp.repository

import android.util.Log
import com.vimcar.priyanallan.vehicletrackingapp.model.Vehicle
import com.vimcar.priyanallan.vehicletrackingapp.remote.RetrofitService
import com.vimcar.priyanallan.vehicletrackingapp.utils.Constants
import org.koin.core.KoinComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VehiclesRepository : KoinComponent {

    private val TAG = VehiclesRepository::class.java.simpleName

    fun getVehicles(callback: NetworkStatusCallback) {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RetrofitService::class.java)

        service.getVehicles().enqueue(object : Callback<List<Vehicle>> {
            override fun onFailure(call: Call<List<Vehicle>>, t: Throwable) {
                callback.onNetworkStatus(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<List<Vehicle>>,
                response: Response<List<Vehicle>>
            ) {
                if (response.isSuccessful) {
                    callback.onDataLoading(response.body())
                } else {
                    Log.e(TAG, "Error getting response!")
                }
            }
        })
    }

    interface NetworkStatusCallback {
        fun onDataLoading(content: List<Vehicle>?)
        fun onNetworkStatus(networkState: String)
    }
}