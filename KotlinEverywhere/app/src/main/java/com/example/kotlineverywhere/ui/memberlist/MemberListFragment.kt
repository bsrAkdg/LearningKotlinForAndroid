package com.example.kotlineverywhere.ui.memberlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.kotlineverywhere.R
import com.example.kotlineverywhere.ui.memberlist.adapter.MemberListAdapter
import com.example.kotlineverywhere.viewmodel.MemberListViewModel
import kotlinx.android.synthetic.main.fragment_member_list.*

class MemberListFragment : Fragment() {

    private val memberListAdapter = MemberListAdapter()

    private lateinit var viewModel: MemberListViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_member_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MemberListViewModel::class.java)

        initMemberListRecyclerView()
        initLiveData()
        onItemClicked()

        viewModel.getMemberList()
    }

    private fun initLiveData() {
        //listen to livedata
        viewModel.memberListLiveData.observe(this, Observer {
            // if there is a changing break this point
            memberListAdapter.memberList = it
        })
    }

    private fun initMemberListRecyclerView() {
        recyclerViewMemberList.apply {
            setHasFixedSize(true)
            adapter = memberListAdapter
        }
    }

    private fun onItemClicked() {
        memberListAdapter.setOnClickListener { clickedMember ->
            val action =
                MemberListFragmentDirections.actionMemberListFragmentToMemberDetailFragment().also {
                    it.member = clickedMember
                }

            findNavController().navigate(action)
        }
    }
}