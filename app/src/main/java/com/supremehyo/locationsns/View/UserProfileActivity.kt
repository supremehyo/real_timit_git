package com.supremehyo.locationsns.View

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.supremehyo.locationsns.Adapter.ContentRecyclerAdapter
import com.supremehyo.locationsns.Adapter.MyContentRecyclerAdapter
import com.supremehyo.locationsns.Base.BaseKotlinActivity
import com.supremehyo.locationsns.DTO.EventDTO
import com.supremehyo.locationsns.DTO.EventListResultDTO
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.ViewModel.FragmentViewModel.ProfileViewModel
import com.supremehyo.locationsns.ViewModel.MainViewModel
import com.supremehyo.locationsns.databinding.ActivityPersonCountBinding
import com.supremehyo.locationsns.databinding.ActivityUserProfileBinding
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.profile_nickname_tv
import kotlinx.android.synthetic.main.activity_user_profile.user_address_tv
import kotlinx.android.synthetic.main.activity_user_profile.user_age_tv
import kotlinx.android.synthetic.main.activity_user_profile.user_content_count_tv
import kotlinx.android.synthetic.main.activity_user_profile.user_content_recycler
import kotlinx.android.synthetic.main.activity_user_profile.user_profile_des_tv
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserProfileActivity : BaseKotlinActivity<ActivityUserProfileBinding, ProfileViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_user_profile
    override val viewModel: ProfileViewModel by viewModel()

    var user_id = ""
    var phone_number = ""
    lateinit var eventListResultDTO : EventListResultDTO
    lateinit var contentListAdapter : MyContentRecyclerAdapter


    override fun initStartView() {
        contentListAdapter  = MyContentRecyclerAdapter(this)
        contentListAdapter.setHasStableIds(true)


        val LayoutManager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, LinearLayout.VERTICAL) // 리사이클러뷰 구분줄
        user_content_recycler.layoutManager= LayoutManager
        user_content_recycler.addItemDecoration(decoration)


        if (intent.hasExtra("user_id") && intent.hasExtra("phone_number")) {
            user_id = intent.getStringExtra("user_id").toString()
            phone_number = intent.getStringExtra("phone_number").toString()
            Log.v("ssssgsgsg" ,phone_number)
            profile_nickname_tv.text = user_id
        } else {
            Toast.makeText(this, "정보를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun initDataBinding() {
        viewModel.usereventListLiveData.observe(this , Observer {
            eventListResultDTO = it
            user_content_count_tv.text = "총 "+it.count.toString()+"개" // 총 몇개인지 적용
            var list : ArrayList<EventDTO> = ArrayList<EventDTO>() // 이벤트 내용들 불러오기
            list.addAll(it.results) // list 에 넣어주고 
            contentListAdapter.contentlist = list
            user_content_recycler.adapter =  contentListAdapter // 어뎁터 연결
            contentListAdapter.notifyDataSetChanged() // 변경사항 알림
        })

        viewModel.userDataLiveData.observe(this , Observer {
            Glide.with(this).load(it.profile_photo).error(R.drawable.profile_outline).circleCrop().into(user_profile_iv2)
            Log.v("ssssgsgsg" , it.age.toString())
            convert_sexAndAge(it.age , it.sex)
            user_address_tv.text = it.location1 +" "+it.location2
            user_profile_des_tv.text = it.intro
        })
    }

    override fun initAfterBinding() {

        viewModel.get_user_writeEvnetList(phone_number , 1)
        viewModel.get_userData(phone_number)



    }

    fun convert_sexAndAge(age : Int? , sex : String?){
        var temp_age = ""
        var temp_sex = ""
        if(age != null){
            when(age) {
                in 10..19 -> temp_age = "10대"
                in 20..29 -> temp_age=  "20대"
                in 30..39 -> temp_age = "30대"
                in 40..49 -> temp_age = "40대"
                in 50..59 -> temp_age = "50대"
                else -> temp_age = "60대 이상"
            }
        }

        if(sex != null){
            when(sex){
                "여성" -> temp_sex = "여"
                "남성" -> temp_sex ="남"
                else ->  temp_sex = ""
            }
        }
        user_age_tv.text = temp_age+" "+temp_sex
    }

}