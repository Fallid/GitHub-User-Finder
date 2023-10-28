package com.naufal.submissionawal.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class FavoriteRoomDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}