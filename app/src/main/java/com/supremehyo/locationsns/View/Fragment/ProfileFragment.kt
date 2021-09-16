package com.supremehyo.locationsns.View.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.supremehyo.locationsns.Adapter.ContentRecyclerAdapter
import com.supremehyo.locationsns.Base.BaseFragment
import com.supremehyo.locationsns.DTO.ContentDTO
import com.supremehyo.locationsns.R
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
        viewModel.getMyProfile()
    }

    override fun initDataBinding() {
        viewModel.myProfileLiveData.observe(this, Observer {
            Log.v("sdfsdf" , it.nickname)
        })
    }

    override fun initAfterBinding() {
        //프로필 수정을 눌렀을 때
        user_profile_edit_bt.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_profileEditActivity)
        }
    }


}