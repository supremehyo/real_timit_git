package com.supremehyo.locationsns.Model

import com.supremehyo.locationsns.DTO.UserDTO
import io.reactivex.Single

interface UserModel {

    fun getMyProfile() : Single<UserDTO>
    fun signUp_user(userDTO: UserDTO)
}