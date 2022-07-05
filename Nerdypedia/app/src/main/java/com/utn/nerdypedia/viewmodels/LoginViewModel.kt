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
import com.utn.nerdypedia.entities.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Firebase.firestore
    private var auth = Firebase.auth

    var flagLogIn = MutableLiveData<Boolean>(false)
    var flagSignIn = MutableLiveData<Boolean>(false)
    var viewState = MutableLiveData<ViewState>(ViewState.RESET)

    lateinit var failureText: String

    fun authUser(email : String, password : String){
        if (email.isEmpty()) {
            failureText = "Complete both fields"
            viewState.value = ViewState.FAILURE
            return
        }

        if (password.isEmpty()) {
            failureText = "The password you’ve entered is incorrect" //TODO poner igual que la de abajo
            viewState.value = ViewState.FAILURE
            return
        }

        viewModelScope.launch(Dispatchers.Main) {
            viewState.value = ViewState.LOADING

            val ret = signInUser(email, password)
            if(ret == null){
                val ret = auth.currentUser?.let { getUserFromDB(it.uid) }
                if(ret == null){
                    failureText = "Unable to get user"
                    viewState.value = ViewState.FAILURE
                } else {
                    Session.user = ret
                    flagLogIn.value = true
                }
//                Session.user = auth.currentUser?.let { getUserFromDB(it.uid) }!!
//                flagLogIn.value = true
            } else {
                failureText = ret.message.toString()
                viewState.value = ViewState.FAILURE
            }
        }
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
    }
}