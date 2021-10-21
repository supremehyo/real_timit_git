package com.supremehyo.locationsns.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.supremehyo.locationsns.Base.BaseKotlinActivity
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.Util.RxEventBusHelper
import com.supremehyo.locationsns.ViewModel.ContentViewModel
import com.supremehyo.locationsns.ViewModel.FragmentViewModel.HomeViewModel
import com.supremehyo.locationsns.ViewModel.MainViewModel
import com.supremehyo.locationsns.databinding.ActivityContentEditBinding
import kotlinx.android.synthetic.main.activity_content_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContentEditActivity : BaseKotlinActivity<ActivityContentEditBinding , ContentViewModel>(){

    //계속 액티비티끼리 viewmodel koin 에러가 났던 이유 : 액티비티 끼리는 viewmodel 을 공유할 수 없음.
    // viewmodel은 activity 가 끝나면 종료되기 때문에 viewmodel 을 공유하고 싶다면 fragment 를 사용해서 공유해야한다.
    override val viewModel: ContentViewModel by viewModel() // Koin 으로 의존성 주입
    override val layoutResourceId: Int
        get() = R.layout.activity_content_edit




    override fun initStartView() {

        title_et.requestFocus()
    }

    override fun initDataBinding() {

        RxEventBusHelper.mapSubject
            .subscribe{
                location_et.setText(it.get(0)+" "+it.get(1))
            }

        RxEventBusHelper.timeSubject
            .subscribe {
                time_et.setText(it.get(0)+"\n"+it.get(1))
            }


        RxEventBusHelper.countSubject
            .subscribe {
                if(it.size == 2){
                    people_count__et.setText("남"+it.get(0).toString()+"명"+"  "+"여"+it.get(1).toString()+"명")
                }else if(it.size == 1){
                    people_count__et.setText(it.get(0).toString()+"명")
                }
            }
    }

    override fun initAfterBinding() {

        //지도 선택
        location_et.setOnClickListener {

            startActivity(Intent(application, MapActivity::class.java))
        }

        //날짜 선택
        calender_iv.setOnClickListener {
            startActivity(Intent(application, TimeActivity::class.java))
            //https://yuuj.tistory.com/51 이거보고 커스텀
        }
        //인원 선택
        people_count__et.setOnClickListener {
            startActivity(Intent(application, PersonCountActivity::class.java))
        }
    }


}