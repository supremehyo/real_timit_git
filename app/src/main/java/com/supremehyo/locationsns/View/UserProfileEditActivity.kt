package com.supremehyo.locationsns.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.supremehyo.locationsns.Base.BaseKotlinActivity
import com.supremehyo.locationsns.Model.UserProfileEditModel
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.ViewModel.ContentViewModel
import com.supremehyo.locationsns.ViewModel.MainViewModel
import com.supremehyo.locationsns.ViewModel.UserProfileEditViewModel
import com.supremehyo.locationsns.databinding.ActivityUserProfileEditBinding
import kotlinx.android.synthetic.main.activity_user_profile_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserProfileEditActivity : BaseKotlinActivity<ActivityUserProfileEditBinding , UserProfileEditViewModel>() {

    override val viewModel: UserProfileEditViewModel by viewModel() // Koin 으로 의존성 주입
    override val layoutResourceId: Int
        get() = R.layout.activity_user_profile_edit


    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        //유저 프로필 수정 완료
        profile_edit_complete_tv.setOnClickListener {
            
        }
    }


}