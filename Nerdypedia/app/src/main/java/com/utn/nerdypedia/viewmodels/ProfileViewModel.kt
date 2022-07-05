package com.utn.nerdypedia.viewmodels

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.utn.nerdypedia.entities.Session

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    var nameText        = MutableLiveData<String>("")
    var userNameText    = MutableLiveData<String>("")
    var emailText       = MutableLiveData<String>("")
    var picURL          = MutableLiveData<String>("")

    var flagSettings = MutableLiveData<Boolean>(false)
    var flagRequestPermissons = MutableLiveData<Boolean>(false)
    var flagGoToGallery = MutableLiveData<Boolean>(false)

    fun loadUserData(){
        nameText.value      = Session.user.name
        userNameText.value  = Session.user.username
        emailText.value     = Session.user.email

        val url = "gs://electiva-android-2022.appspot.com/images/ryhVWxq8_400x400.jpg"//Session.user.photoUrl
        if(url.isNotEmpty()) {
            picURL.value = url
        }
    }

    fun goToSettings(){
        flagSettings.value = true
    }

    fun onStart(){
        flagSettings.value = false


    }

    fun updatePic(){
        if(!checkPermissions()){
            flagRequestPermissons.value = true
        } else {
            flagGoToGallery.value = true
        }
    }

    private fun checkPermissions() : Boolean{
        if (ActivityCompat.checkSelfPermission(
                getApplication<Application>().applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    fun processPermissionsResult(result : Boolean){
        if(result){
            updatePic()
        }
    }

    fun processGalleryPick(data : Uri?){
        Log.d("Gallery pick", data.toString())
        flagGoToGallery.value = false
    }
}