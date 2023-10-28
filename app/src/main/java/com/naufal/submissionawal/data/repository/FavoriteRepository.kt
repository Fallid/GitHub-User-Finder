package com.naufal.submissionawal.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.naufal.submissionawal.data.database.Favorite
import com.naufal.submissionawal.data.database.FavoriteDao
import com.naufal.submissionawal.data.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao : FavoriteDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<Favorite>> = mFavoriteDao.getAllUserFavorite()
    fun insert(favorite: Favorite){
        executorService.execute { mFavoriteDao.insert(favorite) }
    }
    fun delete(favorite: Favorite){
        executorService.execute { mFavoriteDao.delete(favorite) }
    }
}