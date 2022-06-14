package com.utn.nerdypedia.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utn.nerdypedia.database.appDataBase
import com.utn.nerdypedia.database.userDao
import com.utn.nerdypedia.entities.Session

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private var db : appDataBase? = appDataBase.getAppDataBase(application.applicationContext)
    private var userDao : userDao? = db?.userDao()

    var flagLogIn = MutableLiveData<Boolean>(false)
    var flagSignIn = MutableLiveData<Boolean>(false)
    var flagEmptyData = MutableLiveData<Boolean>(false)
    var flagUserUnknown = MutableLiveData<Boolean>(false)

    fun authUser(username : String, password : String){
        if (username.isEmpty()) {
            flagEmptyData.value = true
            return
        }

        if (password.isEmpty()) {
            flagEmptyData.value = true
            return
        }

        val user = userDao?.loadUserByUsername(username)

        if(user == null){
            flagUserUnknown.value = true
        } else if(password != user.password){
            flagUserUnknown.value = true
        } else {
            Session.user = user //El usuario que ingreso queda guardado en el singleton
            flagLogIn.value = true
        }
    }

    fun signIn(){
        flagSignIn.value = true
    }

    fun onStart(){
        flagLogIn.value = false
        flagSignIn.value = false
        flagEmptyData.value = false
        flagUserUnknown.value = false
    }
}