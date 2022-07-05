package com.utn.testgallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
//    private lateinit var imageView: ImageView
//    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        imageView = findViewById(R.id.imageView)
//        btn = findViewById(R.id.button)
//
//        btn.setOnClickListener{
//            openFileChooser()
//        }
    }


//    private val fileChooserContract = registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
//        if (imageUri != null) {
//            // imageUri now contains URI to selected image
//            imageView.setImageURI(imageUri)
//        }
//    }
//
//    // ...
//
//    fun openFileChooser() {
//        fileChooserContract.launch("image/*")
//    }
}