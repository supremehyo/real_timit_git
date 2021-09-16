package com.supremehyo.locationsns.Retrofit

import com.supremehyo.locationsns.DTO.UserDTO
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {

    @GET("api/user/my/profile/") // 회원가입
    fun getMyProfile(@Header("Authorization") token : String): Single<UserDTO>

    @FormUrlEncoded
    @GET("users/{userid}") //로그인
    fun signIn_user(
        @Path("userid") userid : String
    ):Call<UserDTO>


}