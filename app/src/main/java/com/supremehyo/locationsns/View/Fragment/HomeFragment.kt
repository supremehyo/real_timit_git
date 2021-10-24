package com.supremehyo.locationsns.View.Fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.supremehyo.locationsns.Adapter.ContentRecyclerAdapter
import com.supremehyo.locationsns.Base.BaseFragment
import com.supremehyo.locationsns.DTO.EventDTO
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.View.AlarmActivity
import com.supremehyo.locationsns.View.ContentCreateActivity
import com.supremehyo.locationsns.View.TimeActivity
import com.supremehyo.locationsns.ViewModel.FragmentViewModel.HomeViewModel
import com.supremehyo.locationsns.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject


class HomeFragment :  BaseFragment<FragmentHomeBinding, HomeViewModel>(){
    override val layoutResourceId: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by inject() // Koin 으로 의존성 주입
     lateinit var contentListAdapter : ContentRecyclerAdapter

     var page_count = 1


    override fun onAttach(context: Context) {
        super.onAttach(context)
        contentListAdapter  = ContentRecyclerAdapter(context)
        contentListAdapter.setHasStableIds(true)
    }

    override fun initStartView() {

        var localCategory : ArrayList<String> = ArrayList<String>()
         localCategory.add("서울")
         localCategory.add("경기")

        var arrayAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(),R.layout.spinner_text , localCategory)
        spinner.adapter = arrayAdapter

        val LayoutManager = LinearLayoutManager(requireContext())
        val decoration = DividerItemDecoration(context, VERTICAL)
        home_recyclerView.layoutManager= LayoutManager
        home_recyclerView.addItemDecoration(decoration)

        //검색창 코드
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 검색 버튼 누를 때 호출
                if(query == null || query.isEmpty()){
                    Toast.makeText(context, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }else{
                    viewModel.getEvnetList(query.toString(),1)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                // 검색창에서 글자가 변경이 일어날 때마다 호출

                return true
            }
        })
    }

    override fun initDataBinding() {

        viewModel.eventListLiveData.observe(this , Observer {
            if(it.results.isNotEmpty()){
                home_recyclerView.visibility = View.VISIBLE
                home_emptyTv.visibility = View.GONE
                var list : ArrayList<EventDTO> = ArrayList<EventDTO>()
                list.addAll(it.results)
                contentListAdapter.setContentList(list) // 이게 아니라 add 하는 식으로 해야될듯?
                home_recyclerView.adapter =  contentListAdapter
                contentListAdapter.notifyDataSetChanged()
                swipeRefreshLayout.isRefreshing = false
            }else if(it.results.isEmpty()){
                Toast.makeText(context, "더 이상 글이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()

            }
        })
    }

    override fun initAfterBinding() {

        //처음 켜질때 event 글 로딩
        viewModel.getEvnetList("",1)

        home_add_bt.setOnClickListener {
            startActivity(Intent(activity, ContentCreateActivity::class.java))
            //findNavController().navigate(R.id.action_homeFragment_to_contentEditActivity)
        }

        alarm_iv.setOnClickListener {
            startActivity(Intent(activity, AlarmActivity::class.java))
            //findNavController().navigate(R.id.action_homeFragment_to_alarmActivity)
        }

        //스와이프 해서 새로고침
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getEvnetList("",1)
        }

        home_recyclerView.addOnScrollListener(object  : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount-1
                // 스크롤이 끝에 도달했는지 확인
                if (!home_recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount ) {
                  //  contentListAdapter.deleteLoading()// 이건 로딩이 성공했을때만 호출해야함
                    viewModel.getEvnetList("",page_count++)
                }
            }
        })

    }

}