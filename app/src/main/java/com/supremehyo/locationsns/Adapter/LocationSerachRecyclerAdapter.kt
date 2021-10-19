package com.supremehyo.locationsns.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.supremehyo.locationsns.DTO.Place
import com.supremehyo.locationsns.DTO.ResultSearchKeyword
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.Util.RxEventBusHelper
import org.w3c.dom.Text

class LocationSerachRecyclerAdapter (private val context: Context) : RecyclerView.Adapter<LocationSerachRecyclerAdapter.ViewHolder>() {

    lateinit var datas : List<Place>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_locationitem_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val location_name_tv: TextView = itemView.findViewById(R.id.location_name_tv)
        private val location_detail_tv: TextView = itemView.findViewById(R.id.location_detail_tv)
        private val location_search_cl : ConstraintLayout = itemView.findViewById(R.id.location_search_cl)

        fun bind(item: Place) {
            location_name_tv.text = item.place_name
            location_detail_tv.text = item.address_name
            location_search_cl.setOnClickListener {
                var temp = listOf<String>(item.place_name ,item.address_name)
                RxEventBusHelper.select_Map_address(temp)
            }

        }
    }


}