package com.utn.nerdypedia.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.utn.nerdypedia.R
import com.utn.nerdypedia.entities.Session
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.profile_fragment, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        profileNameText = v.findViewById(R.id.profileNameText)
        profileUserNameText = v.findViewById(R.id.profileUserNameText)
        profileEmailText = v.findViewById(R.id.profileEmailText)
        profileLastLoginText = v.findViewById(R.id.profileLastLoginText)

        profileNameText.text = Session.user.name
        profileUserNameText.text = Session.user.username
//        profileEmailText.text = Session.user.email
//        profileLastLoginText.text = Session.user.lastLogin
    }
}