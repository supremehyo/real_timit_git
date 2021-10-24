package com.supremehyo.locationsns.DTO

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


data class AccressTokenDTO(
    @SerializedName("access")
    var access : String
)