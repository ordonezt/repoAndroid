package com.utn.nerdypedia.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    var uid: String,
    var name: String,
    var username: String,
    var email: String,
    var photoUrl: String,
    var settingsUrl: String) : Parcelable
{
    constructor() : this("", "", "", "", "", ""){}
}