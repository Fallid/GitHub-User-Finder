package com.naufal.submissionawal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * from user WHERE id LIKE :id LIMIT 1")
    fun findById(id:Int):LiveData<Favorite?>

    @Query("SELECT * FROM user")
    fun getAllFavorite(): LiveData<MutableList<Favorite>>
}