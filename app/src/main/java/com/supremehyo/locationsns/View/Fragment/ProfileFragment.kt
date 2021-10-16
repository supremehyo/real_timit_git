package com.supremehyo.locationsns.View.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.supremehyo.locationsns.Adapter.ContentRecyclerAdapter
import com.supremehyo.locationsns.Base.BaseFragment
import com.supremehyo.locationsns.DTO.ContentDTO
import com.supremehyo.locationsns.MyApplication
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.View.MainActivity
import com.supremehyo.locationsns.View.UserProfileEditActivity
import com.supremehyo.locationsns.ViewModel.FragmentViewModel.HomeViewModel
import com.supremehyo.locationsns.ViewModel.FragmentViewModel.ProfileViewModel
import com.supremehyo.locationsns.ViewModel.MainViewModel
import com.supremehyo.locationsns.databinding.FragmentProfileBinding
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_read.view.*
import org.koin.android.ext.android.inject

class ProfileFragment : BaseFragment<FragmentProfileBinding , ProfileViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by inject() // Koin 으로 의존성 주입



    override fun initStartView() {
        //일단 여기서 userDTO 에 있는 값들을 미리 넣어놓고나서 밑에서 rx로 처리하도록 하면 될듯
        viewModel.getMyProfile()
    }

    override fun initDataBinding() {
        viewModel.myProfileLiveData.observe(this, Observer {
            MyApplication.userDto = it
            
            profile_nickname_tv.text = it.nickname
            Glide.with(requireContext()).load(it.profile_photo).error(R.drawable.profile_outline).circleCrop().into(user_profile_iv)
            convert_sexAndAge(it.age , it.sex)
            user_address_tv.text = it.location1 +" "+it.location2
            if(it.intro == null){
                user_profile_des_tv.text = "자기소개가 없습니다."
            }else{
                user_profile_des_tv.text  = it.intro
            }
            Log.v("sdfsdf" , it.nickname)
        })
    }

    override fun initAfterBinding() {
        //프로필 수정을 눌렀을 때
        user_profile_edit_bt.setOnClickListener {
            startActivity(Intent(context , UserProfileEditActivity::class.java))
          //  findNavController().navigate(R.id.action_profileFragment_to_profileEditActivity)
        }
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


    private fun newInstant() : ProfileFragment
    {
        val args = Bundle()
        val frag = ProfileFragment()
        frag.arguments = args
        return frag
    }
}