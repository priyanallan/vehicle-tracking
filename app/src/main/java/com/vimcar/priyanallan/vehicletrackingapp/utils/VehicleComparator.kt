package com.vimcar.priyanallan.vehicletrackingapp.utils

import com.vimcar.priyanallan.vehicletrackingapp.model.Vehicle

class VehicleComparator : Comparator<Vehicle> {
    override fun compare(o1: Vehicle, o2: Vehicle): Int {
        val brandCompare = o1.brand.compareTo(o2.brand, true)
        val modelCompare = o1.model.compareTo(o2.model, true)
        val licensePlateCompare = o1.licensePlateNumber.compareTo(o2.licensePlateNumber, true)

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