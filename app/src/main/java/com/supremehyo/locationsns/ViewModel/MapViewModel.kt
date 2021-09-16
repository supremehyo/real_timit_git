package com.supremehyo.locationsns.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.supremehyo.locationsns.Base.BaseViewModel
import com.supremehyo.locationsns.DTO.ResultSearchKeyword
import com.supremehyo.locationsns.Model.MapModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MapViewModel(private val model : MapModel) : BaseViewModel() {

    private val TAG = "mapViewModel"
    private val _mapSearchResponseLiveData = MutableLiveData<ResultSearchKeyword>()
    val mapSearchResponseLiveData: LiveData<ResultSearchKeyword>
        get() = _mapSearchResponseLiveData

    fun getResultSearchMap(keyword : String){
        addDisposable(model.searchKeyword(keyword)// addDisposable는 Rxjava, 안에 model.getData 는 retrofit2 동작
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())// observeOn 은 Observable이 다음처리를 진행할때 사용할 스레드를 지정 //https://vagabond95.me/posts/is-this-rxjava-1/ 참고
            .subscribe({
                it.run {
                    if (documents.size > 0) {
                        Log.d(TAG, "documents : $documents")
                        _mapSearchResponseLiveData.postValue(this)
                        //postValue 와 setValue의 차이는 setValue는 호출하는 당사자가
                        // UI 스레드가 아니라면 반영이 안되지만 postValue는
                        //UI 스레드로 post 해주기 때문에 반영이 된다.
                    }
                    Log.d(TAG, "meta : $meta")
                }
            }, {
                Log.d(TAG, "response error, message : ${it.message}")
            }))

    }
}