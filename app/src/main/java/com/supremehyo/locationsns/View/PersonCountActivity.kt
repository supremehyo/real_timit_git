package com.supremehyo.locationsns.View

import android.util.Log
import android.view.View
import com.supremehyo.locationsns.Base.BaseKotlinActivity
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.Util.RxEventBusHelper
import com.supremehyo.locationsns.ViewModel.MainViewModel
import com.supremehyo.locationsns.databinding.ActivityPersonCountBinding
import kotlinx.android.synthetic.main.activity_person_count.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonCountActivity : BaseKotlinActivity<ActivityPersonCountBinding , MainViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_person_count
    override val viewModel: MainViewModel by viewModel()

    var icon_check : Boolean = true


    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        //성별 무관 선택
        gender_check_icon.setOnClickListener {
            if(icon_check == true){
                gender_check_icon.setImageResource(R.drawable.non_check_icon)
                man_woman_ll.visibility = View.VISIBLE
                person_count_et1.visibility = View.GONE
                icon_check = false
            }else if(icon_check == false){
                gender_check_icon.setImageResource(R.drawable.check_icon)
                man_woman_ll.visibility = View.GONE
                person_count_et1.visibility = View.VISIBLE
                icon_check = true
            }

        }

        count_complete_tv.setOnClickListener {
            Log.v("클릭1" , "눌림")
            if(icon_check){
                Log.v("클릭2" , "눌림")
                RxEventBusHelper.return_person_count(person_count_et1.text.toString().toInt())
                finish()

            }else{
                Log.v("클릭3" , "눌림")
                RxEventBusHelper.return_person_count(man_et.text.toString().toInt() , woman_et.text.toString().toInt())
                finish()
            }

        }
    }


}