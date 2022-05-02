package com.utn.clase3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.utn.clase3.R
import com.utn.clase3.viewmodels.ScientistDescriptionViewModel

class ScientistDescription : Fragment() {

    companion object {
        fun newInstance() = ScientistDescription()
    }

    private lateinit var viewModel: ScientistDescriptionViewModel
    private lateinit var v: View
    private lateinit var scientistNameText : TextView
    private lateinit var scientistImageView : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.scientist_description_fragment, container, false)

        scientistNameText = v.findViewById(R.id.scientistNameTextView)
        scientistImageView = v.findViewById(R.id.scientistImageView)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScientistDescriptionViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        val scientist = ScientistDescriptionArgs.fromBundle(requireArguments()).selectedScientist

        scientistNameText.text = scientist.name
    }

}