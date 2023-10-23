package com.naufal.submissionawal.database

import android.content.Context
import androidx.room.Room

class DatabaseModul(context: Context) {
    private val db = Room.databaseBuilder(context, FavoriteRoomDatabase::class.java, "usergithub.db").allowMainThreadQueries().build()
    val favoriteDao = db.favoriteDao()
}