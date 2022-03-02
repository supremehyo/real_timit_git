package com.supremehyo.locationsns.Model

import com.supremehyo.locationsns.DTO.ResultSearchKeyword
import com.supremehyo.locationsns.Retrofit.RetrofitClass
import com.supremehyo.locationsns.Retrofit.UserAPI
import com.supremehyo.locationsns.Retrofit7.KakaoAPI
import io.reactivex.Single

class MapModelImpl() : MapModel {
    val retrofit : RetrofitClass = RetrofitClass


    private val API_KEY = "KakaoAK "  // REST API 키
    override fun searchKeyword( keywork: String): Single<ResultSearchKeyword> {
        return  retrofit.kakao_api.getSearchKeyword(API_KEY,keywork)
    }
}
