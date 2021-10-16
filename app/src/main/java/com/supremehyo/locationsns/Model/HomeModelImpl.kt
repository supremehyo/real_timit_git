package com.supremehyo.locationsns.Model

import com.supremehyo.locationsns.DTO.EventListResultDTO
import com.supremehyo.locationsns.Retrofit.RetrofitClass
import io.reactivex.Single

class HomeModelImpl : HomeModel {

    var retrofit : RetrofitClass = RetrofitClass

    override fun getEventList(search: String, page: Int) : Single<EventListResultDTO>{
        return retrofit.evnet_api.getEventList(search, page)
    }
}