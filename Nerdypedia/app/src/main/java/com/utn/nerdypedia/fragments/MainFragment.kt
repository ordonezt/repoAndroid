package com.utn.nerdypedia.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.utn.nerdypedia.R
import com.utn.nerdypedia.adapters.ScientistAdapter
import com.utn.nerdypedia.database.scientistDao
import com.utn.nerdypedia.database.appDataBase
import com.utn.nerdypedia.entities.Scientist
import com.utn.nerdypedia.entities.Session
import com.utn.nerdypedia.viewmodels.MainViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var nameTextView: TextView
    private lateinit var recyclerScientists : RecyclerView

    private lateinit var scientistAdapter : ScientistAdapter
    private var db : appDataBase? = null
    private var scientistDao : scientistDao? = null

    private lateinit var fab : FloatingActionButton

    private lateinit var list: MutableList<Scientist?>
    private lateinit var listEmptyText: TextView

    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.main_fragment, container, false)

        nameTextView = v.findViewById(R.id.nameTextView)
        recyclerScientists = v.findViewById(R.id.recyclerScientists)
        fab = v.findViewById(R.id.fab)
        listEmptyText = v.findViewById(R.id.listEmptyText)

        return v
    }

    override fun onStart() {
        super.onStart()

        recyclerScientists.setHasFixedSize(true)
        recyclerScientists.layoutManager = LinearLayoutManager(context)

        db = appDataBase.getAppDataBase(v.context)
        scientistDao = db?.scientistDao()

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        //Si es la primera vez que arranca la app, inicializamos la base de datos
        if(prefs.getBoolean("firstRun", true)){
            val editor = prefs.edit()
            editor.putBoolean("firstRun", false)

            initScientistDataBase(scientistDao!!)
        }

        val clickCard = fun (scientist : Scientist?) {
            val action = scientist?.let {
                MainFragmentDirections.actionMainFragmentToDetailsFragment(
                    it
                )
            }
            if (action != null) {
                Session.scientist = scientist
                v.findNavController().navigate(action)
            }
        }
        val clickEdit = fun (scientist : Scientist?){
            var action = MainFragmentDirections.actionMainFragmentToAddFragment(scientist)
            v.findNavController().navigate(action)
        }

        val clickDelete = fun (scientist : Scientist?) {
            scientistDao?.deleteScientist(scientist)
            onStart()
        }

        if(prefs.getBoolean("showPrivate", false)) {
            list = scientistDao?.loadScientistByAuthor(Session.user.username)!!
        } else {
            list = scientistDao?.loadAllScientist()!!
        }
        scientistAdapter = ScientistAdapter(list, clickCard, clickEdit, clickDelete)
        recyclerScientists.adapter = scientistAdapter

        if(scientistAdapter.itemCount > 0){
            listEmptyText.visibility = View.INVISIBLE
            recyclerScientists.visibility = View.VISIBLE
        } else {
            listEmptyText.visibility = View.VISIBLE
            recyclerScientists.visibility = View.INVISIBLE
        }

        nameTextView.text = Session.user.name + '!'

        fab.setOnClickListener{
            var action = MainFragmentDirections.actionMainFragmentToAddFragment(null)
            v.findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun initScientistDataBase(dao : scientistDao) {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val date = currentTime.format(formatter)

        dao?.insertScientist(Scientist( "Albert Einstein","https://en.wikipedia.org/wiki/Albert_Einstein", date, "admin"))
        dao?.insertScientist(Scientist( "Stephen Hawking","https://en.wikipedia.org/wiki/Stephen_Hawking", date, "admin"))
        dao?.insertScientist(Scientist( "James Clerk Maxwell","https://en.wikipedia.org/wiki/James_Clerk_Maxwell", date, "admin"))
        dao?.insertScientist(Scientist("Marie Curie","https://en.wikipedia.org/wiki/Marie_Curie", date, "admin"))
        dao?.insertScientist(Scientist("Paul Dirac","https://en.wikipedia.org/wiki/Paul_Dirac", date, "admin"))
        dao?.insertScientist(Scientist("Carl Friedrich Gauss","https://en.wikipedia.org/wiki/Carl_Friedrich_Gauss", date, "admin"))
        dao?.insertScientist(Scientist("Bernhard Riemann","https://en.wikipedia.org/wiki/Bernhard_Riemann", date, "admin"))
        dao?.insertScientist(Scientist("Max Planck","https://en.wikipedia.org/wiki/Max_Planck", date, "admin"))
        dao?.insertScientist(Scientist("Erwin Schrödinger","https://en.wikipedia.org/wiki/Erwin_Schr%C3%B6dinger", date, "admin"))
        dao?.insertScientist(Scientist("Richard Feynman","https://en.wikipedia.org/wiki/Richard_Feynman", date, "admin"))
        dao?.insertScientist(Scientist("Werner Heisenberg","https://en.wikipedia.org/wiki/Werner_Heisenberg", date, "admin"))
        dao?.insertScientist(Scientist("Enrico Fermi","https://en.wikipedia.org/wiki/Enrico_Fermi", date, "admin"))
        dao?.insertScientist(Scientist("Niels Bohr","https://en.wikipedia.org/wiki/Niels_Bohr", date, "admin"))
        dao?.insertScientist(Scientist("Ernest Rutherford","https://en.wikipedia.org/wiki/Ernest_Rutherford", date, "admin"))
        dao?.insertScientist(Scientist("Michael Faraday","https://en.wikipedia.org/wiki/Michael_Faraday", date, "admin"))
        dao?.insertScientist(Scientist("Nikola Tesla","https://en.wikipedia.org/wiki/Nikola_Tesla", date, "admin"))
        dao?.insertScientist(Scientist("Thomas Edison","https://en.wikipedia.org/wiki/Thomas_Edison", date, "admin"))
        dao?.insertScientist(Scientist("Galileo Galilei","https://en.wikipedia.org/wiki/Galileo_Galilei", date, "admin"))
        dao?.insertScientist(Scientist("Isaac Newton","https://en.wikipedia.org/wiki/Isaac_Newton", date, "admin"))
        dao?.insertScientist(Scientist("Alan Turing","https://en.wikipedia.org/wiki/Alan_Turing", date, "admin"))
        dao?.insertScientist(Scientist("Pierre de Fermat","https://en.wikipedia.org/wiki/Pierre_de_Fermat", date, "admin"))
        dao?.insertScientist(Scientist("J. Robert Oppenheimer","https://en.wikipedia.org/wiki/J._Robert_Oppenheimer", date, "admin"))
    }
}