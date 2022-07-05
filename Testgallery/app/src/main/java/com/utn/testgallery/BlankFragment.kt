package com.utn.testgallery

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts

class BlankFragment : Fragment() {

    companion object {
        fun newInstance() = BlankFragment()
    }

    private lateinit var viewModel: BlankViewModel
    private lateinit var imageView: ImageView
    private lateinit var btn: Button
    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.blank_fragment, container, false)

        imageView = v.findViewById(R.id.imageView)
        btn = v.findViewById(R.id.button)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BlankViewModel::class.java)
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