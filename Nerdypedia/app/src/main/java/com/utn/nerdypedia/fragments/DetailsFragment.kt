package com.utn.nerdypedia.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.utn.nerdypedia.R
import com.utn.nerdypedia.viewmodels.DetailsViewModel

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var viewModel: DetailsViewModel

    private lateinit var v: View
    private lateinit var scientistNameText : TextView
    private lateinit var scientistImageView : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.details_fragment, container, false)

        scientistNameText = v.findViewById(R.id.scientistNameTextView)
        scientistImageView = v.findViewById(R.id.scientistImageView)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        val scientist = DetailsFragmentArgs.fromBundle(requireArguments()).selectedItem

        scientistNameText.text = scientist.name
    }
}