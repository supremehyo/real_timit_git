package com.supremehyo.locationsns.View

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.supremehyo.locationsns.Base.BaseKotlinActivity
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.ViewModel.MainViewModel
import com.supremehyo.locationsns.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity :  BaseKotlinActivity<ActivityMainBinding, MainViewModel>(){
    override val layoutResourceId: Int
        get() = R.layout.activity_main
    override val viewModel: MainViewModel by inject() // Koin 으로 의존성 주입


    override fun initStartView() {
        val navController = findNavController(R.id.main_nav_host)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.itemIconTintList = null


    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}