package com.example.clockproject.screens.host

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragment: Fragment, private val listOf: List<Fragment>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = listOf.size

    override fun createFragment(position: Int): Fragment = listOf[position]
}
