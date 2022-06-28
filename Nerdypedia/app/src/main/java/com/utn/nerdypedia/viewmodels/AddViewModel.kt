package com.utn.nerdypedia.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.nerdypedia.database.appDataBase
import com.utn.nerdypedia.database.scientistDao
import com.utn.nerdypedia.entities.Scientist
import com.utn.nerdypedia.entities.Session
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Firebase.firestore

    var titleText = MutableLiveData<String>("")
    var nameText = MutableLiveData<String>("")
    var urlText = MutableLiveData<String>("")

    var flagEmptyData = MutableLiveData<Boolean>(false)
    var flagExit = MutableLiveData<Boolean>(false)

    private fun setEditView(scientist:Scientist){
        titleText.value = "Edit"

        nameText.value = scientist.name
        urlText.value = scientist.biographyUrl
    }

    private fun setAddView(){
        titleText.value = "Add"
    }

    private fun addScientist(name:String, url:String){
        if(name.isEmpty() || url.isEmpty()){
            flagEmptyData.value = true
        }else{
            val currentTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            val date = currentTime.format(formatter)

            val author = Session.user.username
            val sct = Scientist(name, url, date, author)
            db.collection("scientists")
                .add(sct)
                .addOnSuccessListener { documentReference ->
                    Log.d("DB firestore", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("DB firestore", "Error adding document", e)
                }

            flagExit.value = true
        }
    }

    private fun editScientist(name:String, url:String, scientist: Scientist){
        if(name.isEmpty() || url.isEmpty()){
            flagEmptyData.value = true
        }else {
            scientist.name = name
            scientist.biographyUrl = url
            db.collection("scientists").document(scientist.id)
                .set(scientist)
                .addOnSuccessListener {
                    Log.d("DB firestore", "DocumentSnapshot added with ID: ${scientist.id}")
                    flagExit.value = true
                }
                .addOnFailureListener { e ->
                    Log.w("DB firestore", "Error adding document", e)
                    flagExit.value = true
                }
        }
    }

    fun saveScientist(name:String, url:String){
        if(Session.scientist == null){
            addScientist(name, url)
        } else {
            editScientist(name, url, Session.scientist!!)
        }
    }

    fun onStart(){
        flagEmptyData.value = false
        flagExit.value = false

        if(Session.scientist == null){
            setAddView()
        } else {
            setEditView(Session.scientist!!)
        }
    }

}