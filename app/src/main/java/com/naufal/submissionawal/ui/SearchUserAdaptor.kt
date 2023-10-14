package com.naufal.submissionawal.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naufal.submissionawal.R
import com.naufal.submissionawal.data.response.SearchResponse

class SearchUserAdaptor(private val indexList: List<SearchResponse>) :
    RecyclerView.Adapter<SearchUserAdaptor.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewUser: TextView = itemView.findViewById(R.id.tvFollowName)
        val imageViewUser: ImageView = itemView.findViewById(R.id.AvatarFollow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return indexList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = indexList[position]
        holder.textViewUser.text = item.login
        Glide.with(holder.itemView)
            .load(item.avatarUrl)
            .into(holder.imageViewUser)

        holder.itemView.setOnClickListener {
            val intentUserDetail = Intent(holder.itemView.context, ContentUser::class.java)
            intentUserDetail.putExtra("user_detail", indexList[holder.adapterPosition])
            holder.itemView.context.startActivity(intentUserDetail)
        }
    }


}