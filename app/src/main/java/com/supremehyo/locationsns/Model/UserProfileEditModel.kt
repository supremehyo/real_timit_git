package com.supremehyo.locationsns.Model

import com.supremehyo.locationsns.DTO.UserDTO
import io.reactivex.Single

interface UserProfileEditModel  {
    fun editMyProfile(userDTO: UserDTO) : Single<UserDTO>
}