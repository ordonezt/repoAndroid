package com.utn.clase3.entities

class ScientistRepository {
    private var scientistList : MutableList<Scientist> = mutableListOf()

    init{
        scientistList.add(Scientist("Albert Einstein","https://en.wikipedia.org/wiki/Albert_Einstein","German", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3e/Einstein_1921_by_F_Schmutzer_-_restoration.jpg/800px-Einstein_1921_by_F_Schmutzer_-_restoration.jpg"))
        scientistList.add(Scientist("Stephen Hawking","https://en.wikipedia.org/wiki/Stephen_Hawking","English", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Stephen_Hawking.StarChild.jpg"))
        scientistList.add(Scientist("James Clerk Maxwell","https://en.wikipedia.org/wiki/James_Clerk_Maxwell","Scottish", "https://upload.wikimedia.org/wikipedia/commons/5/57/James_Clerk_Maxwell.png"))
        scientistList.add(Scientist("Marie Curie","https://en.wikipedia.org/wiki/Marie_Curie","Polish", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c8/Marie_Curie_c._1920s.jpg/800px-Marie_Curie_c._1920s.jpg"))
    }

    fun getMovies () : MutableList<Scientist>{
        return scientistList
    }
}