package com.utn.nerdypedia.entities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.utn.nerdypedia.fragments.BiographyFragment
import com.utn.nerdypedia.fragments.PicFragment

class DetailsPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> BiographyFragment()
            1 -> PicFragment()
            else -> BiographyFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    companion object {
        private const val TAB_COUNT = 2
    }
}