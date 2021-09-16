package com.supremehyo.locationsns.ViewModel

import com.supremehyo.locationsns.Base.BaseViewModel
import com.supremehyo.locationsns.DTO.ContentDTO
import com.supremehyo.locationsns.Model.ContentModel

class WriteViewModel(private  val model :ContentModel)  : BaseViewModel() {

    fun writeContent(contentDto : ContentDTO){
        model.post_content(contentDto)
    }
}