package com.supremehyo.locationsns.Model

import com.supremehyo.locationsns.DTO.ResultSearchKeyword
import com.supremehyo.locationsns.Retrofit.UserAPI
import com.supremehyo.locationsns.Retrofit7.KakaoAPI
import io.reactivex.Single

class MapModelImpl(private val service : KakaoAPI) : MapModel {

    private val API_KEY = "911147e0a3efaca7823fafa26eb452a0"  // REST API 키
    override fun searchKeyword( keywork: String): Single<ResultSearchKeyword> {
        return service.getSearchKeyword(API_KEY,keywork)
    }
}