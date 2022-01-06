package com.supremehyo.locationsns.View.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.supremehyo.locationsns.Adapter.ContentRecyclerAdapter
import com.supremehyo.locationsns.Adapter.MyContentRecyclerAdapter
import com.supremehyo.locationsns.Base.BaseFragment
import com.supremehyo.locationsns.DTO.EventDTO
import com.supremehyo.locationsns.DTO.EventListResultDTO
import com.supremehyo.locationsns.MyApplication
import com.supremehyo.locationsns.MyApplication.date.userDto
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.View.UserProfileEditActivity
import com.supremehyo.locationsns.ViewModel.FragmentViewModel.ProfileViewModel
import com.supremehyo.locationsns.databinding.FragmentProfileBinding
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.profile_nickname_tv
import kotlinx.android.synthetic.main.fragment_profile.user_address_tv
import kotlinx.android.synthetic.main.fragment_profile.user_age_tv
import kotlinx.android.synthetic.main.fragment_profile.user_content_count_tv
import kotlinx.android.synthetic.main.fragment_profile.user_content_recycler
import kotlinx.android.synthetic.main.fragment_profile.user_profile_des_tv
import kotlinx.android.synthetic.main.fragment_read.view.*
import org.koin.android.ext.android.inject

class ProfileFragment : BaseFragment<FragmentProfileBinding , ProfileViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by inject() // Koin 으로 의존성 주입
    lateinit var eventListResultDTO : EventListResultDTO
    lateinit var contentListAdapter : MyContentRecyclerAdapter


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }



    override fun initStartView() {
        //일단 여기서 userDTO 에 있는 값들을 미리 넣어놓고나서 밑에서 rx로 처리하도록 하면 될듯
      //  viewModel.getMyProfile()
        contentListAdapter  = MyContentRecyclerAdapter(requireContext()) // 값은 제대로 오는데 리사이클러뷰 어뎁터를 이거에 맞게 새로 만들어야함
        contentListAdapter.setHasStableIds(true)

        val LayoutManager = LinearLayoutManager(requireContext())
        val decoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
        user_content_recycler.layoutManager= LayoutManager
        user_content_recycler.addItemDecoration(decoration)

    }

    override fun initDataBinding() {
        viewModel.myProfileLiveData.observe(this, Observer {
            MyApplication.userDto = it
            profile_nickname_tv.text = it.nickname
            Glide.with(requireContext()).load(it.profile_photo).error(R.drawable.profile_outline).circleCrop().into(user_profile_iv)
            convert_sexAndAge(it.age , it.sex)
            user_address_tv.text = it.location1 +" "+it.location2
            Log.v("sdfsdf", it.location2)
            if(it.intro == null){
                user_profile_des_tv.text = "자기소개가 없습니다."
            }else{
                user_profile_des_tv.text  = it.intro
            }
         
        })

        viewModel.usereventListLiveData.observe(this , Observer {
            eventListResultDTO = it
            user_content_count_tv.text = it.count.toString() // 총 몇개인지 적용
            var list : ArrayList<EventDTO> = ArrayList<EventDTO>() // 이벤트 내용들 불러오기
            list.addAll(it.results) // list 에 넣어주고
            contentListAdapter.contentlist = list
            user_content_recycler.adapter = contentListAdapter
            contentListAdapter.notifyDataSetChanged()
        })

        viewModel.myProfileLiveData.observe(this, Observer {
            userDto = it
            viewModel.get_user_writeEvnetList(userDto.phone , 1) // 유저가 쓴 데이터 가져오기
        })

    }

    override fun initAfterBinding() {
        //프로필 수정을 눌렀을 때
        user_profile_edit_bt.setOnClickListener {
            startActivity(Intent(context , UserProfileEditActivity::class.java))
        }
        viewModel.getMyProfile()
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
                "female" -> temp_sex = "여"
                "male" -> temp_sex ="남"
                else ->  temp_sex = ""
            }
        }
        user_age_tv.text = temp_age+" "+temp_sex
    }


    override fun onResume() {
        super.onResume()
        viewModel.getMyProfile() //다시 화면으로 왔을때 리프레시 되도록
    }

}