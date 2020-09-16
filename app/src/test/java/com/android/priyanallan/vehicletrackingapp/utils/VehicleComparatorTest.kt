package com.android.priyanallan.vehicletrackingapp.utils

import com.android.priyanallan.vehicletrackingapp.model.Vehicle
import com.android.priyanallan.vehicletrackingapp.model.VehicleLocation
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class VehicleComparatorTest {

    val defaultVehicleLocation: VehicleLocation = VehicleLocation(13.45, 52.13)
    val defaultVehicle: Vehicle = Vehicle(1, "X Y 100", "Puegot", "B1", "", defaultVehicleLocation)
    lateinit var classUnderTest: VehicleComparator

    @Before
    fun setUp() {
        classUnderTest = VehicleComparator()
    }

    @Test
    fun vehicleComparisonByBrand() {
        val vehicle1 = defaultVehicle.copy(brand = "Alto")
        val vehicle2 = defaultVehicle.copy(brand = "Mercedes")

        val result = classUnderTest.compare(vehicle1, vehicle2)

        Assert.assertTrue("Brand Alto should come before Mercedes", result < 0)
    }

    @Test
    fun vehicleComparisonByModel() {
        val vehicle1 = defaultVehicle.copy(model = "Z1")
        val vehicle2 = defaultVehicle.copy(model = "B123")

        val result = classUnderTest.compare(vehicle1, vehicle2)

        Assert.assertTrue("Model Puegot Z1 should come after Puegot B123", result > 0)
    }

    @Test
    fun vehicleComparisonByLicensePlate() {
        val vehicle1 = defaultVehicle.copy(licensePlateNumber = "US 1 23")
        val vehicle2 = defaultVehicle.copy(licensePlateNumber = "UX 3 4")

        val result = classUnderTest.compare(vehicle1, vehicle2)

        Assert.assertTrue("License of Puegot US 1 23 should come before Puegot UX 3 4", result < 0)
    }

    @Test
    fun vehicleComparisonByBrandAndModel() {
        val vehicle1 = defaultVehicle.copy(brand = "Audi", model = "AG")
        val vehicle2 = defaultVehicle.copy(brand = "Jeep", model = "AG")

        val result = classUnderTest.compare(vehicle1, vehicle2)

        Assert.assertTrue("Audi should come before Jeep", result < 0)
    }

    @Test
    fun vehicleComparisonByBrandAndLicensePlate() {
        val vehicle1 = defaultVehicle.copy(brand = "Spyker", licensePlateNumber = "MC 1 23")
        val vehicle2 = defaultVehicle.copy(brand = "Polski", licensePlateNumber = "MC 1 23")

        val result = classUnderTest.compare(vehicle1, vehicle2)

        Assert.assertTrue("Spyker should come after Polski", result > 0)
    }

}