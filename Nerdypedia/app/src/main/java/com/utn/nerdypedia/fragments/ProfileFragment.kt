package com.utn.nerdypedia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.utn.nerdypedia.R
import com.utn.nerdypedia.entities.ViewState
import com.utn.nerdypedia.viewmodels.ProfileViewModel


class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    private lateinit var imageView: ImageView
    private lateinit var btn: ImageButton

    private lateinit var profileNameText : TextView
    private lateinit var profileUserNameText : TextView
    private lateinit var profileEmailText : TextView
    private lateinit var settingsBtn : Button
    private lateinit var progressBarProfile: ProgressBar

    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.profile_fragment, container, false)

        imageView = v.findViewById(R.id.userPic)
        btn = v.findViewById(R.id.changePicBtn)

        profileNameText = v.findViewById(R.id.profileNameText)
        profileUserNameText = v.findViewById(R.id.profileUserNameText)
        profileEmailText = v.findViewById(R.id.profileEmailText)
        settingsBtn = v.findViewById(R.id.settingsBtn)
        progressBarProfile = v.findViewById(R.id.progressBarProfile)

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

        btn.setOnClickListener{
//            openFileChooser()
            viewModel.updatePic()
        }

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

        viewModel.flagGoToGallery.observe(viewLifecycleOwner, Observer { value ->
            if(value == true){
                viewModel.flagGoToGallery.value = false
                openFileChooser()
            }
        })

//        viewModel.picURI.observe(viewLifecycleOwner, Observer { uri ->
//            Picasso.get().load(uri).into(imageView)
//        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            when(state){
                ViewState.RESET -> {
                    viewModel.loadUserData()
                }
                ViewState.LOADING -> {
                    progressBarProfile.visibility   = View.VISIBLE
                    imageView.visibility            = View.INVISIBLE
                    btn.visibility                  = View.INVISIBLE
                }
                ViewState.SUCCESS -> {
                    progressBarProfile.visibility   = View.INVISIBLE
                    imageView.visibility            = View.VISIBLE
                    btn.visibility                  = View.VISIBLE
                    Picasso.get().load(viewModel.picURI).into(imageView)
                }
                ViewState.FAILURE -> {
                    progressBarProfile.visibility   = View.INVISIBLE
                    imageView.visibility            = View.VISIBLE
                    btn.visibility                  = View.VISIBLE
                    Picasso.get().load(viewModel.picURI).into(imageView)
                    Snackbar.make(v, viewModel.failtText, Snackbar.LENGTH_LONG).show()
                }
            }
        })

    }

    private val fileChooserContract = registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
        if (imageUri != null) {
            // imageUri now contains URI to selected image
            //imageView.setImageURI(imageUri)
//            Picasso.get().load(imageUri).into(imageView)
            viewModel.processGalleryPick(imageUri)
        }
    }

    // ...

    fun openFileChooser() {
        fileChooserContract.launch("image/*")
    }
}