package com.utn.nerdypedia.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "scientist", indices = [Index(value = ["name"], unique = true)])
class Scientist(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "biographyUrl")
    var biographyUrl: String,

    @ColumnInfo(name = "citizenship")
    var citizenship: String,

    @ColumnInfo(name = "pictureUrl")
    var pictureUrl: String) : Parcelable
{
    constructor(name: String, biographyUrl: String, citizenship: String, pictureUrl: String) : this(0,name, biographyUrl, citizenship, pictureUrl) {

    }
}