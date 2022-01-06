package com.supremehyo.locationsns.View


import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import com.supremehyo.locationsns.Base.BaseKotlinActivity
import com.supremehyo.locationsns.MyApplication.date.userDto
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.ViewModel.UserProfileEditViewModel
import com.supremehyo.locationsns.databinding.ActivityUserProfileEditBinding
import kotlinx.android.synthetic.main.activity_detail_content.*
import kotlinx.android.synthetic.main.activity_user_profile_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserProfileEditActivity : BaseKotlinActivity<ActivityUserProfileEditBinding , UserProfileEditViewModel>() {

    override val viewModel: UserProfileEditViewModel by viewModel() // Koin 으로 의존성 주입
    override val layoutResourceId: Int
        get() = R.layout.activity_user_profile_edit


    var edit_nickname : String = ""
    var edit_sex : String = ""
    var edit_age : String = ""
    var edit_location : String = ""
    var edit_intro : String = ""

    override fun initStartView() {
        //유저 초기 값 불러오기
        edit_nickname_et.setText(userDto.nickname)
        if(userDto.location1 == null || userDto.location2 == null){
            edit_address_et.setText("")
        }else{
            edit_address_et.setText(userDto.location1 +" "+ userDto.location2)
        }
        edit_des_et.setText(userDto.intro)
    }

    override fun initDataBinding() {
        viewModel.editUserDTOLiveData.observe(this, Observer {
            userDto = it // 새로운 userdto 로 갱신
            finish() // 완료되고 나면 뒤로가기
           // Log.v("새로갱신", it.nickname)
        })
    }

    override fun initAfterBinding() {

        //성별 클릭

        gender_spinner.adapter = ArrayAdapter.createFromResource(this, R.array.gender_list, android.R.layout.simple_spinner_dropdown_item)
        //오른쪽 상단 점세개 버튼 눌렀을 때
        gender_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //edit_gender_et.setText(parent!!.getItemAtPosition(position).toString())
                edit_sex = parent!!.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        age_spinner.adapter = ArrayAdapter.createFromResource(this, R.array.age_list, android.R.layout.simple_spinner_dropdown_item)
        //오른쪽 상단 점세개 버튼 눌렀을 때
        age_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                edit_age =  parent!!.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        init_spinner(userDto.age , userDto.sex)

        //지역 클릭
        edit_address_et.setOnClickListener {
            var intent = Intent(this, UserAddressActivity::class.java)
            startActivityForResult(intent, 1)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        }


        //유저 프로필 수정 완료
        profile_edit_complete_tv.setOnClickListener {
            edit_nickname = edit_nickname_et.text.toString()
            edit_location = edit_address_et.text.toString()
            edit_intro = edit_des_et.text.toString()

            userDto.nickname = edit_nickname
            when(edit_sex){
                "남성" -> userDto.sex= "male"
                "여성" -> userDto.sex= "female"
            }
            Log.v("sdssss" , edit_location)
            val temparray = edit_location.split(" ")
            Log.v("sdssss" , temparray.get(0))
            Log.v("sdssss" , temparray.get(1))
          //  userDto.age = edit_age
            userDto.location1 = temparray.get(0)
            userDto.location2 = temparray.get(1)
            userDto.intro = edit_intro

            Log.v("asdfasf" , "${userDto.phone},${userDto.last_login} , ${userDto.nickname}, ${userDto.intro}, ${userDto.sex}, ${userDto.age}, " +
                    "${userDto.location1},${userDto.location2} ,${userDto.notification_pref} , ${userDto.auto_apply}, ${userDto.is_active},${userDto.is_admin}")

            viewModel.editProfile(userDto)

            
        }
    }


    fun init_spinner(age: Int , gender : String){
        Log.v("sssvsvsv" , age.toString() +" " + gender.toString())
        if(age != null){
            when(age) {
                in 10..19 -> age_spinner.setSelection(1)
                in 20..29 -> age_spinner.setSelection(2)
                in 30..39 -> age_spinner.setSelection(3)
                in 40..49 -> age_spinner.setSelection(4)
                in 50..59 -> age_spinner.setSelection(5)
                else -> age_spinner.setSelection(0)
            }
        }

        if(gender !=null){
            when(gender){
                "male" -> gender_spinner.setSelection(1)
                "female" -> gender_spinner.setSelection(2)
                else -> gender_spinner.setSelection(0)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val sendText: String = data?.extras?.getString("data").toString()
        //여기서 주소 파싱해야함.
        //젤에 우편 번호를 먼저 떼어냄
        val array = sendText.split("," , " ")
        for( i  in array){

        }
        Log.v("파싱테스트" ,array.get(2))
        Log.v("파싱테스트" ,array.get(3))
        edit_address_et.setText(array.get(2)+" "+array.get(3))

    }
}