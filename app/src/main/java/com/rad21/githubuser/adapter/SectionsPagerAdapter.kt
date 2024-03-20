package com.rad21.githubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rad21.githubuser.FollowerFragment
import com.rad21.githubuser.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, data: Bundle): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    private var fragment: Bundle

    init {
        fragment = data
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragment
        return fragment as Fragment
    }
}