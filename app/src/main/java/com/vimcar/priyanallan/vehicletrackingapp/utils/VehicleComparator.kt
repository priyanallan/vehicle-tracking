package com.vimcar.priyanallan.vehicletrackingapp.utils

import com.vimcar.priyanallan.vehicletrackingapp.model.Vehicle

class VehicleComparator : Comparator<Vehicle> {
    override fun compare(vehicle1: Vehicle, vehicle2: Vehicle): Int {
        val brandCompare = vehicle1.brand.compareTo(vehicle2.brand, true)
        val modelCompare = vehicle1.model.compareTo(vehicle2.model, true)
        val licensePlateCompare =
            vehicle1.licensePlateNumber.compareTo(vehicle2.licensePlateNumber, true)

        return if (brandCompare == 0) {
            if (modelCompare == 0) {
                licensePlateCompare
            } else {
                modelCompare
            }
        } else {
            brandCompare
        }
    }
}