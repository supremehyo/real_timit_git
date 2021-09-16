package com.supremehyo.locationsns.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.supremehyo.locationsns.Adapter.ContentRecyclerAdapter
import com.supremehyo.locationsns.DTO.ContentDTO
import com.supremehyo.locationsns.R
import kotlinx.android.synthetic.main.fragment_read.*
import kotlinx.android.synthetic.main.fragment_read.view.*

class ChattingFragment : Fragment() {

    lateinit var contentRecyclerAdapter: ContentRecyclerAdapter
    val datas = mutableListOf<ContentDTO>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view : View = inflater.inflate(R.layout.fragment_read, container, false)
         return view
    }

}