package com.supremehyo.locationsns.Util

import io.reactivex.subjects.PublishSubject

object RxEventBusHelper {

    val mSubject = PublishSubject.create<String>()
    val timeSubject = PublishSubject.create<List<String>>()
    val mapSubject = PublishSubject.create<String>()
    val countSubject = PublishSubject.create<ArrayList<Int>>()

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
    fun return_Map_address(address : String){
        mapSubject.onNext(address)
    }

    //시간 값 결과 보내기
    fun return_Time(start_time : String, end_time : String){
        var tempList = listOf<String>(start_time,end_time)
        timeSubject.onNext(tempList)
    }
}