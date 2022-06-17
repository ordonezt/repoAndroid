package com.utn.nerdypedia.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utn.nerdypedia.entities.Session

class ProfileViewModel : ViewModel() {
    var nameText    = MutableLiveData<String>("")
    var userNameText  = MutableLiveData<String>("")
    var emailText    = MutableLiveData<String>("")

    var flagSettings = MutableLiveData<Boolean>(false)

    fun loadUserData(){
        nameText.value      = Session.user.name
        userNameText.value  = Session.user.username
        emailText.value     = Session.user.email
    }

    fun goToSettings(){
        flagSettings.value = true
    }

    fun onStart(){
        flagSettings.value = false
    }
}