package com.supremehyo.locationsns.Model

import android.util.Log
import com.supremehyo.locationsns.DTO.UserDTO
import com.supremehyo.locationsns.Retrofit.RetrofitClass
import com.supremehyo.locationsns.Retrofit.UserAPI
import com.google.gson.Gson
import io.reactivex.Single


class UserProfileEditModelImpl() : UserProfileEditModel {
    val retrofit : RetrofitClass = RetrofitClass

    override fun editMyProfile(userDTO: UserDTO): Single<UserDTO> {
      return  retrofit.user_api.editMyProfile( userDTO.phone,"" , userDTO.nickname, userDTO.intro, userDTO.sex, userDTO.age,
            userDTO.location1,userDTO.location2 , userDTO.notification_pref , userDTO.auto_apply, userDTO.is_active,userDTO.is_admin)
    }
}