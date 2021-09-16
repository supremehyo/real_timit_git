package com.supremehyo.locationsns.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.supremehyo.locationsns.DTO.ContentDTO
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.View.DetailContentActivity
import com.supremehyo.locationsns.View.MainActivity
import kotlinx.android.synthetic.main.home_item_layout.view.*
import java.util.ArrayList

class ContentRecyclerAdapter (private val context: Context) : RecyclerView.Adapter<ContentRecyclerAdapter.ViewHolder>() {
    var contentlist : ArrayList<ContentDTO> = ArrayList<ContentDTO>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_item_layout,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = contentlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contentlist[position])

    }
    fun setContentList(contentlist: ArrayList<ContentDTO>) {
        this.contentlist = contentlist
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var content_cl : ConstraintLayout = itemView.findViewById(R.id.content_cl)
        var title_tv: TextView = itemView.findViewById(R.id.title_tv)
        var date_tv: TextView = itemView.findViewById(R.id.date_time_tv)
        var chat_count_tv: TextView = itemView.findViewById(R.id.chat_count_tv)
        var location_tv: TextView = itemView.findViewById(R.id.location_tv)

        fun bind(item: ContentDTO) {
            title_tv.text = item.title
            date_tv.text = item.date
            chat_count_tv.text = item.chat_count.toString()
            location_tv.text = item.location

            content_cl.setOnClickListener {
                //  Toast.makeText(context, contentlist[position].title, Toast.LENGTH_SHORT).show()
                val intent = Intent(content_cl?.context , DetailContentActivity::class.java)
              //  intent.pu intent로 값넘길때 parcle 그거 해서 넘기기 content dto 자체를 넘기면 된다.
                startActivity(content_cl.context,intent,null)
            }

        }
    }


}