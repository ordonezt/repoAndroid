package com.utn.clase3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.utn.clase3.R
import com.utn.clase3.entities.User
import com.utn.clase3.viewmodels.WelcomeViewModel

class welcomeFragment : Fragment() {

    companion object {
        fun newInstance() = welcomeFragment()
    }

    private lateinit var viewModel: WelcomeViewModel
    private lateinit var nameTextView: TextView
    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.welcome_fragment, container, false)

        nameTextView = v.findViewById(R.id.nameTextView)

        return v
    }

    override fun onStart() {
        super.onStart()

        val user = welcomeFragmentArgs.fromBundle(requireArguments()).logedUsr

        nameTextView.text = user.name + '!'
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WelcomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}