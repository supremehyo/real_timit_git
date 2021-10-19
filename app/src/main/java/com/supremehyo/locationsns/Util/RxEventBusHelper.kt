package com.supremehyo.locationsns.Util

import io.reactivex.subjects.PublishSubject

object RxEventBusHelper {

    val mSubject = PublishSubject.create<String>()
    val timeSubject = PublishSubject.create<List<String>>()
    val mapSubject = PublishSubject.create<List<String>>()
    val countSubject = PublishSubject.create<ArrayList<Int>>()

    //지도에서 클릭했을때 콜백하는 함수
    val clikemapSubject =PublishSubject.create<List<String>>()

    fun sendEvent(str: String) {
        mSubject.onNext(str)
    }

    //사람 수 카운트
    fun return_person_count(vararg count : Int){
        var tempList = ArrayList<Int>()
        for(num in count){
            tempList.add(num)
        }
        countSubject.onNext(tempList)
    }

    //지도 주소 보내기
    fun return_Map_address(address : List<String>){
        mapSubject.onNext(address)
    }
    //주소창에서 선택한 지도 주소 보내기
    fun select_Map_address(address : List<String>){
        clikemapSubject.onNext(address)
    }


    //시간 값 결과 보내기
    fun return_Time(start_time : String, end_time : String){
        var tempList = listOf<String>(start_time,end_time)
        timeSubject.onNext(tempList)
    }
}