package com.supremehyo.locationsns.Adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.supremehyo.locationsns.DTO.ContentDTO
import com.supremehyo.locationsns.DTO.EventDTO
import com.supremehyo.locationsns.DTO.Place
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.Util.RxEventBusHelper
import com.supremehyo.locationsns.Util.TimeCal
import com.supremehyo.locationsns.View.DetailContentActivity
import java.time.LocalDateTime

class MyContentRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<MyContentRecyclerAdapter.ViewHolder>() {

    var timecal : TimeCal = TimeCal()
    lateinit var contentlist : ArrayList<EventDTO>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_item_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = contentlist.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contentlist[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var content_cl : ConstraintLayout = itemView.findViewById(R.id.content_cl)
        var title_tv: TextView = itemView.findViewById(R.id.title_tv)
        var date_tv: TextView = itemView.findViewById(R.id.date_time_tv)
        var chat_count_tv: TextView = itemView.findViewById(R.id.chat_count_tv)
        var location_tv: TextView = itemView.findViewById(R.id.location_tv)


        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: EventDTO) {
            title_tv.text = item.title
            Log.v("mymy" , item.title)
            date_tv.text = timecal.convert_date(item.start_time , item.end_time)
            chat_count_tv.text = item.chat_count.toString()

            //시간 계산 + 장소 이름
            val currentDateTime = LocalDateTime.now()
            var list : List<String> = item.create_time!!.split("+")
            val createDataTime = LocalDateTime.parse(list.get(0))
            timecal.compareHour(createDataTime,currentDateTime)
            location_tv.text = item.location_name +" · "+timecal.compareHour(createDataTime,currentDateTime)

            content_cl.setOnClickListener {
                val intent = Intent(context , DetailContentActivity::class.java)
                intent.putExtra("content", item)
                startActivity(context ,intent,null)
            }

        }
    }


}