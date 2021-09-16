package com.supremehyo.locationsns

import android.app.Application
import com.supremehyo.locationsns.DI.myDiModule
import com.supremehyo.locationsns.DTO.UserDTO
import com.supremehyo.locationsns.Util.PreferenceUtil
import org.koin.android.ext.android.startKoin


class MyApplication : Application() {

    companion object date {
        var date : String =""
       // var userDto : UserDTO = UserDTO("userId","userEmail")
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)
        startKoin(applicationContext, myDiModule)
        //이런식으로 최상위에서 myDiModule 을 넣어서 주입해줌.
    }


}