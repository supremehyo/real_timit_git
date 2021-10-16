package com.supremehyo.locationsns.Model

import com.supremehyo.locationsns.DTO.EventDTO
import com.supremehyo.locationsns.DTO.EventListResultDTO
import io.reactivex.Single

interface HomeModel {

    fun getEventList(search : String , page : Int) : Single<EventListResultDTO>
}