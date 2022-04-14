package com.utn.clase2.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    var name : String,
    var password : String,
    var username : String
) : Parcelable {
}