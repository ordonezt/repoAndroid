package com.utn.nerdypedia.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.utn.nerdypedia.R
import androidx.lifecycle.Observer
import com.utn.nerdypedia.viewmodels.ProfileViewModel

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var v : View

    private lateinit var profileNameText : TextView
    private lateinit var profileUserNameText : TextView
    private lateinit var profileEmailText : TextView
    private lateinit var profileLastLoginText : TextView
    private lateinit var settingsBtn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.profile_fragment, container, false)

        profileNameText = v.findViewById(R.id.profileNameText)
        profileUserNameText = v.findViewById(R.id.profileUserNameText)
        profileEmailText = v.findViewById(R.id.profileEmailText)
        profileLastLoginText = v.findViewById(R.id.profileLastLoginText)
        settingsBtn = v.findViewById(R.id.settingsBtn)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        viewModel.onStart()

        settingsBtn.setOnClickListener{
            viewModel.goToSettings()
        }

        viewModel.loadUserData()

        /* Observadores del viewModel */
        viewModel.nameText.observe(viewLifecycleOwner, Observer { name ->
            profileNameText.text = name
        })

        viewModel.userNameText.observe(viewLifecycleOwner, Observer { userName ->
            profileUserNameText.text = userName
        })

        viewModel.emailText.observe(viewLifecycleOwner, Observer { email ->
            profileEmailText.text = email
        })

        viewModel.flagSettings.observe(viewLifecycleOwner, Observer { value ->
            if(value){
                val action = ProfileFragmentDirections.actionProfileFragmentToSettingsActivity()
                v.findNavController().navigate(action)
            }
        })
    }
}