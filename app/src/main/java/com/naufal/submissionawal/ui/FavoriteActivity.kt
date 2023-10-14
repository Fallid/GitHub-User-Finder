package com.naufal.submissionawal.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.naufal.submissionawal.R
import com.naufal.submissionawal.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private  var sharedPreferences : SharedPreferences? = null
    private  var editor : SharedPreferences.Editor? = null
    private var nightMode : Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        nightMode = sharedPreferences?.getBoolean("nightMode", false)!!

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

    }
}