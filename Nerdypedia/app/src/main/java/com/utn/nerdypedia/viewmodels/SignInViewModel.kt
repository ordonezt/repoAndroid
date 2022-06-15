package com.utn.nerdypedia.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.utn.nerdypedia.database.appDataBase
import com.utn.nerdypedia.database.userDao
import com.utn.nerdypedia.entities.Session
import com.utn.nerdypedia.entities.User

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    private var db : appDataBase? = appDataBase.getAppDataBase(application.applicationContext)
    private var userDao : userDao? = db?.userDao()

    var flagPassError = MutableLiveData<Boolean>(false)
    var flagSignIn = MutableLiveData<Boolean>(false)
    var flagEmptyData = MutableLiveData<Boolean>(false)
    var flagUserRepeated = MutableLiveData<Boolean>(false)

    fun signInUser(name:String, username:String, pass:String, pass2:String, email:String){
        if( name.isEmpty() or
            username.isEmpty() or
            pass.isEmpty() or
            pass2.isEmpty() or
            email.isEmpty()) {
            flagEmptyData.value = true
        } else if(pass != pass2){
            flagPassError.value = true
        } else if (userDao?.loadUserByUsername(username) != null){
            flagUserRepeated.value = true
        } else {
            val user = User(name, pass, username, email)

            userDao?.insertUser(user)

            Session.user = user //El usuario que ingreso queda guardado en el singleton
            flagSignIn.value = true
        }
    }
}