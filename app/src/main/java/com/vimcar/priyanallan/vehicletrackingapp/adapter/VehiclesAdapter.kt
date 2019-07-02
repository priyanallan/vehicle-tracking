package com.vimcar.priyanallan.vehicletrackingapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vimcar.priyanallan.vehicletrackingapp.model.Vehicle
import com.vimcar.priyanallan.vehicletrackingapp.R
import kotlinx.android.synthetic.main.list_item_vehicle.view.*

class VehiclesAdapter(val vehicles: List<Vehicle>) :
    RecyclerView.Adapter<VehiclesAdapter.ViewHolder>() {

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
            carBrand.text = vehicles[position].brand
            carModel.text = vehicles[position].model
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}