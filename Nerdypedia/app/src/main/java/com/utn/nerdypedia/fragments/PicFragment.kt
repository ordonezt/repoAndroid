package com.utn.nerdypedia.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.utn.nerdypedia.R
import com.utn.nerdypedia.viewmodels.PicViewModel

class PicFragment : Fragment() {

    companion object {
        fun newInstance() = PicFragment()
    }

    private lateinit var viewModel: PicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pic_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PicViewModel::class.java)
        // TODO: Use the ViewModel
    }

}