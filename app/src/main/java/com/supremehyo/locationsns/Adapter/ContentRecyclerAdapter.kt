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
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.Util.TimeCal
import com.supremehyo.locationsns.View.DetailContentActivity
import com.supremehyo.locationsns.databinding.HomeItemLayoutBinding
import com.supremehyo.locationsns.databinding.ItemLoadingBinding
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class ContentRecyclerAdapter (private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var contentlist : ArrayList<EventDTO> = ArrayList<EventDTO>()
    var timecal : TimeCal= TimeCal()
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HomeItemLayoutBinding.inflate(layoutInflater, parent, false)
                ViewHolder(binding)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(binding)
            }
        }

    }

    override fun getItemCount(): Int = contentlist.size

    fun setContentList(contentlist: ArrayList<EventDTO>) {
        this.contentlist = contentlist
    }

    inner class LoadingViewHolder(private val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
    inner class ViewHolder(private val binding: HomeItemLayoutBinding):RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: EventDTO) {
            binding.titleTv.text = item.title
            binding.dateTimeTv.text = timecal.convert_date(item.start_time , item.end_time)
            binding.chatCountTv.text = item.chat_count.toString()
          
            //시간 계산 + 장소 이름
            val currentDateTime = LocalDateTime.now()
            var list : List<String> = item.create_time!!.split("+")
            val createDataTime = LocalDateTime.parse(list.get(0))
            timecal.compareHour(createDataTime,currentDateTime)
            binding.locationTv.text = item.location_name +" · "+timecal.compareHour(createDataTime,currentDateTime)

            binding.contentCl.setOnClickListener {
                val intent = Intent(binding.contentCl.context , DetailContentActivity::class.java)
                intent.putExtra("content", item)
                startActivity(binding.contentCl.context ,intent,null)
            }

        }
    }

    override fun getItemId(position: Int): Long {
        return contentlist.get(position).id!!.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return when (contentlist[position].title) {
            "" -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            var safePosition = holder.adapterPosition
            holder.bind(contentlist[safePosition])
        }else{

        }
    }

    fun deleteLoading(){
        contentlist.removeAt(contentlist.lastIndex)
    }
}