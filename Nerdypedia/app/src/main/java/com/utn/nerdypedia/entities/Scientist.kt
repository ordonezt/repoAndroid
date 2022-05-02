package com.utn.nerdypedia.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Scientist (
    var name : String,
    var biographyUrl : String,
    var citizenship : String,
    var pictureUrl : String
) : Parcelable {
}