package com.utn.nerdypedia.viewmodels

import android.app.Application
import android.util.Log
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

class SignInViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Firebase.firestore
    private var auth = Firebase.auth

    var flagPassError = MutableLiveData<Boolean>(false)
    var flagSignIn = MutableLiveData<Boolean>(false)
    var flagEmptyData = MutableLiveData<Boolean>(false)
    var wrongSignInText = MutableLiveData<String>("")


    fun signInUser(name:String, username:String, pass:String, pass2:String, email:String){
        if( name.isEmpty() or
            username.isEmpty() or
            pass.isEmpty() or
            pass2.isEmpty() or
            email.isEmpty()) {
            flagEmptyData.value = true
            wrongSignInText.value = "Complete all fields"
        } else if(pass != pass2){
            flagPassError.value = true
            wrongSignInText.value = "Both passwords must match"
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                val ret = createUser(email, pass)
                if(ret == null) {
                    val firebaseUser = auth.currentUser
                    if(firebaseUser != null){
                        val user = User(firebaseUser.uid, name, username, email, "", "")
                        uploadUserDB(user)
                        Session.user = user //El usuario que ingreso queda guardado en el singleton
                        flagSignIn.value = true
                    }
                } else {
                    wrongSignInText.value = ret.message.toString()
                }
            }
        }
    }

    private suspend fun createUser(email: String, password: String): Exception? {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            return null
        } catch (e: Exception){
            //TODO
            Log.w("DB firestore", "Error creating user", e)
            return e
        }
    }

    private suspend fun uploadUserDB(user: User){
        try{
            db.collection("users").add(user).await()
        } catch (e: Exception){
            //TODO
            Log.w("DB firestore", "Error adding document", e)
        }
    }

}