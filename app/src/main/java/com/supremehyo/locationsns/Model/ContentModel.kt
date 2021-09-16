package com.supremehyo.locationsns.Model

import com.supremehyo.locationsns.DTO.ContentDTO

interface ContentModel {
    fun post_content(contentDTO: ContentDTO)

    //사람 수 카운트
    fun return_person_count(vararg count : Int)

    //지도 주소 보내기
    fun return_Map_address(address : String)

    //시간 값 결과 보내기
    fun return_Time(start_time : String, end_time : String)
}