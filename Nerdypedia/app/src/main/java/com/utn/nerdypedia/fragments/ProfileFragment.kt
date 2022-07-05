package com.utn.nerdypedia.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import com.utn.nerdypedia.R
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
    private lateinit var changePicBtn : ImageButton
    private lateinit var userPic : ImageView

    private var imageUri: Uri? = null

    private val READ_PERMISSONS = 100
    private val PICK_IMAGE = 101

// Register the permissions callback, which handles the user's response to the
// system permissions dialog. Save the return value, an instance of
// ActivityResultLauncher. You can use either a val, as shown in this snippet,
// or a lateinit var in your onAttach() or onCreate() method.
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                val galleryIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, PICK_IMAGE)
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }


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
        changePicBtn = v.findViewById(R.id.changePicBtn)
        userPic = v.findViewById(R.id.userPic)

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

        changePicBtn.setOnClickListener{
//            viewModel.updatePic()

//            try {
//                if (ActivityCompat.checkSelfPermission(requireContext(),
//                        Manifest.permission.READ_EXTERNAL_STORAGE) !=
//                    PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(
//                        this.requireActivity(), arrayOf(
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE), READ_PERMISSONS
//                    )
//                } else {
//                    val galleryIntent =
//                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//                    startActivityForResult(galleryIntent, pickImage)
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }

            when {
                context?.let { it1 ->
                    ContextCompat.checkSelfPermission(
                        it1,
                        Manifest.permission.READ_EXTERNAL_STORAGE


                    )
                } == PackageManager.PERMISSION_GRANTED -> {
                    // You can use the API that requires the permission.
                    val galleryIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, PICK_IMAGE)
                }
//                shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
//                // In an educational UI, explain to the user why your app requires this
//                // permission for a specific feature to behave as expected. In this UI,
//                // include a "cancel" or "no thanks" button that allows the user to
//                // continue using your app without granting the permission.
//                showInContextUI(...)
//            }
                else -> {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    requestPermissionLauncher.launch(
                        Manifest.permission.READ_EXTERNAL_STORAGE

                    )
                }
            }

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

        viewModel.flagRequestPermissons.observe(viewLifecycleOwner, Observer { value ->
            if(value){
                //requestPermissions()
            }
        })

        viewModel.flagGoToGallery.observe(viewLifecycleOwner, Observer { value ->
            if(value == true){
                val galleryIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, PICK_IMAGE)
            }
        })

        viewModel.picURL.observe(viewLifecycleOwner, Observer { url ->
            if(url.isNotEmpty()){
                Picasso.get().load(url).into(userPic)
            }
            //userPic.setImageURI(uri)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            //viewModel.processGalleryPick(data?.data)
            imageUri = data?.data
            userPic.setImageURI(imageUri)
        }
    }

//    private fun requestPermissions() {
//        ActivityCompat.requestPermissions(
//            this.requireActivity(),
//            arrayOf(
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE),
//            READ_PERMISSONS
//        )
//    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        if (requestCode == READ_PERMISSONS) {
//            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                viewModel.processPermissionsResult(true)
//            } else {
//                viewModel.processPermissionsResult(false)
//            }
//        }
//    }
}