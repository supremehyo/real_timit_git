package com.supremehyo.locationsns.View

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.maps.*
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
import net.daum.mf.map.api.MapPoint
import org.koin.android.ext.android.inject
import java.time.LocalDateTime
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapView




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


        val mapView = MapView(this)
        val mapViewContainer = content_map

        // 중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(event.longitude.toDouble(),   event.latitude.toDouble()), true);
        // 줌 레벨 변경
        mapView.setZoomLevel(7, true);
        // 중심점 변경 + 줌 레벨 변경
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(event.longitude.toDouble(),   event.latitude.toDouble()), 9, true);
        // 줌 인
        mapView.zoomIn(true);
        mapViewContainer.addView(mapView)
        val marker = MapPOIItem()
        marker.itemName = event.location_name
        marker.tag = 0
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(event.latitude.toDouble(), event.longitude.toDouble())
        marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
        marker.selectedMarkerType =
            MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker)

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


        /*
        //지도 관련
        Places.initialize(applicationContext, getString(R.string.google_map_token))
        // Create a new PlacesClient instance
        val placesClient = Places.createClient(this)
        //............................................................. 여기를 카카오 지도로 바꿔야할듯
        // 아마 카카오지도는 이름으로 검색하는게 있어서 이름으로 검색하고 그 좌표를 가져와서 마커 찍으면 될거같음.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.content_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        //처음에 켜질때 이렇게 하는데 수정으로 갔다가 다시 돌아올때는 서버에 요청해서 새로 값 갱신해야하는게 맞는거 같음.
        // onResume 에다가 처리 할 것
        */

    }

    override fun initDataBinding() {
        viewModel.NicknameLiveData.observe(this, Observer {
            user_nickname_tv.text = it.nickname // 이게 정상적으로 가져와
            if(it.location1!=null && it.location2 != null){
                user_address_tv.text = it.location1+" "+it.location2
            }else{
                user_address_tv.text = ""
            }
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

        user_nickname_tv.setOnClickListener {
            val intent = Intent(application, UserProfileActivity::class.java)
            intent.putExtra("user_id", user_nickname_tv.text)
            intent.putExtra("phone_number" , event.host.toString())
            startActivity(intent)

        }

        map_connect_iv.setOnClickListener {
            val location: Uri =
                Uri.parse("geo:${event.latitude}.${event.longitude}?q=${event.location}")
            // Or map point based on latitude/longitude
            // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
            // Or map point based on latitude/longitude
            // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
            val mapIntent = Intent(Intent.ACTION_VIEW, location)
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                Log.d("Error", "지도 앱이 없습니다 ")
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {

    }

    fun gomap(latLng: LatLng , name : String , des : String){

    }


    override fun onResume() {
        super.onResume()

    }
}