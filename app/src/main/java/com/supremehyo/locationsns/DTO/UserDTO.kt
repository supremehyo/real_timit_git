package com.supremehyo.locationsns.DTO

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class UserDTO(
    @SerializedName("phone")
    var phone : String,
    @SerializedName("last_login")
    var last_login : String,
    @SerializedName("nickname")
    var nickname : String,
    @SerializedName("sex")
    var sex : String ,
    @SerializedName("age")
    var age : Int,
    @SerializedName("location1")
    var location1 : String,
    @SerializedName("location2")
    var location2 : String,
    @SerializedName("profile_photo")
    var profile_photo : String,
    @SerializedName("notification_pref")
    var notification_pref : Boolean,
    @SerializedName("auto_apply")
    var auto_apply : Boolean,
    @SerializedName("is_active")
    var is_active : Boolean,
    @SerializedName("is_admin")
    var is_admin : Boolean,
    @SerializedName("intro")
    var intro : String
)
