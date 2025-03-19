package com.example.todolist.presentation

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todolist.presentation.mainFragment.MainFragment

class TabAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return MainFragment().apply {
            arguments = bundleOf("tabIndex" to position)
        }
    }
}
