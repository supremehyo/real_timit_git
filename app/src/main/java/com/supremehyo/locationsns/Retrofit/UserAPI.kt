package com.supremehyo.locationsns.Retrofit

import com.supremehyo.locationsns.DTO.EventListResultDTO
import com.supremehyo.locationsns.DTO.UserDTO
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {

    @GET("api/user/my/profile/") // 회원가입
    fun getMyProfile(): Single<UserDTO>

    @FormUrlEncoded
    @PATCH("api/user/my/profile/")// 프로필 수정
    fun editMyProfile(
        @Field("phone") phone : String,
        @Field("last_login") last_login : String?,
        @Field("nickname") nickname : String,
        @Field("intro") intro : String,
        @Field("sex") sex : String,
        @Field("age") age : Int,
        @Field("location1") location1 : String,
        @Field("location2") location2 : String?,
        @Field("notification_pref") notification_pref : Boolean,
        @Field("auto_apply") auto_apply : Boolean,
        @Field("is_active") is_active : Boolean,
        @Field("is_admin") is_admin : Boolean,
    ) : Single<UserDTO>

    @FormUrlEncoded
    @GET("users/{userid}") //로그인
    fun signIn_user(
        @Path("userid") userid : String
    ):Call<UserDTO>

    @GET("api/user/{user_id}/")
    fun get_content_host_nickname(
        @Path("user_id") user_id  : String
    ) : Single<UserDTO>


    @GET("api/user/{user_id}/events/") //로그인
    fun get_user_writeEvnetList(
        @Path("user_id") user_id : String,
        @Query("page") page : Int
    ):Single<EventListResultDTO>

    @FormUrlEncoded
    @GET("api//user/{phone}/") //로그인
    fun get_userData(
        @Path("phone") phone : String
    ):Single<UserDTO>


}