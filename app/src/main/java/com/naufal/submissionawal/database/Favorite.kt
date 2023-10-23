package com.naufal.submissionawal.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user")
data class Favorite(
    @ColumnInfo(name = "avatar_url")
    val avatar_url: String,
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "login")
    val login: String,
) :Parcelable