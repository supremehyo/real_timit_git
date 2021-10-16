package com.supremehyo.locationsns.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.supremehyo.locationsns.Base.BaseViewModel
import com.supremehyo.locationsns.DTO.AuthDTO
import com.supremehyo.locationsns.DTO.SmsResponseDTO
import com.supremehyo.locationsns.DTO.UserDTO
import com.supremehyo.locationsns.Model.ContentModel
import com.supremehyo.locationsns.Model.UserProfileEditModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserProfileEditViewModel(private val model: UserProfileEditModel) : BaseViewModel() {

    private  val _editUserDTOLiveData = MutableLiveData<UserDTO>()
    val editUserDTOLiveData: LiveData<UserDTO>
        get() = _editUserDTOLiveData

    fun editProfile(userDTO: UserDTO) {
        addDisposable(model.editMyProfile(userDTO)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _editUserDTOLiveData.postValue(it)
                }
            }, {

                Log.d("스토어리스트", "response error, message : ${it.printStackTrace()}")
            }))
    }

}