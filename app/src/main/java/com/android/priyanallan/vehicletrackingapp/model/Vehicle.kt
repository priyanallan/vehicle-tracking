package com.android.priyanallan.vehicletrackingapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Vehicle(
    @SerializedName("id") val id: Int,
    @SerializedName("licensePlate") val licensePlateNumber: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("model") val model: String,
    @SerializedName("nickname") val nickname: String?,
    @SerializedName("lastPosition") val location: VehicleLocation
) : Serializable