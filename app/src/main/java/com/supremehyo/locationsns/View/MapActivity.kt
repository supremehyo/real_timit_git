package com.supremehyo.locationsns.View


import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.LinearLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.supremehyo.locationsns.Base.BaseKotlinActivity
import com.supremehyo.locationsns.ViewModel.MapViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.supremehyo.locationsns.R

import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.maps.model.LatLng
import com.supremehyo.locationsns.Adapter.LocationSerachRecyclerAdapter
import com.supremehyo.locationsns.DTO.ContentDTO
import com.supremehyo.locationsns.DTO.ResultSearchKeyword
import com.supremehyo.locationsns.Util.RxEventBusHelper
import com.supremehyo.locationsns.ViewModel.ContentViewModel
import com.supremehyo.locationsns.databinding.ActivityMapBinding
import kotlinx.android.synthetic.main.activity_content_edit.*
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.fragment_home.*


class MapActivity : BaseKotlinActivity<ActivityMapBinding, MapViewModel>() , OnMapReadyCallback{

    override val viewModel: MapViewModel by viewModel()
    override val layoutResourceId: Int
        get() = R.layout.activity_map
    private var mMap: GoogleMap? = null

    var address : String = ""
    var place_name : String = ""
    var latitude : String = ""
    var longitude : String = ""

    lateinit var datas : List<com.supremehyo.locationsns.DTO.Place>
    lateinit var locationSerachRecyclerAdapter: LocationSerachRecyclerAdapter

    override fun initStartView() {

        locationSerachRecyclerAdapter = LocationSerachRecyclerAdapter(this)
        val LayoutManager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, LinearLayout.VERTICAL)
        location_recyclerView.layoutManager= LayoutManager
        location_recyclerView.addItemDecoration(decoration)
        /*
        Places.initialize(applicationContext, getString(R.string.google_map_token))
        val placesClient = Places.createClient(this)
        val autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME , Place.Field.LAT_LNG , Place.Field.ADDRESS))
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
               Log.e("select", "Place: ${place.name}, ${place.latLng} , ${place.address}")
               address = place.address.toString()
                place_name = place.name.toString()
                gomap(place.latLng!!, place.name!! ,"rd")
            }
            override fun onError(status: Status) {
               Log.e("err", "An error occurred: $status")
            }
        })

         */



        //지도
        /*
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

         */

        RxEventBusHelper.clikemapSubject
            .subscribe{
                place_name = it.get(0)
                address = it.get(1)
                latitude = it.get(2)
                longitude = it.get(3)
                location_editText.setText(place_name)
            }
    }

    override fun initDataBinding() {
        viewModel.mapSearchResponseLiveData.observe(this, Observer {
            datas = it.documents

            locationSerachRecyclerAdapter.datas = datas
            location_recyclerView.adapter = locationSerachRecyclerAdapter
            locationSerachRecyclerAdapter.notifyDataSetChanged()
        })
    }

    override fun initAfterBinding() {
        /*
        location_editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                if(location_editText.text.toString().isNotBlank() || location_editText.text.toString().isNotEmpty()){

                }
            }

        })*/

        //지도 완료 버튼
        map_complete_tv.setOnClickListener {
            var tempList = listOf<String>(address, place_name , latitude ,longitude)//상세주소랑 , 장소이름을 보냄
            RxEventBusHelper.return_Map_address(tempList)
            finish()
        }

        location_search_bt.setOnClickListener {
            viewModel.getResultSearchMap(location_editText.text.toString())
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        /*
        mMap = googleMap

        val SEOUL = LatLng(37.56, 126.97)

        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL)
        markerOptions.title("서울")
        markerOptions.snippet("한국의 수도")
        mMap!!.addMarker(markerOptions)

        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 10F))

         */
    }


    fun gomap(latLng: LatLng , name : String , des : String){
        /*
        val SEOUL = latLng

        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL)
        markerOptions.title(name)
        markerOptions.snippet(des)
        mMap!!.addMarker(markerOptions)

        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 10F))

         */
    }

}