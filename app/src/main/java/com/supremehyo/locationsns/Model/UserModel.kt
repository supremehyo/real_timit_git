package com.supremehyo.locationsns.Model

import com.supremehyo.locationsns.DTO.EventListResultDTO
import com.supremehyo.locationsns.DTO.UserDTO
import io.reactivex.Single

interface UserModel {

    fun getMyProfile() : Single<UserDTO>
    fun editMyProfile(userDTO: UserDTO)
    fun signUp_user(userDTO: UserDTO)
    fun get_user_writeEvnetList(user_id : String , page : Int) : Single<EventListResultDTO>
    fun get_userData(phone_number : String) : Single<UserDTO>

}