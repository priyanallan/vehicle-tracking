package com.vimcar.priyanallan.vehicletrackingapp.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.vimcar.priyanallan.vehicletrackingapp.R
import com.vimcar.priyanallan.vehicletrackingapp.adapter.VehicleOnClickListener
import com.vimcar.priyanallan.vehicletrackingapp.adapter.VehiclesAdapter
import com.vimcar.priyanallan.vehicletrackingapp.model.Vehicle
import com.vimcar.priyanallan.vehicletrackingapp.utils.Constants.Companion.VEHICLE_LOCATION
import com.vimcar.priyanallan.vehicletrackingapp.utils.VehicleComparator
import com.vimcar.priyanallan.vehicletrackingapp.viewmodel.VehiclesViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class VehiclesActivity : AppCompatActivity(), VehicleOnClickListener {

    private val vehiclesViewModel by viewModel<VehiclesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        vehicles_recyclerview.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        vehiclesViewModel.vehiclesList.observe(this,
            Observer<List<Vehicle>> { listOfVehicles ->
                listOfVehicles?.let { vehicles ->
                    progress_bar.visibility = View.GONE
                    vehicles_recyclerview.adapter =
                        VehiclesAdapter(
                            vehicles.sortedWith(VehicleComparator()),
                            this@VehiclesActivity
                        )
                }
            })

        vehiclesViewModel.onDataLoadingStatus.observe(this,
            Observer<String> { networkState ->
                if (!networkState.isNullOrEmpty()) {
                    showNetworkErrorDialog(this)
                }
            })
        getVehiclesData()
    }

    override fun onVehicleClickListener(selectedVehicle: Vehicle) {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra(VEHICLE_LOCATION, selectedVehicle)
        startActivity(intent)
    }

    private fun showNetworkErrorDialog(
        context: Context?
    ) {
        context?.apply {
            val alertDialogBuilder = AlertDialog.Builder(this)

            alertDialogBuilder
                .setMessage(getString(R.string.network_dialog_error_message))
                .setCancelable(false)
                .setPositiveButton(
                    getString(R.string.retry_network_button)
                ) { dialog, _ ->
                    getVehiclesData()
                    dialog.dismiss()
                }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

    }

    private fun getVehiclesData() {
        vehiclesViewModel.getVehicles()
    }
}