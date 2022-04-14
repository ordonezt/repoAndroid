package com.utn.miprimerapp.entities

class Movie(
    private var title : String,
    private var urlImage : String,
    private var description : String,
    private var year: Int
) {
    fun showMovieInfo() : String {
        return this.title + this.year
    }
}