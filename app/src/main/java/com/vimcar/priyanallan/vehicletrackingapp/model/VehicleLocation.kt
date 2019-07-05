package com.vimcar.priyanallan.vehicletrackingapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VehicleLocation(
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lng")
    val longitude: Double
) : Serializable