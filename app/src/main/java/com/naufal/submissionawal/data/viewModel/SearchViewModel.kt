package com.naufal.submissionawal.data.viewModel

import androidx.lifecycle.ViewModel
import com.naufal.submissionawal.data.response.SearchResponse

class SearchViewModel(): ViewModel() {
    private lateinit var searchData : List<SearchResponse>
    private var statusData = "empty"

    fun setStatusResult(status: String){
        this.statusData = status
    }

    fun setSearchResult(data: List<SearchResponse>){
        this.searchData = data
    }

    fun getSearchResult(): List<SearchResponse>{
        return this.searchData
    }

    fun getStatusResult(): String{
        return this.statusData
    }
}