package com.naufal.submissionawal.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPageAdapter(activity: AppCompatActivity, private val login: String ) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> {
                fragment = FollowersFragment()
                val bundle = Bundle()
                bundle.putString("username", login)
                fragment.arguments = bundle
            }
            1 -> {
                fragment = FollowingFragment()
                val bundle = Bundle()
                bundle.putString("username", login)
                fragment.arguments = bundle
            }
        }
        return fragment as Fragment
    }


}