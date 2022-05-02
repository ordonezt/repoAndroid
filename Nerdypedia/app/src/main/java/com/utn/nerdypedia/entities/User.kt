package com.utn.nerdypedia.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    var id : Int,
    var name : String,
    var password : String,
    var username : String
) : Parcelable {
}