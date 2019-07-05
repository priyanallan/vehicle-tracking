package com.vimcar.priyanallan.vehicletrackingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vimcar.priyanallan.vehicletrackingapp.R
import com.vimcar.priyanallan.vehicletrackingapp.model.Vehicle
import kotlinx.android.synthetic.main.list_item_vehicle.view.*

class VehiclesAdapter(
    val vehicles: List<Vehicle>,
    val onVehicleClickListener: VehicleOnClickListener
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<VehiclesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_vehicle, parent, false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.apply {
            vehicles[position].apply {
                vehicleBrand.text = brand
                vehicleModel.text = model
                vehicleLicense.text = licensePlateNumber
            }
        }
        holder.bind(vehicles[position], onVehicleClickListener)
    }


    class ViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(selectedVehicle: Vehicle, listener: VehicleOnClickListener) {

            itemView.setOnClickListener {
                listener.onVehicleClickListener(selectedVehicle)
            }
        }
    }
}

interface VehicleOnClickListener {

    fun onVehicleClickListener(selectedVehicle: Vehicle)
}