package com.supremehyo.locationsns.Model

import com.supremehyo.locationsns.DTO.ResultSearchKeyword
import io.reactivex.Single

interface MapModel {

    fun searchKeyword(keywork : String) : Single<ResultSearchKeyword>
}