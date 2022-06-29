package com.utn.nerdypedia.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.nerdypedia.adapters.ScientistAdapter
import com.utn.nerdypedia.database.appDataBase
import com.utn.nerdypedia.database.scientistDao
import com.utn.nerdypedia.entities.Scientist
import com.utn.nerdypedia.entities.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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
            val scientist = Scientist(name, url, date, author)
            viewModelScope.launch(Dispatchers.Main) {
                addScientistToDB(scientist)
                flagExit.value = true
                //TODO hacer la opcion de error
            }
        }
    }

    private fun editScientist(name:String, url:String, scientist: Scientist){
        if(name.isEmpty() || url.isEmpty()){
            flagEmptyData.value = true
        }else {
            scientist.name = name
            scientist.biographyUrl = url
            viewModelScope.launch(Dispatchers.Main) {
                updateScientistToDB(scientist)
                flagExit.value = true
                //TODO hacer la opcion de error
            }
        }
    }

    private suspend fun addScientistToDB(scientist: Scientist){
        try{
            db.collection("scientists").add(scientist).await()
        } catch (e: Exception){
            //TODO
            Log.w("DB firestore", "Error adding document", e)
        }
    }

    suspend fun updateScientistToDB(scientist: Scientist){
        try{
            db.collection("scientists").document(scientist.id).set(scientist).await()
        } catch (e: Exception){
            //TODO
            Log.w("DB firestore", "Error adding document", e)
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