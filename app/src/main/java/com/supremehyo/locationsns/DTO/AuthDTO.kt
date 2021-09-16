package com.supremehyo.locationsns.DTO

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AuthDTO(
    @SerializedName("refresh")
    val refresh : String ,
    @SerializedName("access")
    val access : String
)