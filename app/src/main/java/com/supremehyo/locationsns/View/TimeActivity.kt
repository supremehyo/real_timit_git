package com.supremehyo.locationsns.View

import android.os.Build
import android.widget.NumberPicker
import com.supremehyo.locationsns.Base.BaseKotlinActivity
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.Util.RxEventBusHelper
import com.supremehyo.locationsns.ViewModel.ContentViewModel
import com.supremehyo.locationsns.ViewModel.FragmentViewModel.HomeViewModel
import com.supremehyo.locationsns.ViewModel.MainViewModel
import com.supremehyo.locationsns.databinding.ActivityTimeBinding
import kotlinx.android.synthetic.main.activity_time.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min

class TimeActivity : BaseKotlinActivity<ActivityTimeBinding, MainViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_time
    override val viewModel: MainViewModel by viewModel() // Koin 으로 의존성 주입

    var local_year = 0
    var local_month = 0
    var local_day = 0
    var local_time = 0
    var local_minite = 0
    var start_end_flag : Boolean = false


    var temp_start_year = ""
    var temp_start_month = ""
    var temp_start_day = ""
    var temp_start_time = ""
    var temp_start_minite = ""

    var temp_end_year = ""
    var temp_end_month = ""
    var temp_end_day = ""
    var temp_end_time = ""
    var temp_end_minite = ""


    var start_dateformat = ""
    var end_dateformat = ""

    override fun initStartView() {

        val now: Long = System.currentTimeMillis()

// 현재 시간을 Date 타입으로 변환
        val date = Date(now)
        val calendar = Calendar.getInstance()
        calendar.time = date

        local_year = calendar.get(Calendar.YEAR)
        local_month = calendar.get(Calendar.MONTH)+1
        local_day = calendar.get(Calendar.DAY_OF_MONTH)
        local_time = calendar.get(Calendar.HOUR_OF_DAY)
        local_minite = calendar.get(Calendar.MINUTE)



        val year : NumberPicker =yearpicker_datepicker
        val month : NumberPicker = monthpicker_datepicker
        val day : NumberPicker = daypicker_datepicker
        val hour : NumberPicker = hourpicker_datepicker
        val minute : NumberPicker = minitpicker_datepicker

        //  순환 안되게 막기
        year.wrapSelectorWheel = false
        month.wrapSelectorWheel = false
        day.wrapSelectorWheel = false
        hour.wrapSelectorWheel = false
        minute.wrapSelectorWheel = false

        //  editText 설정 해제
        year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        day.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        hour.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        minute.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


        var displayedValues: List<String> = listOf("0","5","10","15","20","25","30","35","40","45","50","55")
        //  최소값 설정
        year.minValue = local_year
        month.minValue = 1
        day.minValue = 1
        hour.minValue = 0
        minute.minValue = 0

        //  최대값 설정
        year.maxValue = local_year+1
        month.maxValue = 12
        day.maxValue = calendar.get(Calendar.DAY_OF_MONTH)
        hour.maxValue = 23
        minute.maxValue = 11

        year.value = local_year
        month.value = local_month
        day.value = local_day
        hour.value = local_time
        minute.value = 0

        start_year.text = local_year.toString()+"년"
        end_year.text= local_year.toString()+"년"

        start_month.text = local_month.toString()+"월"
        end_month.text= local_month.toString()+"월"

        start_day.text = local_day.toString()+"일"
        end_day.text= local_day.toString()+"일"

        start_hour.text = local_time.toString()+"시"
        end_hour.text= local_time.toString()+"시"

        start_minute.text = local_minite.toString()+"분"
        end_minute.text= local_minite.toString()+"분"


        start_dateformat = local_year.toString()+"-"+local_month.toString()+"-"+local_day.toString()+local_time.toString()+":"+local_minite.toString()
        end_dateformat = local_year.toString()+"-"+local_month.toString()+"-"+local_day.toString()+local_time.toString()+":"+local_minite.toString()

        //시작 영역
        start_time_ll.setOnClickListener {
            start_end_flag = false
        }
        //종료 영역
        end_time_ll.setOnClickListener {
            start_end_flag = true
        }


        year.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener{
            override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
                if(start_end_flag == false){
                    start_year.text = newVal.toString()+"년"
                    temp_start_year = newVal.toString()
                }else{
                    end_year.text = newVal.toString()+"년"
                    temp_end_year = newVal.toString()
                }
            }
        })

        month.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener{
            override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
                if(start_end_flag == false){
                    start_month.text = newVal.toString()+"월"
                    temp_start_month = newVal.toString()
                    calendar.set(start_year.text.toString().substring(0,start_year.text.toString().length-1).toInt() ,
                        start_month.text.toString().substring(0,start_month.text.toString().length-1).toInt(), 1)
                    day.maxValue = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                }else{
                    end_month.text = newVal.toString()+"월"
                    temp_end_month = newVal.toString()
                }
            }
        })
        day.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener{
            override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
                if(start_end_flag == false){
                    start_day.text = newVal.toString()+"일"
                    temp_start_day = newVal.toString()
                }else{
                    end_day.text = newVal.toString()+"일"
                    temp_end_day = newVal.toString()
                }
            }
        })
        hour.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener{
            override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
                if(start_end_flag == false){
                    start_hour.text = newVal.toString()+"시"
                    temp_start_time = newVal.toString()
                }else{
                    end_hour.text = newVal.toString()+"시"
                    temp_end_time = newVal.toString()
                }
            }
        })
        minute.displayedValues = displayedValues.toTypedArray()
        minute.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener{
            override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
                if(start_end_flag == false){
                    start_minute.text = (newVal*5).toString()+"분"
                    temp_start_minite = (newVal*5).toString()
                }else{
                    end_minute.text = (newVal*5).toString()+"분"
                    temp_end_minite = (newVal*5).toString()
                }
            }
        })

        time_com.setOnClickListener {
            var temp_start = start_year.text.toString()+" "+start_month.text.toString()+" "+start_day.text.toString()+" "+start_hour.text.toString()+" "+start_minute.text.toString()
            var temp_end = end_year.text.toString()+" "+end_month.text.toString()+" "+end_day.text.toString()+" "+end_hour.text.toString()+" "+end_minute.text.toString()

            start_dateformat = temp_start_year+"-"+temp_start_month+"-"+temp_start_day+temp_start_time+""

            RxEventBusHelper.return_Time(temp_start,temp_end)
            finish()
        }


    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {


    }


