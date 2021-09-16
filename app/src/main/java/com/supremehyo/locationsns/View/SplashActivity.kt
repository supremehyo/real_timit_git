package com.supremehyo.locationsns.View

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.supremehyo.locationsns.R
import kotlinx.android.synthetic.main.activity_main.*


class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //getWindow().setNavigationBarColor(getColor(R.color.colorSplashBackground))

        // 내비게이션바 없애기 ======================
        val uiOptions: Int = getWindow().getDecorView().getSystemUiVisibility()
        var newUiOptions = uiOptions
        val isImmersiveModeEnabled = uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY === uiOptions
        if (isImmersiveModeEnabled) {
            Log.d("off", "Turning immersive mode mode off. ")
        } else {
            Log.d("on", "Turning immersive mode mode on.")
        }
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions)
        // 내비게이션바 없애기 End ======================

        /*
        // 익명로그인
        val mAuth = FirebaseAuth.getInstance()
        mAuth.signInAnonymously()
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.e("익명로그인", "signInAnonymously:success")
                        val user = mAuth.currentUser
                        ProductData.productData.getProductDataFromDB()
                        val storeName: String = sSharedPreferences.getString(
                            "store",
                            getString(R.string.tv_store_title)
                        )
                        ProductData.productData.getBackgroudImageFromDB(storeName)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e("익명로그인", "signInAnonymously:failure", task.exception)
                    }
                })*/
        val hd = Handler()
        hd.postDelayed(splashHandler(), 2000) // 2초 후에 hd handler 실행
    }

    private inner class splashHandler : Runnable {
        override fun run() {
            startActivity(
                Intent(
                    getApplication(),
                    FirstLoginActivity::class.java
                )
            ) //로딩이 끝난 후, HomeActivity 이동
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            this@Splash.finish() // 로딩페이지 Activity stack에서 제거
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}