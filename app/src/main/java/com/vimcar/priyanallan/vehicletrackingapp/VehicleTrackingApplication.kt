package com.vimcar.priyanallan.vehicletrackingapp

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.vimcar.priyanallan.vehicletrackingapp.repository.VehiclesRepository
import com.vimcar.priyanallan.vehicletrackingapp.viewmodel.VehiclesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class VehicleTrackingApplication : Application() {

    private val PREFERENCES_FILE_KEY = "com.example.settings_preferences"
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

    private val sharedPreferencesModule = module {
        single {
            androidApplication().getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@VehicleTrackingApplication)
            modules(
                listOf(
                    vehiclesRepository,
                    vehiclesViewmodel,
                    sharedPreferencesModule
                )
            )
        }
    }
}