package com.example.android_taskmaz.presentaion.fragments.dashboard

import CourseAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.android_taskmaz.R
import com.example.android_taskmaz.databinding.FragmentDashBoardBinding
import com.example.android_taskmaz.databinding.FragmentMainBinding
import com.example.android_taskmaz.domain.models.Course
import com.example.android_taskmaz.domain.models.LiveUser
import com.example.android_taskmaz.presentaion.adapters.LiveUserAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs


class DashBoardFragment : Fragment() {

    private var _binding: FragmentDashBoardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = LiveUserAdapter(DataGenerator.generateFakeUsers())
        }
    }

    private fun setupViewPager() {

        binding.viewPager.apply {
            clipChildren=false
            clipToPadding=false
            offscreenPageLimit= 3
            getChildAt(0).overScrollMode= RecyclerView.OVER_SCROLL_NEVER
            adapter = CourseAdapter(DataGenerator.generateFakeCourses())

            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(50))
            compositePageTransformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
            binding.viewPager.setPageTransformer(compositePageTransformer)

            TabLayoutMediator(binding.tabDots, this) { _, _ -> }.attach()
        }
        setupTabLayoutDots()
    }

    private fun setupTabLayoutDots() {
        val tabs = binding.tabDots.getChildAt(0) as ViewGroup
        for (i in 0 until tabs.childCount ) {
            val tab = tabs.getChildAt(i)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 0f
            layoutParams.marginEnd = 10
            layoutParams.marginStart = 10
            layoutParams.width = 120
            tab.layoutParams = layoutParams
            binding.tabDots.requestLayout()
        }
    }


}





