package com.supremehyo.locationsns.Retrofit

import com.supremehyo.locationsns.DTO.AuthDTO
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthAPI {

    @FormUrlEncoded
    @POST("api/sms/") // 인증번호 요청auth_user
    fun auth_user(
        @Field("phone")phone: String
    ): Single<ResponseBody>

    @FormUrlEncoded
    @POST("api/token/") // 인증번호 요청
    fun get_token(
        @Field("phone")phone: String
    ): Single<AuthDTO>


}