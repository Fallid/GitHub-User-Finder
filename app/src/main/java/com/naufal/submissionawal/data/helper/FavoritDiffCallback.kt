package com.naufal.submissionawal.data.helper

import androidx.recyclerview.widget.DiffUtil
import com.naufal.submissionawal.data.database.Favorite

class FavoritDiffCallback(private val oldFavList: List<Favorite>, private val newFavList: List<Favorite>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldFavList.size
    }

    override fun getNewListSize(): Int {
        return  newFavList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].id == newFavList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorite = oldFavList[oldItemPosition]
        val newFavorite = newFavList[newItemPosition]
        return oldFavorite.login == newFavorite.login && oldFavorite.avatarUrl == newFavorite.avatarUrl
    }

}