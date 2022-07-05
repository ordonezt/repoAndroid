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
import com.utn.nerdypedia.entities.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignInViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Firebase.firestore
    private var auth = Firebase.auth

    var flagSignIn = MutableLiveData<Boolean>(false)
    var viewState = MutableLiveData<ViewState>(ViewState.RESET)

    lateinit var failureText: String


    fun signInUser(name:String, username:String, pass:String, pass2:String, email:String){
        if( name.isEmpty() or
            username.isEmpty() or
            pass.isEmpty() or
            pass2.isEmpty() or
            email.isEmpty()) {
            failureText = "Complete all fields"
            viewState.value = ViewState.FAILURE
        } else if(pass != pass2){
            failureText = "Both passwords must match"
            viewState.value = ViewState.FAILURE
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                viewState.value = ViewState.LOADING
                val ret = createUser(email, pass)
                if(ret == null) {
                    val firebaseUser = auth.currentUser
                    if(firebaseUser != null){
                        val user = User(firebaseUser.uid, name, username, email, "", "")
                        if(uploadUserDB(user)) {
                            Session.user =
                                user //El usuario que ingreso queda guardado en el singleton
                            flagSignIn.value = true
                        } else {
                            failureText = "Unable to upload user"
                            viewState.value = ViewState.FAILURE
                        }
                    } else {
                        failureText = "Unable to log in"
                        viewState.value = ViewState.FAILURE
                    }
                } else {
                    failureText = ret.message.toString()
                    viewState.value = ViewState.FAILURE
                }
            }
        }
    }

    private suspend fun createUser(email: String, password: String): Exception? {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            null
        } catch (e: Exception){
            //TODO
            Log.w("DB firestore", "Error creating user", e)
            e
        }
    }

    private suspend fun uploadUserDB(user: User): Boolean{
        return try{
            db.collection("users").add(user).await()
            true
        } catch (e: Exception){
            //TODO
            Log.w("DB firestore", "Error adding document", e)
            false
        }
    }
}