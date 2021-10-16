package com.supremehyo.locationsns.View

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.supremehyo.locationsns.Base.BaseKotlinActivity
import com.supremehyo.locationsns.MyApplication.date.userDto
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.View.Fragment.ChattingFragment
import com.supremehyo.locationsns.View.Fragment.HomeFragment
import com.supremehyo.locationsns.View.Fragment.ProfileFragment
import com.supremehyo.locationsns.View.Fragment.SampleFragment
import com.supremehyo.locationsns.ViewModel.FragmentViewModel.ProfileViewModel
import com.supremehyo.locationsns.ViewModel.MainViewModel
import com.supremehyo.locationsns.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject


class MainActivity :  BaseKotlinActivity<ActivityMainBinding, MainViewModel>(){
    override val layoutResourceId: Int
        get() = R.layout.activity_main
    override val viewModel: MainViewModel by inject() // Koin 으로 의존성 주입
    val profile_viewModel: ProfileViewModel by inject()

    val homeFragment  = HomeFragment()
    val chattingFragment  = ChattingFragment()
    val profileFragment  = ProfileFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = homeFragment


    override fun initStartView() {

        fragmentManager.beginTransaction().apply {
            add(R.id.container, profileFragment, "").hide(profileFragment)
            add(R.id.container, chattingFragment, "").hide(chattingFragment)
            add(R.id.container, homeFragment, "")
        }.commit()
        initListeners()
    //    val navController = findNavController(R.id.main_nav_host)
     //   bottomNavigationView.setupWithNavController(navController)
      //  bottomNavigationView.itemIconTintList = null


    }

    override fun initDataBinding() {
        profile_viewModel.myProfileLiveData.observe(this, Observer {
            userDto = it
            Log.v("메인", it.age.toString())
        })
    }

    override fun initAfterBinding() {
        profile_viewModel.getMyProfile()
    }

    private fun initListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                    activeFragment = homeFragment
                    true
                }

                R.id.chattingFragment -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(chattingFragment).commit()
                    activeFragment = chattingFragment
                    true
                }

                R.id.profileFragment -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit()
                    activeFragment = profileFragment
                    true
                }

                else -> false
            }
        }
    }


}