package com.naufal.submissionawal.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)
    @Delete
    fun delete(favorite: Favorite)
    @Query("SELECT * FROM favorite ORDER BY id ASC")
    fun getAllUserFavorite():LiveData<List<Favorite>>
}