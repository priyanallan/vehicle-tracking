package com.vimcar.priyanallan.vehicletrackingapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Log
import com.vimcar.priyanallan.vehicletrackingapp.utils.Constants
import com.vimcar.priyanallan.vehicletrackingapp.model.Vehicle
import com.vimcar.priyanallan.vehicletrackingapp.remote.RetrofitService
import org.koin.core.KoinComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VehiclesRepository : KoinComponent {

    private val TAG = VehiclesRepository::class.java.simpleName

    fun getVehicles(): LiveData<List<Vehicle>>{

        val vehiclesObservable: MutableLiveData<List<Vehicle>> = MutableLiveData()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RetrofitService::class.java)

        service.getVehicles().enqueue(object : Callback<List<Vehicle>> {
            override fun onFailure(call: Call<List<Vehicle>>, t: Throwable) {
                //TODO: Add error handling
                Log.e("VehiclesActivity", "Error in retrofit ${t.localizedMessage}")
            }

            override fun onResponse(
                call: Call<List<Vehicle>>,
                response: Response<List<Vehicle>>
            ) {
                if (response.isSuccessful) {
                    vehiclesObservable.postValue(response.body())
                } else {
                    Log.e(TAG, "Error getting response!")
                }
            }
        })
        return vehiclesObservable
    }
}