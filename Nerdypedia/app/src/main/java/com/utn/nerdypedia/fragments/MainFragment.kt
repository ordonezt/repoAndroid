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
import com.utn.nerdypedia.entities.ScientistRepository
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
    private var repository : ScientistRepository = ScientistRepository()
    private var db : appDataBase? = null
    private var scientistDao : scientistDao? = null

    private lateinit var fab : FloatingActionButton

    private lateinit var list: MutableList<Scientist?>

    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.main_fragment, container, false)

        nameTextView = v.findViewById(R.id.nameTextView)
        recyclerScientists = v.findViewById(R.id.recyclerScientists)
        fab = v.findViewById(R.id.fab)

        return v
    }

    override fun onStart() {
        super.onStart()

        recyclerScientists.setHasFixedSize(true)
        recyclerScientists.layoutManager = LinearLayoutManager(context)

        db = appDataBase.getAppDataBase(v.context)
        scientistDao = db?.scientistDao()

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

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

        nameTextView.text = Session.user.name + '!'

        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val date = currentTime.format(formatter)
        val scientist = Scientist(
            "Albert Einstein",
            "https://en.wikipedia.org/wiki/Albert_Einstein",
            date,
            "admin"
        )

        scientistDao?.insertScientist(scientist)

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

}