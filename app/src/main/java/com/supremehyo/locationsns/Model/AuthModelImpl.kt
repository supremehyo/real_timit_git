package com.supremehyo.locationsns.Model

import com.supremehyo.locationsns.DTO.AuthDTO
import com.supremehyo.locationsns.Retrofit.RetrofitClass
import io.reactivex.Single
import okhttp3.ResponseBody

class AuthModelImpl() : AuthModel{

    val retrofit : RetrofitClass = RetrofitClass

    override fun auth_user(phone : String)  : Single<ResponseBody> {
        return retrofit.auth_api.auth_user(phone)
    }

    override fun get_token(encoded_phone: String): Single<AuthDTO> {
        return  retrofit.auth_api.get_token(encoded_phone)
    }


}