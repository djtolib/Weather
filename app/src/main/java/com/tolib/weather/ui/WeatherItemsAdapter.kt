package com.tolib.weather.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tolib.weather.R
import com.tolib.weather.data.model.ItemModel

class WeatherItemsAdapter(private val list: List<ItemModel>) :
    RecyclerView.Adapter<WeatherItemsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]
        Picasso.get().load(item.imageUrl).into(holder.image)
        holder.day.text = item.day
        holder.temperature.text = item.temperature
        holder.minMax.text = item.minMax

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val day: TextView = itemView.findViewById(R.id.day)
        val temperature: TextView = itemView.findViewById(R.id.temperature)
        val image: ImageView = itemView.findViewById(R.id.image)
        val minMax: TextView = itemView.findViewById(R.id.minMaxTv)
    }
}