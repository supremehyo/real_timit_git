package com.supremehyo.locationsns.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.supremehyo.locationsns.Base.BaseViewModel
import com.supremehyo.locationsns.DTO.AuthDTO
import com.supremehyo.locationsns.DTO.SmsResponseDTO
import com.supremehyo.locationsns.Model.AuthModel
import com.supremehyo.locationsns.MyApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AuthViewModel(private val model : AuthModel): BaseViewModel() {


    private  val _smsLiveData = MutableLiveData<Int>()
    val smsLiveData: LiveData<Int>
        get() = _smsLiveData


    private  val _tokenLiveData = MutableLiveData<AuthDTO>()
    val tokenLiveData: LiveData<AuthDTO>
        get() = _tokenLiveData


    fun auth_sms(encoded_phone : String){

        addDisposable(model.auth_user(encoded_phone)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    val data =  Gson().fromJson(it.string() , SmsResponseDTO::class.java)
                    //Log.v("temp_sms",  data.auth_number.toString())
                    _smsLiveData.postValue(data.auth_number)
                }
            }, {

                Log.d("스토어리스트", "response error, message : ${it.printStackTrace()}")
            }))
    }

    fun get_token(encoded_phone : String) {
        addDisposable(model.get_token(encoded_phone)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    //     val temp_token = Gson().fromJson<AuthDTO>(it,AuthDTO::class.java)
                    //_tokenLiveData.postValue(it)
                    _tokenLiveData.postValue(it)
                }
            }, {
                Log.d("스토어리스트", "response error, message : ${it.message}")
            }))
    }

}