package com.supremehyo.locationsns.Model

import com.supremehyo.locationsns.DTO.ContentDTO
import com.supremehyo.locationsns.DTO.EventDTO
import com.supremehyo.locationsns.DTO.UserDTO
import com.supremehyo.locationsns.Retrofit.RetrofitClass
import io.reactivex.Single

class ContentModelImpl() : ContentModel{
    var retrofit : RetrofitClass = RetrofitClass

    override fun post_content(contentDTO: ContentDTO) {
      //  service.post_content(contentDTO.imageuri.toString(), contentDTO.content_text , contentDTO.latitude,contentDTO.longitude)
    }

    override fun return_person_count(vararg count: Int) {

    }

    override fun return_Map_address(address: String) {

    }

    override fun return_Time(start_time: String, end_time: String) {

    }

    override fun create_evnet(event_dto: EventDTO) : Single<EventDTO> {

        return  retrofit.evnet_api.create_Event(event_dto.title,event_dto.start_time,event_dto.end_time,event_dto.location,
            event_dto.location_name,event_dto.latitude , event_dto.longitude ,event_dto.description, event_dto.pre_payment,event_dto.level_pref,event_dto.head,event_dto.head_male,event_dto.head_female,
            event_dto.age, event_dto.chat_count, event_dto.is_active,event_dto.host)
    }

    override fun delete_event(id: Int) {
        retrofit.evnet_api.delete_Event(id)
    }

    override fun get_content_host_nickname(user_id: String) : Single<UserDTO> {
       return   retrofit.user_api.get_content_host_nickname(user_id)
    }


}