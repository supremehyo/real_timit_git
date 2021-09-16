package com.supremehyo.locationsns.Util

import com.supremehyo.locationsns.MyApplication
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.*

// https://kimch3617.tistory.com/entry/Retrofit%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%ED%86%A0%ED%81%B0-%EC%9D%B8%EC%A6%9D-%EB%B0%A9%EC%8B%9D-%EA%B5%AC%ED%98%84
// https://eclipse-owl.tistory.com/27 Rx로 처리 참고
class AuthenticationInterceptor() : Interceptor {

    var authToken = MyApplication.prefs.getString("access","")


    override fun intercept(chain: Interceptor.Chain): Response {
        var req =
            chain.request().newBuilder().addHeader("Authorization", authToken).build()
        return chain.proceed(req)
    }
}