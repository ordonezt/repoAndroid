package com.utn.nerdypedia.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utn.nerdypedia.entities.Session

class PicViewModel : ViewModel() {
    var nameText    = MutableLiveData<String>("")
    var authorText  = MutableLiveData<String>("")
    var dateText    = MutableLiveData<String>("")
    var urlText     = MutableLiveData<String>("")

    fun loadItemData(){
        nameText.value      = "Name: "      + Session.scientist.name
        authorText.value    = "Author: "    + Session.scientist.author
        dateText.value      = "Date: "      + Session.scientist.date
        urlText.value       = "URL: "       + Session.scientist.biographyUrl
    }
}