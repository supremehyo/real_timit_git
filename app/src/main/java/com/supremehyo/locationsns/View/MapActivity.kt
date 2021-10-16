package com.supremehyo.locationsns.View


import android.util.Log
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
import com.supremehyo.locationsns.Util.RxEventBusHelper
import com.supremehyo.locationsns.ViewModel.ContentViewModel
import com.supremehyo.locationsns.databinding.ActivityMapBinding
import kotlinx.android.synthetic.main.activity_map.*


class MapActivity : BaseKotlinActivity<ActivityMapBinding, MapViewModel>() , OnMapReadyCallback{

    override val viewModel: MapViewModel by viewModel()
    override val layoutResourceId: Int
        get() = R.layout.activity_map
    private var mMap: GoogleMap? = null

    var address : String = ""
    var place_name : String = ""

    override fun initStartView() {

        // Initialize the SDK
        Places.initialize(applicationContext, getString(R.string.google_map_token))

        // Create a new PlacesClient instance
        val placesClient = Places.createClient(this)


        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME , Place.Field.LAT_LNG , Place.Field.ADDRESS))

        // Set up a PlaceSelectionListener to handle the response.
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



        //지도
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun initDataBinding() {


    }

    override fun initAfterBinding() {
        //지도 완료 버튼
        map_complete_tv.setOnClickListener {
            var tempList = listOf<String>(address, place_name)
            RxEventBusHelper.return_Map_address(tempList)
            finish()
        }
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