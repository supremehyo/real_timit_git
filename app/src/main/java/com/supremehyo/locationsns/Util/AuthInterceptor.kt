package com.supremehyo.locationsns.Util

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import com.supremehyo.locationsns.DTO.AuthDTO
import com.supremehyo.locationsns.MyApplication
import com.supremehyo.locationsns.Retrofit.RetrofitClass
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Callback
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import java.io.IOException
import java.util.*

// https://kimch3617.tistory.com/entry/Retrofit%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%ED%86%A0%ED%81%B0-%EC%9D%B8%EC%A6%9D-%EB%B0%A9%EC%8B%9D-%EA%B5%AC%ED%98%84
// https://eclipse-owl.tistory.com/27 Rx로 처리 참고
class AuthenticationInterceptor() : Interceptor {

    val retrofit : RetrofitClass = RetrofitClass

    var authToken = MyApplication.prefs.getString("access","")
    var refresh_Token = MyApplication.prefs.getString("refresh","")

    @SuppressLint("CheckResult")
    override fun intercept(chain: Interceptor.Chain): Response {
        var toto : String = authToken
        var newRequest : Request
        if(toto != null && !toto.equals("")){
            newRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer "+toto).build();
            var response = chain.proceed(newRequest)
            if(response.code()==400 || response.code() == 401) {
                retrofit.auth_api.get_refreshToken(refresh_Token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        it.run {
                            Log.v("asdfasfe", it.access)
                            authToken = it.access
                            MyApplication.prefs.setString("access", authToken)
                            newRequest = chain.request().newBuilder().addHeader("Authorization", authToken).build();
                        }
                    }, {
                        Log.d(ContentValues.TAG, "response error, message : ${it.message}")
                    }).run {
                       return chain.proceed(newRequest)
                    }
            }
        }else{ newRequest = chain.request(); }
        return chain.proceed(newRequest)
    }
}