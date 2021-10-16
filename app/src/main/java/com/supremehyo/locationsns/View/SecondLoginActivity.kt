package com.supremehyo.locationsns.View

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.gms.common.util.Base64Utils
import com.supremehyo.locationsns.Base.BaseKotlinActivity
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.R.color.black
import com.supremehyo.locationsns.R.color.colorPrimary
import com.supremehyo.locationsns.ViewModel.AuthViewModel
import com.supremehyo.locationsns.databinding.ActivitySecondLoginBinding
import kotlinx.android.synthetic.main.activity_second_login.*
import org.koin.android.ext.android.inject
import java.security.DigestException
import java.security.MessageDigest
import kotlin.concurrent.timer

//Minsu - Islet
class SecondLoginActivity :  BaseKotlinActivity<ActivitySecondLoginBinding, AuthViewModel>(){

    override val layoutResourceId: Int
        get() = R.layout.activity_second_login
    override val viewModel: AuthViewModel by inject()// Koin 으로 의존성 주입

    var timeTick = 0
    var minute = 5
    var second = 0

    var origin_phone_number = ""
    var sms_number : Int = 0
    var timeString : String = ""


    override fun initStartView() {

    }

    override fun initDataBinding() {
        viewModel.smsLiveData.observe(this, Observer {
            sms_number = it

            minute = timeTick/60
            second = timeTick%60
            timer(period = 1000,initialDelay = 1000){
                runOnUiThread {
                    get_auth_text_bt.text = "인증문자 다시 받기"+String.format("%02d : %02d",minute,second)
                }
                if(second ==0 && minute ==0){

                }
                if(second == 0){
                    minute--
                    second= 60
                }
                second--
            }

        })
    }

    override fun initAfterBinding() {



        back_iv.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fadeout, R.anim.fadein)
        }

        get_auth_text_bt.setOnClickListener {
            if(phoneNumber_edit.text.length == 11) {
                //여기서 번호 보내서 토큰이랑 문자요청
                viewModel.auth_sms(origin_phone_number)
            }else{
                Toast.makeText(this, "휴대폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        post_auth_text_bt.setOnClickListener {
            if((smsNumber_edit.text.length ==6) && (sms_number.toString() == smsNumber_edit.text.toString())){
                //이때 토큰 요청해야함
                viewModel.get_token(origin_phone_number)
                startActivity(Intent(application, MainActivity::class.java))
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            }else{
                Toast.makeText(this, "인증번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        //폰 번호 입력했는 text 변화감지
        phoneNumber_edit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(phoneNumber_edit.text.length == 11){
                    var bgShape : GradientDrawable = get_auth_text_bt.background as GradientDrawable
                    bgShape.setColor(resources.getColor(black))
                    get_auth_text_bt.isClickable = true
                }else{
                    var bgShape : GradientDrawable = get_auth_text_bt.background as GradientDrawable
                    bgShape.setColor(resources.getColor(R.color.colorGray))
                    get_auth_text_bt.isClickable = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
                origin_phone_number =  s.toString()
                //    Log.v("ddd",origin_phone_number)
            }

        })

        //sms번호 입력 했는지 text 변화감지
        smsNumber_edit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(smsNumber_edit.text.length == 6){
                    var bgShape : GradientDrawable = post_auth_text_bt.background as GradientDrawable
                    bgShape.setColor(resources.getColor(colorPrimary))
                }else{
                    var bgShape : GradientDrawable = post_auth_text_bt.background as GradientDrawable
                    bgShape.setColor(resources.getColor(R.color.colorGray))
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }

    fun sha256_hash(input: String): String{
        val hash: ByteArray
        try{
            val md = MessageDigest.getInstance("SHA-256")
            md.update(input.toByteArray())
            hash = md.digest()
        }catch (e: CloneNotSupportedException){
            throw DigestException("couldn't make digest of patial content")
        }
        return Base64Utils.encode(hash)
    }
}