package com.supremehyo.locationsns.Model

import com.supremehyo.locationsns.DTO.AccressTokenDTO
import com.supremehyo.locationsns.DTO.AuthDTO
import io.reactivex.Single
import okhttp3.ResponseBody

interface AuthModel {
    fun auth_user(phone : String) : Single<ResponseBody>
    fun get_token(encoded_phone: String) : Single<AuthDTO>
    fun get_refreshToken(refresh : String) : Single<AccressTokenDTO>
}