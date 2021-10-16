package com.supremehyo.locationsns.View

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.supremehyo.locationsns.Base.BaseKotlinActivity
import com.supremehyo.locationsns.DTO.EventDTO
import com.supremehyo.locationsns.MyApplication.date.userDto
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.Util.RxEventBusHelper
import com.supremehyo.locationsns.ViewModel.ContentViewModel
import com.supremehyo.locationsns.ViewModel.FragmentViewModel.HomeViewModel
import com.supremehyo.locationsns.ViewModel.MainViewModel
import com.supremehyo.locationsns.databinding.ActivityContentEditBinding
import kotlinx.android.synthetic.main.activity_content_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ContentCreateActivity : BaseKotlinActivity<ActivityContentEditBinding , ContentViewModel>(){

    //계속 액티비티끼리 viewmodel koin 에러가 났던 이유 : 액티비티 끼리는 viewmodel 을 공유할 수 없음.
    // viewmodel은 activity 가 끝나면 종료되기 때문에 viewmodel 을 공유하고 싶다면 fragment 를 사용해서 공유해야한다.
    override val viewModel: ContentViewModel by viewModel() // Koin 으로 의존성 주입
    override val layoutResourceId: Int
        get() = R.layout.activity_content_edit


    // dto 조합을 위한 변수들
    var title = ""
    var location  = ""
    var location_name = ""
    var start_time = ""
    var end_time = ""
    var description = ""
    var premoney = ""
    var head_male = 0
    var head_female = 0
    var head = 0


    override fun initStartView() {
        title_et.requestFocus()
    }

    override fun initDataBinding() {

        RxEventBusHelper.mapSubject
            .subscribe{
                location = it.get(0)
                location_name = it.get(1)
                location_et.setText(location+" "+location_name)
            }

        RxEventBusHelper.timeSubject
            .subscribe {
                start_time = it.get(0)

                end_time = it.get(1)
                time_et.setText(it.get(0)+"\n"+it.get(1))
            }


        RxEventBusHelper.countSubject
            .subscribe {
                if(it.size == 2){
                    head_male = it.get(0)
                    head_female = it. get(1)
                    people_count__et.setText("남"+it.get(0).toString()+"명"+"  "+"여"+it.get(1).toString()+"명")
                }else if(it.size == 1){
                    head = it.get(0)
                    people_count__et.setText(it.get(0).toString()+"명")
                }
            }

        viewModel.eventLiveData.observe(this, Observer {
            Log.v("이벤트작성리스폰스", it.host+" "+it.title)
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
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

        //완료 버튼
        content_complete_tv.setOnClickListener {
            //완료됐을때 넘어가는 event dto 만드는중이였다. null 값 되도 되는거랑 안되도 되는거 잘 구분해서 해야할듯

            val str = start_time
            val intStr = str.replace("[\uAC00-\uD7A3]".toRegex(), "")
            val testString : String = intStr
            val splitArray = testString.split(".")

            val end_str = end_time
            val end_intStr = end_str.replace("[\uAC00-\uD7A3]".toRegex(), "")
            val end_String : String = end_intStr
            val end_splitArray = end_String.split(".")


            //시작시간 파싱
            var format_start = splitArray[0]+"-"
            if(splitArray.get(1).length == 1){
                format_start =format_start+"0"+splitArray[1]
            }else{
                format_start += splitArray[1]
            }
            format_start += "-"
            if(splitArray.get(2).length == 1){
                format_start = format_start+"0"+splitArray[2]
            }else{
                format_start += splitArray[2]
            }
            format_start += " "
            if(splitArray.get(3).length == 1){
                format_start = format_start+"0"+splitArray[3]
            }else{
                format_start += splitArray[3]
            }
            format_start += ":"
            if(splitArray.get(4).length == 1){
                format_start = format_start+"0"+splitArray[4]
            }else{
                format_start += splitArray[4]
            }
            format_start += ":00"


            //엔드시간 파싱

            var format_end = end_splitArray[0]+"-"
            if(end_splitArray.get(1).length == 1){
                format_end =format_end+"0"+end_splitArray[1]
            }else{
                format_end += end_splitArray[1]
            }
            format_end += "-"
            if(end_splitArray.get(2).length == 1){
                format_end = format_end+"0"+end_splitArray[2]
            }else{
                format_end += end_splitArray[2]
            }
            format_end += " "
            if(end_splitArray.get(3).length == 1){
                format_end = format_end+"0"+end_splitArray[3]
            }else{
                format_end += end_splitArray[3]
            }
            format_end += ":"
            if(end_splitArray.get(4).length == 1){
                format_end = format_end+"0"+end_splitArray[4]
            }else{
                format_end += end_splitArray[4]
            }
            format_end += ":00"

            val dateAndtime: LocalDateTime = LocalDateTime.now() //작성시간
            var eventDTO : EventDTO = EventDTO(1, dateAndtime.toString() ,title_et.text.toString() ,format_start,format_end,location,location_name,des_et.text.toString(), premoney_et.text.toString().toInt(),
            "" , head ,head_male,head_female,"10",0, true,userDto.phone)
            viewModel.create_event(eventDTO)

            finish();
        }
    }


}