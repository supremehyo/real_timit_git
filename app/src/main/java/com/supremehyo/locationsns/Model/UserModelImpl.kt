package com.supremehyo.locationsns.Model

import android.util.Log
import com.supremehyo.locationsns.DTO.UserDTO
import com.supremehyo.locationsns.MyApplication
import com.supremehyo.locationsns.Retrofit.RetrofitClass
import com.supremehyo.locationsns.Retrofit.UserAPI
import io.reactivex.Single
import okhttp3.ResponseBody

class UserModelImpl() : UserModel {
    val retrofit : RetrofitClass = RetrofitClass

    override fun getMyProfile()  : Single<UserDTO>{
        Log.v("zzzzzz2" , MyApplication.prefs.getString("access", ""))
       return  retrofit.user_api.getMyProfile("Bearer "+MyApplication.prefs.getString("access", ""))
    }

    override fun signUp_user(userDTO: UserDTO) {
        //service.signUp_user(userDTO.userId , userDTO.userEmail)
    }
}