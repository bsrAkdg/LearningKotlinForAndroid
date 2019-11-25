package com.example.kotlineverywhere.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlineverywhere.model.Member
import com.example.kotlineverywhere.repository.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MemberListViewModel : ViewModel() {

    val memberListLiveData = MutableLiveData<List<Member>>() //for can set we use Mutable

    private val memberRepository = MemberRepository()

    fun getMemberList() { //fragment will access this method
        viewModelScope.launch {
            val members: List<Member> = memberRepository.getMemberList()

            //Dispatchers Main is that we want to return thread
            withContext(Dispatchers.Main) {
                // add to suspend(coroutines) MemberService and MemberRepository
                memberListLiveData.value = members
            }
        }
    }
}