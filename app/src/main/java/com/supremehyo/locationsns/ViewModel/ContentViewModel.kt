package com.supremehyo.locationsns.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.supremehyo.locationsns.Base.BaseViewModel
import com.supremehyo.locationsns.DTO.ContentDTO
import com.supremehyo.locationsns.Model.ContentModel

class ContentViewModel(private val model: ContentModel) : BaseViewModel() {


    private  val _MapLiveData = MutableLiveData<String>()
    val MapLiveData: LiveData<String>
        get() = _MapLiveData


    private  val _TimeLiveData = MutableLiveData<List<String>>()
    val TimeLiveData: LiveData<List<String>>
        get() = _TimeLiveData

    private  val _PersonCountLiveData = MutableLiveData<List<Int>>()
    val PersonCountLiveData: LiveData<List<Int>>
        get() = _PersonCountLiveData


    fun writeContent(contentDto : ContentDTO){
        model.post_content(contentDto)
    }

    //사람 수 카운트
    fun return_person_count(vararg count : Int){
        var tempList = ArrayList<Int>()
        for(num in count){
            tempList.add(num)
        }
        _PersonCountLiveData.postValue(tempList)
    }

    //지도 주소 보내기
    fun return_Map_address(address : String){
        _MapLiveData.postValue(address)
    }

    //시간 값 결과 보내기
    fun return_Time(start_time : String, end_time : String){
        var tempList = listOf<String>(start_time,end_time)
        _TimeLiveData.postValue(tempList)
    }
}