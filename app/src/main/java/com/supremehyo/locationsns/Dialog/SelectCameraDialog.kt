package com.supremehyo.locationsns.Dialog

import android.content.Context
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.Util.RxEventBusHelper
import kotlinx.android.synthetic.main.camera_bottom_dialog.view.*

class SelectCameraDialog (context: Context) : BottomSheetDialog(context) {


    init {
        //R.layout.confirm_bottom_dialog 하단 다이어로그 생성 버튼을 눌렀을 때 보여질 레이아웃
        val view: View = layoutInflater.inflate(R.layout.camera_bottom_dialog, null)
        setContentView(view)
        
        //카메라 클릭
        view.camera_imageView.setOnClickListener {
            RxEventBusHelper.sendEvent("카메라")
    }

        view.gallery_imageView.setOnClickListener {
            RxEventBusHelper.sendEvent("갤러리")
        }
    }

}