package com.utn.nerdypedia.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.nerdypedia.entities.Session
import com.utn.nerdypedia.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Firebase.firestore
    private var auth = Firebase.auth

    var flagLogIn = MutableLiveData<Boolean>(false)
    var flagSignIn = MutableLiveData<Boolean>(false)
    var flagEmptyData = MutableLiveData<Boolean>(false)
    var flagUserUnknown = MutableLiveData<Boolean>(false)
    var wrongLogInText = MutableLiveData<String>("")

    fun authUser(email : String, password : String){
        if (email.isEmpty()) {
            wrongLogInText.value = "Complete both fields"
            return
        }

        if (password.isEmpty()) {
            wrongLogInText.value = "The password you’ve entered is incorrect" //TODO poner igual que la de abajo
            return
        }

        viewModelScope.launch(Dispatchers.Main) {
            var ret = signInUser(email, password)
            if(ret == null){
                //Session.user = auth.currentUser //El usuario que ingreso queda guardado en el singleton TODO
                Session.user = auth.currentUser?.let { getUserFromDB(it.uid) }!!
                flagLogIn.value = true
            } else {
                wrongLogInText.value = ret.message.toString()
            }
        }
//        val user = userDao?.loadUserByUsername(username)
//
//        if(user == null){
//            flagUserUnknown.value = true
//        } else if(password != user.password){
//            flagUserUnknown.value = true
//        } else {
//            Session.user = user //El usuario que ingreso queda guardado en el singleton
//            flagLogIn.value = true
//        }
    }
    private suspend fun signInUser(email: String, password: String) : Exception? {
        return try{
            auth.signInWithEmailAndPassword(email, password).await()
            null
        } catch (e: Exception){
            e
        }
    }

    private suspend fun getUserFromDB(uid: String) : User? {
        return try{
            val snapshot = db.collection("users").whereEqualTo("uid", uid).get().await()
            snapshot.first().toObject(User::class.java)
        } catch (e: Exception){
            null
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