package com.supremehyo.locationsns.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.supremehyo.locationsns.Base.BaseViewModel
import com.supremehyo.locationsns.DTO.ContentDTO
import com.supremehyo.locationsns.DTO.EventDTO
import com.supremehyo.locationsns.Model.ContentModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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

    private  val _eventLiveData = MutableLiveData<EventDTO>()
    val eventLiveData: LiveData<EventDTO>
        get() = _eventLiveData

    private  val _NicknameLiveData = MutableLiveData<String>()
    val NicknameLiveData: LiveData<String>
        get() = _NicknameLiveData


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

    //글 작성 완료시
    fun create_event(eventDTO: EventDTO){
        addDisposable(model.create_evnet(eventDTO)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _eventLiveData.postValue(it)
                }
            }, {

                Log.d("이벤트 등록", "response error, message : ${it.printStackTrace()}")
            }))

    }

    //글 삭제 요청시
    fun delete_event(id : Int){
        model.delete_event(id)
    }

    //해당 유저 닉네임 갱신하는 요청
    fun get_content_host_nickname(user_id : String){
        addDisposable(  model.get_content_host_nickname(user_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _NicknameLiveData.postValue(it.nickname)
                }
            }, {

                Log.d("이벤트 등록", "response error, message : ${it.printStackTrace()}")
            }))

    }

}