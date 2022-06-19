package com.utn.nerdypedia.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.utn.nerdypedia.database.appDataBase
import com.utn.nerdypedia.database.scientistDao
import com.utn.nerdypedia.entities.Scientist
import com.utn.nerdypedia.entities.Session
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddViewModel(application: Application) : AndroidViewModel(application) {
    private var db : appDataBase? = appDataBase.getAppDataBase(application.applicationContext)
    private var scientistDao : scientistDao? = db?.scientistDao()

    var titleText = MutableLiveData<String>("")
    var nameText = MutableLiveData<String>("")
    var urlText = MutableLiveData<String>("")

    var flagEmptyData = MutableLiveData<Boolean>(false)
    var flagExit = MutableLiveData<Boolean>(false)

    fun setEditView(scientist:Scientist){
        titleText.value = "Edit"

        nameText.value = scientist.name
        urlText.value = scientist.biographyUrl
    }

    fun setAddView(){
        titleText.value = "Add"
    }

    fun addScientist(name:String, url:String){
        if(name.isEmpty() || url.isEmpty()){
            flagEmptyData.value = true
        }else{
            val currentTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            val date = currentTime.format(formatter)

            val author = Session.user.username
            scientistDao?.insertScientist(Scientist(name, url, date, author))
            flagExit.value = true
        }
    }

    fun editScientist(name:String, url:String, scientist: Scientist){
        if(name.isEmpty() || url.isEmpty()){
            flagEmptyData.value = true
        }else {
            scientist.name = name
            scientist.biographyUrl = url
            scientistDao?.updateScientist(scientist)
            flagExit.value = true
        }
    }

    fun onStart(){
        flagEmptyData.value = false
        flagExit.value = false
    }

}