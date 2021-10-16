package com.supremehyo.locationsns.Retrofit

import com.supremehyo.locationsns.DTO.EventDTO
import com.supremehyo.locationsns.DTO.EventListResultDTO
import io.reactivex.Single
import retrofit2.http.*

interface EventAPI {
    

    @GET("api/events/") //이벤트 리스트 가져오기
    fun getEventList(
        @Query("search") search : String,
        @Query("page") page : Int
    ): Single<EventListResultDTO>

    @FormUrlEncoded
    @POST("api/events/") //이벤트 만들기
    fun create_Event(
        @Field("title") title: String,
        @Field("start_time") start_time: String,
        @Field("end_time") end_time: String,
        @Field("location") location: String,
        @Field("location_name") location_name: String,
        @Field("description") description: String?,
        @Field("pre_payment") pre_payment: Int?,
        @Field("level_pref") level_pref: String?,
        @Field("head") head: Int?,
        @Field("head_male") head_male: Int?,
        @Field("head_female") head_female: Int?,
        @Field("age") age: String?,
        @Field("chat_count") chat_count: Int?,
        @Field("is_active") is_active: Boolean?,
        @Field("host") host: String?,
    ): Single<EventDTO>

    @FormUrlEncoded
    @PATCH("api/events/{id}") // 이벤트 수정
    fun editEvent(
        @Path("id") id : Int,
        @Field("title") title: String,
        @Field("start_time") start_time: String,
        @Field("end_time") end_time: String,
        @Field("location") location: String,
        @Field("description") description: String?,
        @Field("pre_payment") pre_payment: Int?,
        @Field("level_pref") level_pref: String?,
        @Field("head") head: Int?,
        @Field("head_male") head_male: Int?,
        @Field("head_female") head_female: Int?,
        @Field("age") age: String?,
        @Field("is_active") is_active: Boolean?,
        @Field("host") host: String?,
    ): Single<EventDTO>

    //이벤트 삭제
    @GET("api/events/{id}") //이벤트 리스트 가져오기
    fun delete_Event(
        @Path("id") id : Int,
    )

}