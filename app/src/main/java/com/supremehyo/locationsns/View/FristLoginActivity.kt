package com.supremehyo.locationsns.View


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.supremehyo.locationsns.R
import kotlinx.android.synthetic.main.activity_firstlogin.*

//FirstLoginActivity
class FirstLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstlogin)

        first_auth_button.setOnClickListener {
            nextActivity()
        }
    }

    //버튼을 누르면 이동
    fun nextActivity(){
        startActivity(Intent(application, SecondLoginActivity::class.java))
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
    }
}