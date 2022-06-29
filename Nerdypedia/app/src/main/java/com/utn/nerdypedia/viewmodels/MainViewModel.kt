package com.utn.nerdypedia.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.nerdypedia.adapters.ScientistAdapter
import com.utn.nerdypedia.entities.Scientist
import com.utn.nerdypedia.entities.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Firebase.firestore

    var prefs: SharedPreferences =
        application.getSharedPreferences("preference_key", Context.MODE_PRIVATE)

    var nameText = MutableLiveData<String>("")

    var flagGoDetails = MutableLiveData<Boolean>(false)
    var flagGoEdit = MutableLiveData<Boolean>(false)
    var flagGoAdd = MutableLiveData<Boolean>(false)

    var scientistAdapter = MutableLiveData<ScientistAdapter>()

    fun onStart(){
        flagGoEdit.value = false
        flagGoAdd.value = false
        flagGoDetails.value = false

        nameText.value = Session.user.name + '!'

        //Si es la primera vez que arranca la app, inicializamos la base de datos
        if(prefs.getBoolean("firstRun", true)){
            val editor = prefs.edit()
            editor.putBoolean("firstRun", false)

//            initScientistDataBase()
        }

        updateScientistAdapter()
    }

    private fun updateScientistAdapter() {
        val clickCard = fun(scientist: Scientist?) {
            if (scientist != null) {
                Session.scientist = scientist
            }
            flagGoDetails.value = true
        }
        val clickEdit = fun(scientist: Scientist?) {
            if (scientist != null) {
                Session.scientist = scientist
            }
            flagGoEdit.value = true
        }

        val clickDelete = fun(scientist: Scientist?) {
            if (scientist != null) {
                viewModelScope.launch(Dispatchers.Main) {
                    deleteScientistFromDB(scientist)
                    onStart()
                }
            }
        }

        var query: Query =
            if (prefs.getBoolean("showPrivate", false)) {//TODO no anda, siempre va por false wtf
                db.collection("scientists")
                    .whereEqualTo("author", Session.user.username)
            } else {
                db.collection("scientists")
            }
        viewModelScope.launch(Dispatchers.Main) {
            var list = getScientistListFromQuery(query)
            scientistAdapter.value = ScientistAdapter(list, clickCard, clickEdit, clickDelete)
        }
    }

    private suspend fun getScientistListFromQuery(query: Query): MutableList<Scientist?> {
        var list = mutableListOf<Scientist?>()
        try{
            val snapshot = query.get().await()
            list = snapshotToScientistList(snapshot)
        } catch (e: Exception) {
            //TODO
            Log.w("DB firestore", "Error deleting document", e)
        }
        return list
    }

    private fun snapshotToScientistList(snapshot: QuerySnapshot) : MutableList<Scientist?> {
        val list = mutableListOf<Scientist?>()
        for (doc in snapshot) {
            list.add(doc.toObject(Scientist::class.java))
        }
        return list
    }

    fun addItem(){
        Session.scientist = null
        flagGoAdd.value = true
    }

    suspend fun addScientistToDB(scientist: Scientist){
        try{
            db.collection("scientists").add(scientist).await()
        } catch (e: Exception){
            //TODO
            Log.w("DB firestore", "Error adding document", e)
        }
    }

    private fun initScientistDataBase() {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val date = currentTime.format(formatter)

        viewModelScope.launch(Dispatchers.Main) {
            addScientistToDB(Scientist( "Albert Einstein","https://en.wikipedia.org/wiki/Albert_Einstein", date, "admin"))
            addScientistToDB(Scientist( "Stephen Hawking","https://en.wikipedia.org/wiki/Stephen_Hawking", date, "admin"))
            addScientistToDB(Scientist( "James Clerk Maxwell","https://en.wikipedia.org/wiki/James_Clerk_Maxwell", date, "admin"))
            addScientistToDB(Scientist("Marie Curie","https://en.wikipedia.org/wiki/Marie_Curie", date, "admin"))
            addScientistToDB(Scientist("Paul Dirac","https://en.wikipedia.org/wiki/Paul_Dirac", date, "admin"))
            addScientistToDB(Scientist("Carl Friedrich Gauss","https://en.wikipedia.org/wiki/Carl_Friedrich_Gauss", date, "admin"))
            addScientistToDB(Scientist("Bernhard Riemann","https://en.wikipedia.org/wiki/Bernhard_Riemann", date, "admin"))
            addScientistToDB(Scientist("Max Planck","https://en.wikipedia.org/wiki/Max_Planck", date, "admin"))
            addScientistToDB(Scientist("Erwin Schrödinger","https://en.wikipedia.org/wiki/Erwin_Schr%C3%B6dinger", date, "admin"))
            addScientistToDB(Scientist("Richard Feynman","https://en.wikipedia.org/wiki/Richard_Feynman", date, "admin"))
            addScientistToDB(Scientist("Werner Heisenberg","https://en.wikipedia.org/wiki/Werner_Heisenberg", date, "admin"))
            addScientistToDB(Scientist("Enrico Fermi","https://en.wikipedia.org/wiki/Enrico_Fermi", date, "admin"))
            addScientistToDB(Scientist("Niels Bohr","https://en.wikipedia.org/wiki/Niels_Bohr", date, "admin"))
            addScientistToDB(Scientist("Ernest Rutherford","https://en.wikipedia.org/wiki/Ernest_Rutherford", date, "admin"))
            addScientistToDB(Scientist("Michael Faraday","https://en.wikipedia.org/wiki/Michael_Faraday", date, "admin"))
            addScientistToDB(Scientist("Nikola Tesla","https://en.wikipedia.org/wiki/Nikola_Tesla", date, "admin"))
            addScientistToDB(Scientist("Thomas Edison","https://en.wikipedia.org/wiki/Thomas_Edison", date, "admin"))
            addScientistToDB(Scientist("Galileo Galilei","https://en.wikipedia.org/wiki/Galileo_Galilei", date, "admin"))
            addScientistToDB(Scientist("Isaac Newton","https://en.wikipedia.org/wiki/Isaac_Newton", date, "admin"))
            addScientistToDB(Scientist("Alan Turing","https://en.wikipedia.org/wiki/Alan_Turing", date, "admin"))
            addScientistToDB(Scientist("Pierre de Fermat","https://en.wikipedia.org/wiki/Pierre_de_Fermat", date, "admin"))
            addScientistToDB(Scientist("J. Robert Oppenheimer","https://en.wikipedia.org/wiki/J._Robert_Oppenheimer", date, "admin"))
        }
    }

    suspend fun deleteScientistFromDB(scientist: Scientist){
        try{
            db.collection("scientists").document(scientist.id)
                .delete().await()
        } catch (e: Exception) {
            //TODO
            Log.w("DB firestore", "Error deleting document", e)
        }
    }
}