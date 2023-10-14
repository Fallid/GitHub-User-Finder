package com.naufal.submissionawal.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naufal.submissionawal.data.response.FollowersResponse
import com.naufal.submissionawal.data.retrofit.ApiConfig
import com.naufal.submissionawal.databinding.FragmentFollowersBinding
import com.naufal.submissionawal.databinding.UserFollowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowersFragment : Fragment() {
    private lateinit var followerBinding : FragmentFollowersBinding

    companion object{
        private const val TAG = "FollowersFragment"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        followerBinding = FragmentFollowersBinding.inflate(inflater, container, false)
        val loginFollowers = arguments?.getString("username")
        if (loginFollowers != null){
            ftechFollowerGitHUb(loginFollowers)
        }
        showLoading(true)

        val linearLayoutManager = LinearLayoutManager(context)
        followerBinding.rvFollowerList.layoutManager =linearLayoutManager
        return followerBinding.root
    }

    private fun ftechFollowerGitHUb(listFollowers: String){
        val client = ApiConfig.getApiService()
        val call : Call<List<FollowersResponse>> = client.getFollowers(listFollowers)
        call.enqueue(object : Callback<List<FollowersResponse>>{
            override fun onResponse(
                call: Call<List<FollowersResponse>>,
                response: Response<List<FollowersResponse>>
            ) {
                if (response.isSuccessful){
                    val listFollower : List<FollowersResponse>? =response.body()
                    if (listFollower != null){
                        followerResult(listFollower)
                    }
                }
            }

            override fun onFailure(call: Call<List<FollowersResponse>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "OnFailure: ${t.message}")
            }
        })
    }
    private fun followerResult(followers: List<FollowersResponse>){
        showLoading(false)
        val dataAdapter =FollowerAdapter(followers)
        followerBinding.rvFollowerList.adapter = dataAdapter
    }

    private fun showLoading(loading: Boolean){
        if (loading){
            followerBinding.progressBarFollowerList.visibility = View.VISIBLE
        }else{
            followerBinding.progressBarFollowerList.visibility = View.GONE
        }
    }

    inner class FollowerAdapter(private val followerList: List<FollowersResponse>): RecyclerView.Adapter<FollowerAdapter.ViewHolder>(){
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val followersView = UserFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(followersView)
        }

        override fun onBindViewHolder(holder:ViewHolder, position: Int) {
            with(followerList[position]){
                holder.tvLogin.text = login
                Glide.with(holder.cvAvatarUrl).load(followersUrl).into(holder.cvAvatarUrl)

            }
        }

        override fun getItemCount(): Int {
            return followerList.size
        }
        inner class ViewHolder(userFollow: UserFollowBinding): RecyclerView.ViewHolder(userFollow.root){
            val tvLogin : TextView = userFollow.tvFollowName
            val cvAvatarUrl : ImageView = userFollow.AvatarFollow
        }
    }

}