package com.vimcar.priyanallan.vehicletrackingapp

import android.app.Application
import com.google.android.gms.location.LocationServices
import com.vimcar.priyanallan.vehicletrackingapp.repository.VehiclesRepository
import com.vimcar.priyanallan.vehicletrackingapp.viewmodel.VehiclesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class VehicleTrackingApplication : Application() {

    //Koin Modules

    private val vehiclesRepository = module {

        single {
            VehiclesRepository()
        }
    }

    private val vehiclesViewmodel = module {
        viewModel {
            VehiclesViewModel()
        }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@VehicleTrackingApplication)
            modules(
                listOf(
                    vehiclesRepository,
                    vehiclesViewmodel
                )
            )
        }
    }
}