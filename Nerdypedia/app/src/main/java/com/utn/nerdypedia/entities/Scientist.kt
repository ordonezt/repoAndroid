package com.utn.nerdypedia.entities

import android.graphics.ColorSpace.Model
import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize
import com.google.firebase.firestore.DocumentId

@Parcelize
@IgnoreExtraProperties
class Scientist(
    @DocumentId
    var id: String,
    var name: String,
    var biographyUrl: String,
    var date: String,
    var author: String
) : Parcelable {
    constructor (name: String, biographyUrl: String, date: String, author: String) :
        this("", name, biographyUrl, date, author){}

    constructor() : this("", "", "", ""){}

//    fun withId(id: String): Scientist {
//        this.id = id
//        return this
//    }
}