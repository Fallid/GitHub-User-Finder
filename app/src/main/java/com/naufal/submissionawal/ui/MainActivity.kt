package com.naufal.submissionawal.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.submissionawal.R
import com.naufal.submissionawal.data.response.SearchResponse
import com.naufal.submissionawal.data.retrofit.ApiConfig
import com.naufal.submissionawal.data.retrofit.ListDataUser
import com.naufal.submissionawal.data.viewModel.SearchViewModel
import com.naufal.submissionawal.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//import android.content.Context
//import android.content.Intent
//import android.content.SharedPreferences
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.app.AppCompatDelegate
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.naufal.submissionawal.R
//import com.naufal.submissionawal.data.response.SearchResponse
//import com.naufal.submissionawal.data.retrofit.ApiConfig
//import com.naufal.submissionawal.data.retrofit.ListDataUser
//import com.naufal.submissionawal.data.viewModel.SearchViewModel
//import com.naufal.submissionawal.databinding.ActivityMainBinding
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchViewModel: SearchViewModel
    private  var sharedPreferences : SharedPreferences? = null
    private  var editor : SharedPreferences.Editor? = null
    private var nightMode : Boolean? = false

    companion object{
        private const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        nightMode = sharedPreferences?.getBoolean("nightMode", false)!!
        if (nightMode == false){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        binding.MtAppBar.setOnMenuItemClickListener { menuItem -> when(menuItem.itemId){
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
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    getUserName(searchView.text.toString())
                    searchViewModel.setStatusResult("exist")
                    false
                }

        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        showLoading(false)

        if (searchViewModel.getStatusResult() != "empty"){
            resultSearch(searchViewModel.getSearchResult())
        }
    }

    private fun getUserName(query: String){
        val client = ApiConfig.getApiService()
        val call: Call<ListDataUser> = client.getUsers(query)
        showLoading(true)
        call.enqueue(object : Callback<ListDataUser> {
            override fun onResponse(
                call: Call<ListDataUser>,
                response: Response<ListDataUser>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val responseBody = response.body()
                    val user: List<SearchResponse>? = responseBody?.items
                    if (user != null ) {
                        searchViewModel.setSearchResult(user)
                        resultSearch(user)
                    }
                }
            }

            override fun onFailure(call: Call<ListDataUser>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "OnFailure: ${t.message}")
            }


        })
    }
    private fun resultSearch(users: List<SearchResponse>){
        val adapter = SearchUserAdaptor(users)
        binding.recyclerView.adapter = adapter
    }

    private fun showLoading(loading: Boolean){
        if(loading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}
