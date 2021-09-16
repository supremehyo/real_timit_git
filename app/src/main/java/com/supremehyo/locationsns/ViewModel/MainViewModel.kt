package com.supremehyo.locationsns.ViewModel

import com.supremehyo.locationsns.Base.BaseViewModel
import com.supremehyo.locationsns.DTO.UserDTO
import com.supremehyo.locationsns.Model.UserModel

class MainViewModel(private val model : UserModel): BaseViewModel() {

    fun signUp_user(userDTO: UserDTO){
        model.signUp_user(userDTO)
    }
}