package com.supremehyo.locationsns.Model

import com.supremehyo.locationsns.DTO.ContentDTO
import com.supremehyo.locationsns.Retrofit.ContentAPI

class ContentModelImpl(private val service : ContentAPI) : ContentModel{





    override fun post_content(contentDTO: ContentDTO) {
      //  service.post_content(contentDTO.imageuri.toString(), contentDTO.content_text , contentDTO.latitude,contentDTO.longitude)
    }

    override fun return_person_count(vararg count: Int) {

    }

    override fun return_Map_address(address: String) {

    }

    override fun return_Time(start_time: String, end_time: String) {

    }


}