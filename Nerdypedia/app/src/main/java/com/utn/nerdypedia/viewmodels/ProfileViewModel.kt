package com.utn.nerdypedia.viewmodels

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.utn.nerdypedia.entities.ProfileViewState
import com.utn.nerdypedia.entities.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val storageRef = Firebase.storage.reference

    var nameText        = MutableLiveData<String>("")
    var userNameText    = MutableLiveData<String>("")
    var emailText       = MutableLiveData<String>("")
    var viewState       = MutableLiveData<ProfileViewState>(ProfileViewState.RESET)

    var picURI: Uri = Uri.parse("@drawable/ic_baseline_person_24_black")
    lateinit var failtText : String

    var flagSettings = MutableLiveData<Boolean>(false)
    var flagGoToGallery = MutableLiveData<Boolean>(false)

    fun loadUserData(){
        nameText.value      = Session.user.name
        userNameText.value  = Session.user.username
        emailText.value     = Session.user.email

        viewModelScope.launch(Dispatchers.Main) {
            viewState.value = ProfileViewState.LOADING
            if(downloadUserPic()){
                viewState.value = ProfileViewState.SUCCESS
            } else {
                picURI = Uri.parse("@drawable/ic_baseline_person_24_black")
                failtText = "Unable to get profile picture"
                viewState.value = ProfileViewState.FAILURE
            }
        }
    }

    fun goToSettings(){
        flagSettings.value = true
    }

    fun onStart(){
        flagSettings.value = false
        flagGoToGallery.value = false

    }

    fun updatePic(){
        flagGoToGallery.value = true
    }

    fun processGalleryPick(uri : Uri){
        flagGoToGallery.value = false
        Log.d("Gallery pick", uri.toString())
        viewModelScope.launch(Dispatchers.Main) {
            viewState.value = ProfileViewState.LOADING
            if(uploadUserPic(uri)){
                picURI = uri
                viewState.value = ProfileViewState.SUCCESS
            }else{
                failtText = "Unable to set profile picture"
                viewState.value = ProfileViewState.FAILURE
            }
        }
    }

    private suspend fun uploadUserPic(uri: Uri): Boolean{
        val uid = Session.user.uid
        val imagesUserRef = storageRef.child("images/$uid")

        return try{
            imagesUserRef.putFile(uri).await()
            true
        } catch(e: Exception){
            false
        }
    }

    suspend fun downloadUserPic(): Boolean{
        val uid = Session.user.uid
        val imagesUserRef = storageRef.child("images/$uid")

        return try{
            picURI = imagesUserRef.downloadUrl.await()
            true
        } catch(e: Exception){
            Log.e("Error get", e.toString())
            false
        }
    }
}