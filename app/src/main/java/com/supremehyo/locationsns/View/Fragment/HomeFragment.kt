package com.supremehyo.locationsns.View.Fragment


import android.widget.ArrayAdapter
import android.widget.LinearLayout.VERTICAL
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.supremehyo.locationsns.Adapter.ContentRecyclerAdapter
import com.supremehyo.locationsns.Base.BaseFragment
import com.supremehyo.locationsns.DTO.ContentDTO
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.ViewModel.FragmentViewModel.HomeViewModel
import com.supremehyo.locationsns.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject


class HomeFragment :  BaseFragment<FragmentHomeBinding, HomeViewModel>(){
    override val layoutResourceId: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by inject() // Koin 으로 의존성 주입

    lateinit var contentListAdapter : ContentRecyclerAdapter


    override fun initStartView() {
        var localCategory : ArrayList<String> = ArrayList<String>()
         localCategory.add("서울")
         localCategory.add("경기")

        var arrayAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(),R.layout.spinner_text , localCategory)

        spinner.adapter = arrayAdapter

        contentListAdapter = ContentRecyclerAdapter(requireContext())
        val LayoutManager = LinearLayoutManager(requireContext())
        val decoration = DividerItemDecoration(context, VERTICAL)
        home_recyclerView.layoutManager= LayoutManager
        home_recyclerView.addItemDecoration(decoration)


        var list : ArrayList<ContentDTO> = ArrayList<ContentDTO>()
         list.add(ContentDTO("반갑습니다1","서울 테니스장","08.24(월)","2시간 전" ,28))
        list.add(ContentDTO("반갑습니다2","서울 테니스장장","08.22(월)","2시간 전" ,14))
        list.add(ContentDTO("반갑습니다3","서울 테니스장장","08.21(월)","2시간 전" ,2))
        list.add(ContentDTO("반갑습니다4","서울 테니스장장장","08.20(ㅋ)","1시간 전" ,8))
        contentListAdapter.setContentList(list)//테스트용 데이터)
        home_recyclerView.adapter =  contentListAdapter

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

        home_add_bt.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_contentEditActivity)
        }

        alarm_iv.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_alarmActivity)
        }
    }


}