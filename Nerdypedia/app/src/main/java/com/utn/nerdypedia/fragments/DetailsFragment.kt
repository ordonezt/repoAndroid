package com.utn.nerdypedia.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.utn.nerdypedia.R
import com.utn.nerdypedia.entities.DetailsPagerAdapter
import com.utn.nerdypedia.viewmodels.DetailsViewModel

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var viewModel: DetailsViewModel

    private lateinit var v: View

    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.details_fragment, container, false)

        viewPager = v.findViewById(R.id.view_pager)
        tabLayout = v.findViewById(R.id.tab_layout)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

//        val scientist = DetailsFragmentArgs.fromBundle(requireArguments()).selectedItem

        viewPager.setAdapter(DetailsPagerAdapter(requireActivity()))

        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            when (position) {
                0 -> tab.text = "Biography"
                1 -> tab.text = "Info"
                else -> tab.text = "undefined"
            }
        }).attach()
    }
}