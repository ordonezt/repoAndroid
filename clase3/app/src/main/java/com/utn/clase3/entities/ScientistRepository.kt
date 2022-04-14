package com.utn.clase3.entities

class ScientistRepository {
    private var scientistList : MutableList<Scientist> = mutableListOf()

    init{
        scientistList.add(Scientist("Albert Einstein","https://en.wikipedia.org/wiki/Albert_Einstein","German", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3e/Einstein_1921_by_F_Schmutzer_-_restoration.jpg/800px-Einstein_1921_by_F_Schmutzer_-_restoration.jpg"))
        scientistList.add(Scientist("Stephen Hawking","https://en.wikipedia.org/wiki/Stephen_Hawking","English", "https://upload.wikimedia.org/wikipedia/commons/e/eb/Stephen_Hawking.StarChild.jpg"))
        scientistList.add(Scientist("James Clerk Maxwell","https://en.wikipedia.org/wiki/James_Clerk_Maxwell","Scottish", "https://upload.wikimedia.org/wikipedia/commons/5/57/James_Clerk_Maxwell.png"))
        scientistList.add(Scientist("Marie Curie","https://en.wikipedia.org/wiki/Marie_Curie","Polish", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c8/Marie_Curie_c._1920s.jpg/800px-Marie_Curie_c._1920s.jpg"))
        scientistList.add(Scientist("Paul Dirac","https://en.wikipedia.org/wiki/Paul_Dirac","British", "https://upload.wikimedia.org/wikipedia/commons/5/50/Paul_Dirac%2C_1933.jpg"))
        scientistList.add(Scientist("Carl Friedrich Gauss","https://en.wikipedia.org/wiki/Carl_Friedrich_Gauss","German", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Carl_Friedrich_Gauss_1840_by_Jensen.jpg/800px-Carl_Friedrich_Gauss_1840_by_Jensen.jpg"))
        scientistList.add(Scientist("Bernhard Riemann","https://en.wikipedia.org/wiki/Bernhard_Riemann","German", "https://upload.wikimedia.org/wikipedia/commons/8/82/Georg_Friedrich_Bernhard_Riemann.jpeg"))
        scientistList.add(Scientist("Max Planck","https://en.wikipedia.org/wiki/Max_Planck","German", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c7/Max_Planck_1933.jpg/800px-Max_Planck_1933.jpg"))
        scientistList.add(Scientist("Erwin Schrödinger","https://en.wikipedia.org/wiki/Erwin_Schr%C3%B6dinger","Austrian", "https://upload.wikimedia.org/wikipedia/commons/2/2e/Erwin_Schr%C3%B6dinger_%281933%29.jpg"))
        scientistList.add(Scientist("Richard Feynman","https://en.wikipedia.org/wiki/Richard_Feynman","American", "https://upload.wikimedia.org/wikipedia/en/4/42/Richard_Feynman_Nobel.jpg"))
        scientistList.add(Scientist("Werner Heisenberg","https://en.wikipedia.org/wiki/Werner_Heisenberg","German", "https://upload.wikimedia.org/wikipedia/commons/f/f8/Bundesarchiv_Bild183-R57262%2C_Werner_Heisenberg.jpg"))
        scientistList.add(Scientist("Enrico Fermi","https://en.wikipedia.org/wiki/Enrico_Fermi","Italian", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Enrico_Fermi_1943-49.jpg/800px-Enrico_Fermi_1943-49.jpg"))
        scientistList.add(Scientist("Niels Bohr","https://en.wikipedia.org/wiki/Niels_Bohr","Danish", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Niels_Bohr.jpg/800px-Niels_Bohr.jpg"))
        scientistList.add(Scientist("Ernest Rutherford","https://en.wikipedia.org/wiki/Ernest_Rutherford","New Zealand", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f8/Sir_Ernest_Rutherford_LCCN2014716719_-_restoration1.jpg/800px-Sir_Ernest_Rutherford_LCCN2014716719_-_restoration1.jpg"))
        scientistList.add(Scientist("Michael Faraday","https://en.wikipedia.org/wiki/Michael_Faraday","English", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/19/Michael_Faraday._Photograph_by_Maull_%26_Polyblank._Wellcome_V0026348.jpg/800px-Michael_Faraday._Photograph_by_Maull_%26_Polyblank._Wellcome_V0026348.jpg"))
        scientistList.add(Scientist("Nikola Tesla","https://en.wikipedia.org/wiki/Nikola_Tesla","Serbian", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/79/Tesla_circa_1890.jpeg/800px-Tesla_circa_1890.jpeg"))
        scientistList.add(Scientist("Thomas Edison","https://en.wikipedia.org/wiki/Thomas_Edison","American", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/Thomas_Edison2.jpg/800px-Thomas_Edison2.jpg"))
        scientistList.add(Scientist("Galileo Galilei","https://en.wikipedia.org/wiki/Galileo_Galilei","Italian", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Justus_Sustermans_-_Portrait_of_Galileo_Galilei%2C_1636.jpg/800px-Justus_Sustermans_-_Portrait_of_Galileo_Galilei%2C_1636.jpg"))
        scientistList.add(Scientist("Isaac Newton","https://en.wikipedia.org/wiki/Isaac_Newton","English", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/Portrait_of_Sir_Isaac_Newton%2C_1689.jpg/800px-Portrait_of_Sir_Isaac_Newton%2C_1689.jpg"))
        scientistList.add(Scientist("Alan Turing","https://en.wikipedia.org/wiki/Alan_Turing","English", "https://upload.wikimedia.org/wikipedia/commons/a/a1/Alan_Turing_Aged_16.jpg"))
        scientistList.add(Scientist("Pierre de Fermat","https://en.wikipedia.org/wiki/Pierre_de_Fermat","French", "https://upload.wikimedia.org/wikipedia/commons/f/f3/Pierre_de_Fermat.jpg"))
        scientistList.add(Scientist("J. Robert Oppenheimer","https://en.wikipedia.org/wiki/J._Robert_Oppenheimer","American", "https://upload.wikimedia.org/wikipedia/commons/0/03/JROppenheimer-LosAlamos.jpg"))
    }

    fun getMovies () : MutableList<Scientist>{
        return scientistList
    }
}