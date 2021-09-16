package com.supremehyo.locationsns.ViewModel.FragmentViewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.supremehyo.locationsns.Base.BaseViewModel
import com.supremehyo.locationsns.DTO.UserDTO
import com.supremehyo.locationsns.Model.AuthModel
import com.supremehyo.locationsns.Model.UserModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(private val model : UserModel) : BaseViewModel(){

    private  val _myProfileLiveData = MutableLiveData<UserDTO>()
    val myProfileLiveData: LiveData<UserDTO>
        get() = _myProfileLiveData


    fun getMyProfile() {
        addDisposable(model.getMyProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                        Log.d("유저프로필", "유저프로필 : ${it.nickname}")
                    _myProfileLiveData.postValue(it)
                }
            }, {
                Log.d(TAG, "response error, message : ${it.message}")
            }))

    }
}