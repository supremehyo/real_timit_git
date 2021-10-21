package com.supremehyo.locationsns.ViewModel.FragmentViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.supremehyo.locationsns.Base.BaseViewModel
import com.supremehyo.locationsns.DTO.EventDTO
import com.supremehyo.locationsns.DTO.EventListResultDTO
import com.supremehyo.locationsns.Model.HomeModel
import com.supremehyo.locationsns.Model.UserModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit

class HomeViewModel(private val model : HomeModel) : BaseViewModel() {

    private  val _eventListLiveData = MutableLiveData<EventListResultDTO>()
    val eventListLiveData: LiveData<EventListResultDTO>
        get() = _eventListLiveData


    fun getEvnetList(search : String, page : Int){
        addDisposable(model.getEventList(search, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe({
                it.run {
                    _eventListLiveData.postValue(it)
                }
            }, {
                Log.d(ContentValues.TAG, "response error, message : ${it.message}")
            }
            ))
    }

}