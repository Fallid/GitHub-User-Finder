package com.naufal.submissionawal.data.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.naufal.submissionawal.data.response.UserContent
import com.naufal.submissionawal.database.DatabaseModul
import com.naufal.submissionawal.database.Favorite
import kotlinx.coroutines.launch

class UserViewModel(private val db: DatabaseModul) : ViewModel(){
    private lateinit var userData: UserContent
    private var statusData = "empty"

    fun addFavorite(item: Favorite?) = viewModelScope.launch {
        item?.let { user -> db.favoriteDao.insert(user) }
    }

    fun removeFavorite(item: Favorite?) =viewModelScope.launch {
        item?.let{user -> db.favoriteDao.delete(user)} }

    fun alreadyInFavorite(id: Int): LiveData<Boolean> = db.favoriteDao.findById(id).map { it != null }

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

    class Factory(private val db:DatabaseModul):ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T  = UserViewModel(db) as T
    }
}