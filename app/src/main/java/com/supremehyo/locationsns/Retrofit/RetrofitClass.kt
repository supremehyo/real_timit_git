package com.supremehyo.locationsns.Retrofit

import com.google.gson.GsonBuilder
import com.supremehyo.locationsns.MyApplication
import com.supremehyo.locationsns.Util.AuthenticationInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClass {
    private var instance: Retrofit? = null
    private var instance2: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    // 서버 주소
    private const val BASE_URL = "http://54.180.138.77/"


    val okHttpClient = OkHttpClient.Builder().addInterceptor(AuthenticationInterceptor()).build()

    // SingleTon
    fun getInstance(): Retrofit {

        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
    }

    // SingleTon
    fun non_token_getInstance(): Retrofit {

        if (instance2 == null) {
            instance2 = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance2!!
    }

    private val _auth_api = getInstance().create(AuthAPI::class.java)
    val auth_api
        get() = _auth_api

    private val _user_api = getInstance().create(UserAPI::class.java)
    val user_api
        get() = _user_api

}
