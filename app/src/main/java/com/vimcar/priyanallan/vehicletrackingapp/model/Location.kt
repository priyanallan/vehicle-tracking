package com.vimcar.priyanallan.vehicletrackingapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location(
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lng")
    val longitude: Double
) : Serializable