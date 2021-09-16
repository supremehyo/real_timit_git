package com.supremehyo.locationsns.DTO

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class UserDTO(
    @SerializedName("phone")
    val phone : String,
    @SerializedName("last_login")
    val last_login : String,
    @SerializedName("nickname")
    val nickname : String,
    @SerializedName("sex")
    val sex : String,
    @SerializedName("age")
    val age : Int,
    @SerializedName("location1")
    val location1 : String,
    @SerializedName("location2")
    val location2 : String,
    @SerializedName("profile_photo")
    val profile_photo : String,
    @SerializedName("notification_pref")
    val notification_pref : Boolean,
    @SerializedName("auto_apply")
    val auto_apply : Boolean,
    @SerializedName("is_active")
    val is_active : Boolean,
    @SerializedName("is_admin")
    val is_admin : Boolean
)
