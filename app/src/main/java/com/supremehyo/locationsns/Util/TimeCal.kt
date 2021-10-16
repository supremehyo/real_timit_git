package com.supremehyo.locationsns.Util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

class TimeCal {

    //https://hianna.tistory.com/611 시간 단위 비교 코드
    @RequiresApi(Build.VERSION_CODES.O)
    fun  compareHour(date1 : LocalDateTime, date2 : LocalDateTime): String {
        var result : String = ""
        when(ChronoUnit.HOURS.between(date1, date2)) {
            in 1..23 -> result = ChronoUnit.HOURS.between(date1, date2).toString()+"시간 전"
            in 24..47 -> result=  "하루전"
            in 48..167 -> result = "몇일전"
            else -> result = date1.toLocalDate().toString()
        }
        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convert_date(date1 : String, date2 : String) : String{
        if(!date1.equals("") && !date2.equals("")){
            var start_list : List<String> = date1.split("+")
            var end_list : List<String> = date2.split("+")
            var start_parse = LocalDateTime.parse(start_list.get(0))
            var end_parse = LocalDateTime.parse(end_list.get(0))

            var temp_month = ""
            var temp_day = ""
            var temp_hour = ""
            var temp_miunte = ""

            var end_temp_hour = ""
            var end_temp_miunte = ""

            var what_day = find_day(date1)

            //start time
            if(start_parse.monthValue < 10){ temp_month = "0"+start_parse.monthValue.toString() }
            else{ temp_month = start_parse.monthValue.toString() }

            if(start_parse.dayOfMonth < 10){ temp_day = "0"+start_parse.dayOfMonth.toString() }
            else{ temp_day = start_parse.dayOfMonth.toString() }

            if(start_parse.hour < 10){ temp_day = "0"+start_parse.hour.toString() }
            else{ temp_hour = start_parse.hour.toString() }

            if(start_parse.minute < 10){ temp_day = "0"+start_parse.minute.toString() }
            else{ temp_miunte = start_parse.minute.toString() }

            //end time
            if(end_parse.hour < 10){ end_temp_hour = "0"+end_parse.hour.toString() }
            else{ end_temp_hour = end_parse.hour.toString() }

            if(end_parse.minute < 10){ end_temp_miunte = "0"+end_parse.minute.toString() }
            else{ end_temp_miunte = end_parse.minute.toString() }

            return temp_month +"."+temp_day+"("+what_day+")" +" "+ temp_hour+":"+temp_miunte + " - " + end_temp_hour+":"+end_temp_miunte
        }else{
            return ""
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun find_day(date : String) : String{
        var result = ""
        var start_list : List<String> = date.split("+")
        var start_parse = LocalDateTime.parse(start_list.get(0))

        when(start_parse.dayOfWeek.toString()){
            "SUNDAY" -> {
                result=  "일"
            }
            "MONDAY" -> {
                result= "월"
            }
            "TUESDAY" -> {
                result="화"
            }
            "WEDNESDAY" -> {
                result="수"
            }
            "THURSDAY" -> {
                result="목"
            }
            "FRIDAY" -> {
                result="금"
            }
            "SATURDAY" -> {
                result="토"
            }
            else -> result="d"
        }

        return result
    }



}