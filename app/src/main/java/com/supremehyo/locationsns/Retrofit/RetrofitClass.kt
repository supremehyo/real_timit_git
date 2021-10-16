package com.supremehyo.locationsns.Retrofit

import com.google.gson.GsonBuilder
import com.supremehyo.locationsns.MyApplication
import com.supremehyo.locationsns.Util.AuthenticationInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClass {
    private var instance: Retrofit? = null
    private var instance2: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    // 서버 주소
    private const val BASE_URL = "http://54.180.138.77/"
    var  loggingInterceptor : HttpLoggingInterceptor =  HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val okHttpClient = OkHttpClient.Builder().addInterceptor(AuthenticationInterceptor()).addInterceptor(loggingInterceptor).build()

    // SingleTon
    fun getInstance(): Retrofit {

        if (instance == null) {
            instance = Retrofit.Builder()
                .client(okHttpClient)
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
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance2!!
    }

    private val _auth_api = non_token_getInstance().create(AuthAPI::class.java)
    val auth_api
        get() = _auth_api

    private val _user_api = getInstance().create(UserAPI::class.java)
    val user_api
        get() = _user_api

    private val _evnet_api = getInstance().create(EventAPI::class.java)
    val evnet_api
        get() = _evnet_api
}