/*
    fun popup_timepicker(StartOrEnd : String){
        val dialog = AlertDialog.Builder(applicationContext).create()
        val edialog : LayoutInflater = LayoutInflater.from(applicationContext)
        val mView : View = edialog.inflate(R.layout.dialog_datepicker,null)

        val year : NumberPicker = mView.findViewById(R.id.yearpicker_datepicker)
        val month : NumberPicker = mView.findViewById(R.id.monthpicker_datepicker)
        val cancel : TextView = mView.findViewById(R.id.cancel_button_datepicker)
        val save : TextView = mView.findViewById(R.id.save_button_datepicker)


        //  순환 안되게 막기
        year.wrapSelectorWheel = false
        month.wrapSelectorWheel = false

        //  editText 설정 해제
        year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        //  최소값 설정
        year.minValue = 2019
        month.minValue = 1

        //  최대값 설정
        year.maxValue = 2020
        month.maxValue = 12


        //  취소 버튼 클릭 시
        cancel.setOnClickListener {
            dialog.dismiss()
            dialog.cancel()
        }

        //  완료 버튼 클릭 시
        save.setOnClickListener {
            if(StartOrEnd.equals("시작")){
                start_time_tv.text = (year.value).toString() + "년"
                //    month_textview_statsfrag.text = (month.value).toString() + "월"
            }else{
                end_time_tv.text
            }


            dialog.dismiss()
            dialog.cancel()
        }

        dialog.setView(mView)
        dialog.create()
        dialog.show()
    }*/

}