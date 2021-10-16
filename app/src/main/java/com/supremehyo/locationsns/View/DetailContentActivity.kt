package com.supremehyo.locationsns.View

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.supremehyo.locationsns.Base.BaseKotlinActivity
import com.supremehyo.locationsns.DTO.EventDTO
import com.supremehyo.locationsns.MyApplication.date.userDto
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.Util.TimeCal
import com.supremehyo.locationsns.ViewModel.ContentViewModel
import com.supremehyo.locationsns.ViewModel.MainViewModel
import com.supremehyo.locationsns.databinding.ActivityDetailContentBinding
import com.supremehyo.locationsns.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_detail_content.*
import org.koin.android.ext.android.inject
import java.time.LocalDateTime

//event  에 쓴사람 닉네임 데이터가 없음.
//글을 쓴사람 호스트의 위치도 필요함
//상세주소말고 해당 건물의 이름을 담는 필드도 있어야할듯
class DetailContentActivity:  BaseKotlinActivity<ActivityDetailContentBinding, ContentViewModel>() , OnMapReadyCallback {
    override val layoutResourceId: Int
        get() = R.layout.activity_detail_content
    override val viewModel: ContentViewModel by inject() // Koin 으로 의존성 주입
    lateinit var event : EventDTO
    lateinit var timecal : TimeCal
    private var mMap: GoogleMap? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initStartView() {
        timecal = TimeCal()
        //그냥 바로 데이터 가져오는거
        event = intent.getSerializableExtra("content") as EventDTO
        content_title_tv.setText(event.title)

        

        //몇시간전 계산
        val currentDateTime = LocalDateTime.now()
        var list : List<String> = event.create_time!!.split("+")
        val createDataTime = LocalDateTime.parse(list.get(0))
        content_sub_text_tv.setText(timecal.compareHour(createDataTime, currentDateTime)+" · "+"채팅 "+event.chat_count+" · "+"조회"+" 0")//마지막에 글 조회수 나와야함
        
        
        content_detail_location_tv.text = event.location_name
        user_address_tv.text = event.location // 호스트의 위치가 나와야하는데 해당 값이 없음
        content_location_tv.text = event.location
        

        if (event.head == 0 || event.head == null) {
            content_count_tv.text =
                "남자" + event.head_male.toString() + "명 " + "여자" + event.head_female.toString() + "명"
        } else {
            content_count_tv.text = event.head.toString() + "명"
        }

        content_time_tv.text = timecal.convert_date(event.start_time , event.end_time)
        event_des.text = event.description

        content_detail_location_sub_tv.text = event.location


        //지도 관련
        Places.initialize(applicationContext, getString(R.string.google_map_token))
        // Create a new PlacesClient instance
        val placesClient = Places.createClient(this)
        //지도
        val mapFragment = supportFragmentManager.findFragmentById(R.id.content_map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        //처음에 켜질때 이렇게 하는데 수정으로 갔다가 다시 돌아올때는 서버에 요청해서 새로 값 갱신해야하는게 맞는거 같음.
        // onResume 에다가 처리 할 것
    }

    override fun initDataBinding() {
        viewModel.NicknameLiveData.observe(this, Observer {
            user_nickname_tv.text = it // 이게 정상적으로 가져와
        })

    }

    override fun initAfterBinding() {
        if(userDto.phone == event.host){

            content_menu_bt.adapter = ArrayAdapter.createFromResource(this, R.array.content_menuList, android.R.layout.simple_spinner_item)
            //오른쪽 상단 점세개 버튼 눌렀을 때
            content_menu_bt.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (position) {
                        //수정
                        0 -> {
                            //이벤트 수정 액티비티를 따로 만들자.
                        }
                        //삭제
                        1 -> {
                            viewModel.delete_event(event.id!!)
                        }
                        //신고
                        2 -> {
                            Toast.makeText(view!!.context, "신고접수가 완료됐습니다.", Toast.LENGTH_SHORT).show()
                        }
                        else -> {

                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }else{
            content_menu_bt.adapter = ArrayAdapter.createFromResource(this, R.array.content_menuList_nonhost, android.R.layout.simple_spinner_item)
            //오른쪽 상단 점세개 버튼 눌렀을 때
            content_menu_bt.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (position) {
                        //신고
                        0 -> {
                            Toast.makeText(view!!.context, "신고접수가 완료됐습니다.", Toast.LENGTH_SHORT).show()
                        }
                        else -> {

                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

        //닉네임 요청
        Log.v("hosaaa" ,event.host!! )
        viewModel.get_content_host_nickname(event.host!!)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap

        val SEOUL = LatLng(37.56, 126.97)

        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL)
        markerOptions.title("서울")
        markerOptions.snippet("한국의 수도")
        mMap!!.addMarker(markerOptions)

        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 10F))
    }

    fun gomap(latLng: LatLng , name : String , des : String){
        val SEOUL = latLng

        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL)
        markerOptions.title(name)
        markerOptions.snippet(des)
        mMap!!.addMarker(markerOptions)

        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 10F))
    }

}