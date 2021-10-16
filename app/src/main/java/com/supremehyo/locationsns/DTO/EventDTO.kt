package com.supremehyo.locationsns.DTO

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


data class EventDTO  (
    @SerializedName("id")
    val id	 : Int? ,
    @SerializedName("create_time")
    val create_time : String?,
    @SerializedName("title")
    val title : String,
    @SerializedName("start_time")
    val start_time : String,
    @SerializedName("end_time")
    val end_time : String,
    @SerializedName("location")
    val location : String,
    @SerializedName("location_name")
    val location_name : String,
    @SerializedName("description")
    val description : String?,
    @SerializedName("pre_payment")
    val pre_payment : Int?,
    @SerializedName("level_pref")
    val level_pref : String?,
    @SerializedName("head")
    val head : Int?,
    @SerializedName("head_male")
    val head_male : Int?,
    @SerializedName("head_female")
    val head_female : Int?,
    @SerializedName("age")
    val age : String?,
    @SerializedName("chat_count")
    val chat_count : Int?,
    @SerializedName("is_active")
    val is_active : Boolean?,
    @SerializedName("host")
    val host : String?

): java.io.Serializable
