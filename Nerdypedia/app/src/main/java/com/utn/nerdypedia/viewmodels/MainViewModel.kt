package com.utn.nerdypedia.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.utn.nerdypedia.adapters.ScientistAdapter
import com.utn.nerdypedia.database.appDataBase
import com.utn.nerdypedia.database.scientistDao
import com.utn.nerdypedia.entities.Scientist
import com.utn.nerdypedia.entities.Session
import com.utn.nerdypedia.fragments.MainFragmentDirections
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var db : appDataBase? = appDataBase.getAppDataBase(application.applicationContext)
    private var scientistDao : scientistDao? = db?.scientistDao()

    var prefs: SharedPreferences =
        application.getSharedPreferences("preference_key", Context.MODE_PRIVATE)

    var nameText = MutableLiveData<String>("")

    var flagGoDetails = MutableLiveData<Boolean>(false)
    var flagGoEdit = MutableLiveData<Boolean>(false)
    var flagGoAdd = MutableLiveData<Boolean>(false)

    private lateinit var list: MutableList<Scientist?>
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

            initScientistDataBase(scientistDao!!)
        }

        val clickCard = fun (scientist : Scientist?) {
            if (scientist != null) {
                Session.scientist = scientist
            }
            flagGoDetails.value = true
        }
        val clickEdit = fun (scientist : Scientist?){
            if (scientist != null) {
                Session.scientist = scientist
            }
            flagGoEdit.value = true
        }

        val clickDelete = fun (scientist : Scientist?) {
            scientistDao?.deleteScientist(scientist)
            onStart()
        }

        if(prefs.getBoolean("showPrivate", false)) {
            list = scientistDao?.loadScientistByAuthor(Session.user.username)!!
        } else {
            list = scientistDao?.loadAllScientist()!!
        }
        scientistAdapter.value = ScientistAdapter(list, clickCard, clickEdit, clickDelete)
    }

    fun addItem(){
        Session.scientist = null
        flagGoAdd.value = true
    }

    private fun initScientistDataBase(dao : scientistDao) {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val date = currentTime.format(formatter)

        dao.insertScientist(Scientist( "Albert Einstein","https://en.wikipedia.org/wiki/Albert_Einstein", date, "admin"))
        dao.insertScientist(Scientist( "Stephen Hawking","https://en.wikipedia.org/wiki/Stephen_Hawking", date, "admin"))
        dao.insertScientist(Scientist( "James Clerk Maxwell","https://en.wikipedia.org/wiki/James_Clerk_Maxwell", date, "admin"))
        dao.insertScientist(Scientist("Marie Curie","https://en.wikipedia.org/wiki/Marie_Curie", date, "admin"))
        dao.insertScientist(Scientist("Paul Dirac","https://en.wikipedia.org/wiki/Paul_Dirac", date, "admin"))
        dao.insertScientist(Scientist("Carl Friedrich Gauss","https://en.wikipedia.org/wiki/Carl_Friedrich_Gauss", date, "admin"))
        dao.insertScientist(Scientist("Bernhard Riemann","https://en.wikipedia.org/wiki/Bernhard_Riemann", date, "admin"))
        dao.insertScientist(Scientist("Max Planck","https://en.wikipedia.org/wiki/Max_Planck", date, "admin"))
        dao.insertScientist(Scientist("Erwin Schrödinger","https://en.wikipedia.org/wiki/Erwin_Schr%C3%B6dinger", date, "admin"))
        dao.insertScientist(Scientist("Richard Feynman","https://en.wikipedia.org/wiki/Richard_Feynman", date, "admin"))
        dao.insertScientist(Scientist("Werner Heisenberg","https://en.wikipedia.org/wiki/Werner_Heisenberg", date, "admin"))
        dao.insertScientist(Scientist("Enrico Fermi","https://en.wikipedia.org/wiki/Enrico_Fermi", date, "admin"))
        dao.insertScientist(Scientist("Niels Bohr","https://en.wikipedia.org/wiki/Niels_Bohr", date, "admin"))
        dao.insertScientist(Scientist("Ernest Rutherford","https://en.wikipedia.org/wiki/Ernest_Rutherford", date, "admin"))
        dao.insertScientist(Scientist("Michael Faraday","https://en.wikipedia.org/wiki/Michael_Faraday", date, "admin"))
        dao.insertScientist(Scientist("Nikola Tesla","https://en.wikipedia.org/wiki/Nikola_Tesla", date, "admin"))
        dao.insertScientist(Scientist("Thomas Edison","https://en.wikipedia.org/wiki/Thomas_Edison", date, "admin"))
        dao.insertScientist(Scientist("Galileo Galilei","https://en.wikipedia.org/wiki/Galileo_Galilei", date, "admin"))
        dao.insertScientist(Scientist("Isaac Newton","https://en.wikipedia.org/wiki/Isaac_Newton", date, "admin"))
        dao.insertScientist(Scientist("Alan Turing","https://en.wikipedia.org/wiki/Alan_Turing", date, "admin"))
        dao.insertScientist(Scientist("Pierre de Fermat","https://en.wikipedia.org/wiki/Pierre_de_Fermat", date, "admin"))
        dao.insertScientist(Scientist("J. Robert Oppenheimer","https://en.wikipedia.org/wiki/J._Robert_Oppenheimer", date, "admin"))
    }
}