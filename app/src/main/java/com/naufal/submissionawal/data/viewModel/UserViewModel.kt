package com.naufal.submissionawal.data.viewModel

import androidx.lifecycle.ViewModel
import com.naufal.submissionawal.data.response.UserContent

class UserViewModel : ViewModel(){
    private lateinit var userData: UserContent
    private var statusData = "empty"

    fun setUserData(data: UserContent){
        this.userData = data
    }

    fun setStatusData(status: String){
        this.statusData = status
    }

    fun getUserData(): UserContent{
        return this.userData
    }

    fun getStatusData(): String{
        return this.statusData
    }
}