package com.utn.nerdypedia.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.utn.nerdypedia.R
import com.utn.nerdypedia.viewmodels.BiographyViewModel

class BiographyFragment : Fragment() {

    companion object {
        fun newInstance() = BiographyFragment()
    }

    private lateinit var viewModel: BiographyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.biography_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BiographyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}