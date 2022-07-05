package com.utn.nerdypedia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.utn.nerdypedia.R
import com.utn.nerdypedia.viewmodels.ProfileViewModel


class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var imageView: ImageView
    private lateinit var btn: ImageButton
    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.profile_fragment, container, false)

        imageView = v.findViewById(R.id.userPic)
        btn = v.findViewById(R.id.changePicBtn)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        btn.setOnClickListener{
            openFileChooser()
        }
    }

    private val fileChooserContract = registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
        if (imageUri != null) {
            // imageUri now contains URI to selected image
            imageView.setImageURI(imageUri)
        }
    }

    // ...

    fun openFileChooser() {
        fileChooserContract.launch("image/*")
    }
}