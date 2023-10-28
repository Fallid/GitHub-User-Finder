package com.naufal.submissionawal.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.naufal.submissionawal.R
import com.naufal.submissionawal.data.response.SearchResponse
import com.naufal.submissionawal.data.response.UserContent
import com.naufal.submissionawal.data.retrofit.ApiConfig
import com.naufal.submissionawal.data.viewModel.UserViewModel
import com.naufal.submissionawal.databinding.ActivityContentUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentUser : AppCompatActivity() {
    private lateinit var contentUser: ActivityContentUserBinding
    private lateinit var userViewModel: UserViewModel
    private  var sharedPreferences : SharedPreferences? = null
    private  var editor : SharedPreferences.Editor? = null
    private var nightMode : Boolean? = false
    companion object{
        @StringRes
        private val TAB_TITLE = intArrayOf(R.string.tab_layout_text1, R.string.tab_layout_text2)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentUser = ActivityContentUserBinding.inflate(layoutInflater)
        setContentView(contentUser.root)

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        nightMode = sharedPreferences?.getBoolean("nightMode", false)!!

        contentUser.MtAppBar.setOnMenuItemClickListener { menuItem -> when(menuItem.itemId){
            R.id.appBarFavorite -> {
                val  intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.appBarThemeMode -> {
                if (nightMode == false){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    editor = sharedPreferences?.edit()
                    editor?.putBoolean("nightMode", true)
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    editor = sharedPreferences?.edit()
                    editor?.putBoolean("nightMode", false)
                }
                editor?.apply()
                true
            }
            else -> false
        } }

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        showLoading(true)

        val getLogin = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("user_detail",SearchResponse::class.java )
        }
        else{
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("user_detail")
        }
        if (userViewModel.getStatusData() == "empty"){
            if (getLogin != null){
                getUserData(getLogin.login.toString())
            }
        }else{
            resultUserContent(userViewModel.getUserData())
        }
    }

    private fun getUserData(username: String){
        val client =ApiConfig.getApiService()
        val call: Call<UserContent> = client.getUserDetail(username)
        showLoading(true)
        call.enqueue(object : Callback<UserContent>{
            override fun onResponse(call: Call<UserContent>, response: Response<UserContent>) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    showLoading(false)
                    if (responseBody != null){
                        userViewModel.setUserData(responseBody)
                        resultUserContent(responseBody)
                    }
                }
            }

            override fun onFailure(call: Call<UserContent>, t: Throwable) {
                showLoading(false)
                Log.e("UserDetail", "onFailure : ${t.message} ")
            }
        })
    }



    @Suppress("NAME_SHADOWING")
    private fun resultUserContent(data: UserContent){
        val name:String = if (data.name == "null"){
            "name not found"
        }else{
            data.name
        }

        val login ="@${data.login}"
        val followers ="Followers: ${data.followers}"
        val following ="Following: ${data.following}"
        contentUser.tvUserNameDetail.text = name
        contentUser.tvLoginUserDetail.text = login
        contentUser.tvFollowersUser.text = followers
        contentUser.tvFollowingUser.text = following
        contentUser.fabFavorite.setOnClickListener { Toast.makeText(this,name + data.avatar_url, Toast.LENGTH_SHORT).show() }
        Glide.with(contentUser.cvAvatarContentUser).load(data.avatar_url).into(contentUser.cvAvatarContentUser)

        val sectionPage = SectionPageAdapter(this, data.login)
        val viewPager2: ViewPager2 = contentUser.viewPagerUserDetail
        viewPager2.adapter = sectionPage
        val tab:TabLayout = contentUser.tabLayout
        TabLayoutMediator(tab, viewPager2){tab, position -> tab.text = resources.getString(TAB_TITLE[position]) }.attach()

        showLoading(false)
    }
    private fun showLoading(loading: Boolean){
        if(loading){
            contentUser.progressBarUserDetail.visibility = View.VISIBLE
        }else{
            contentUser.progressBarUserDetail.visibility = View.GONE
        }
    }
}