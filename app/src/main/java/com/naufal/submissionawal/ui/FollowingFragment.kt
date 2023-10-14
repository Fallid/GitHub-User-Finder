package com.naufal.submissionawal.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naufal.submissionawal.data.response.FollowingResponse
import com.naufal.submissionawal.data.retrofit.ApiConfig
import com.naufal.submissionawal.databinding.FragmentFollowingBinding
import com.naufal.submissionawal.databinding.UserFollowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("NAME_SHADOWING")
class FollowingFragment : Fragment() {
    private lateinit var followingBinding: FragmentFollowingBinding

    companion object{
        private const val TAG = "FollowingFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        followingBinding = FragmentFollowingBinding.inflate(inflater, container, false)
        val loginFollowing = arguments?.getString("username")
        if (loginFollowing != null){
            ftechFollowingGithub(loginFollowing)
        }
        showLoading(true)
        val linearLayoutManager = LinearLayoutManager(context)
        followingBinding.rvFollowingList.layoutManager = linearLayoutManager
        return followingBinding.root
    }

    private fun ftechFollowingGithub(listFollowing: String){
        val client = ApiConfig.getApiService()
        val call : Call<List<FollowingResponse>> = client.getFollowing(listFollowing)
        call.enqueue(object : Callback<List<FollowingResponse>>{
            override fun onResponse(
                call: Call<List<FollowingResponse>>,
                response: Response<List<FollowingResponse>>
            ) {
                if (response.isSuccessful){
                    val listFollowing: List<FollowingResponse>? = response.body()
                    if (listFollowing != null){
                        followingResult(listFollowing)
                    }
                }
            }

            override fun onFailure(call: Call<List<FollowingResponse>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "OnFailure: ${t.message}")
            }

        })
    }

    private fun followingResult(following: List<FollowingResponse>){
        showLoading(false)
        val dataAdapter = FollowingAdapter(following)
        followingBinding.rvFollowingList.adapter = dataAdapter
    }

    private fun showLoading(loading: Boolean){
        if (loading){
            followingBinding.progressBarFollowingList.visibility = View.VISIBLE
        }else{
            followingBinding.progressBarFollowingList.visibility = View.GONE
        }
    }

    inner class FollowingAdapter(private val followingList: List<FollowingResponse>):RecyclerView.Adapter<FollowingAdapter.ViewHolder>(){

        inner class ViewHolder(userFollowing: UserFollowBinding): RecyclerView.ViewHolder(userFollowing.root){
            val tvLoginFollowing: TextView = userFollowing.tvFollowName
            val cvAvatarFollowing: ImageView = userFollowing.AvatarFollow
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val followingView = UserFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(followingView)
        }

        override fun getItemCount(): Int {
            return followingList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(followingList[position]){
                holder.tvLoginFollowing.text = login
                Glide.with(holder.cvAvatarFollowing).load(followersUrl).into(holder.cvAvatarFollowing)
            }
        }
    }
}