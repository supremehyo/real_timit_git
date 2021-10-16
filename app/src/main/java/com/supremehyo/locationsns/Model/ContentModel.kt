package com.supremehyo.locationsns.Model

import com.supremehyo.locationsns.DTO.ContentDTO
import com.supremehyo.locationsns.DTO.EventDTO
import com.supremehyo.locationsns.DTO.UserDTO
import io.reactivex.Single

interface ContentModel {
    fun post_content(contentDTO: ContentDTO)

    //사람 수 카운트
    fun return_person_count(vararg count : Int)

    //지도 주소 보내기
    fun return_Map_address(address : String)

    //시간 값 결과 보내기
    fun return_Time(start_time : String, end_time : String)

    //이벤트 생성
    fun create_evnet(event_dto : EventDTO) : Single<EventDTO>

    //이벤트 삭제
    fun delete_event(id : Int)

    //호스트 닉네임 가져오기
    fun get_content_host_nickname(user_id : String) : Single<UserDTO>
}