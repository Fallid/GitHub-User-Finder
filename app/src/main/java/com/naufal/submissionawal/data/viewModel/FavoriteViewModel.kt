package com.naufal.submissionawal.data.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.naufal.submissionawal.data.database.Favorite
import com.naufal.submissionawal.data.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite){
        mFavoriteRepository.insert(favorite)
    }
    fun delete(favorite: Favorite){
        mFavoriteRepository.delete(favorite)
    }
}