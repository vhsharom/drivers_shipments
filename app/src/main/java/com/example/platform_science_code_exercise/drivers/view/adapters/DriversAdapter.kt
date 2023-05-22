package com.example.platform_science_code_exercise.drivers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.platform_science_code_exercise.drivers.model.Driver
import com.example.platform_science_code_exercise.R
import com.example.platform_science_code_exercise.core.OnItemClickListener

class DriversViewHolder: RecyclerView.ViewHolder {
    val driverName: TextView
    constructor(itemView: View): super(itemView) {
        driverName = itemView.findViewById<TextView>(R.id.tv_driver_name)
    }
}

class DriversAdapter(): RecyclerView.Adapter<DriversViewHolder>() {

    var drivers = ArrayList<Driver>()
    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriversViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_driver, parent, false)
        return DriversViewHolder(view)
    }

    override fun onBindViewHolder(holder: DriversViewHolder, position: Int) {
        val driver = drivers[position]
        holder.driverName.text = driver.name
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(driver)
        }
    }

    override fun getItemCount(): Int {
        return drivers.size
    }
}