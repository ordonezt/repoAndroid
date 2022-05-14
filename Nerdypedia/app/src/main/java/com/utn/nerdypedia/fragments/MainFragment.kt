package com.utn.nerdypedia.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.utn.nerdypedia.R
import com.utn.nerdypedia.adapters.ScientistAdapter
import com.utn.nerdypedia.database.scientistDao
import com.utn.nerdypedia.database.scientistsDataBase
import com.utn.nerdypedia.entities.Scientist
import com.utn.nerdypedia.entities.ScientistRepository
import com.utn.nerdypedia.viewmodels.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var nameTextView: TextView
    private lateinit var recyclerScientists : RecyclerView

    private lateinit var scientistAdapter : ScientistAdapter
    private var repository : ScientistRepository = ScientistRepository()
    private var db : scientistsDataBase? = null
    private var scientistDao : scientistDao? = null

    private lateinit var fab : FloatingActionButton

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

        db = scientistsDataBase.getAppDataBase(v.context)
        scientistDao = db?.scientistDao()

        val clickCard = fun (scientist : Scientist?) {
            val action = scientist?.let {
                MainFragmentDirections.actionMainFragmentToDetailsFragment(
                    it
                )
            }
            if (action != null) {
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
        scientistAdapter = ScientistAdapter(scientistDao?.loadAllScientist(), clickCard, clickEdit, clickDelete)
        recyclerScientists.adapter = scientistAdapter

        //TODO
        //val user = mainFragmentArgs.fromBundle(requireArguments()).logedUsr

        nameTextView.text = "user.name"// + '!'

        val scientist = Scientist(
            "Albert Einstein",
            "https://en.wikipedia.org/wiki/Albert_Einstein",
            "German",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3e/Einstein_1921_by_F_Schmutzer_-_restoration.jpg/800px-Einstein_1921_by_F_Schmutzer_-_restoration.jpg"
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