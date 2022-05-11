package com.utn.nerdypedia.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.utn.nerdypedia.R
import com.utn.nerdypedia.viewmodels.SettingsFakeViewModel

class SettingsFakeFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFakeFragment()
    }

    private lateinit var viewModel: SettingsFakeViewModel

    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.settings_fake_fragment, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsFakeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        val action = SettingsFakeFragmentDirections.actionSettingsFakeFragment2ToSettingsActivity()
        v.findNavController().navigate(action)
    }
}