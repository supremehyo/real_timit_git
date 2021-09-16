package com.supremehyo.locationsns.Retrofit

import android.net.Uri
import com.supremehyo.locationsns.DTO.ContentDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST

//https://angangmoddi.tistory.com/217 참고
//https://g-song-ii.tistory.com/11 참고
interface ContentAPI {

    @POST("")
    fun post_content(
        imageuri: String,
        content_text: String,
        latitude : String,
        longitude : String
    ): Call<ResponseBody>

}