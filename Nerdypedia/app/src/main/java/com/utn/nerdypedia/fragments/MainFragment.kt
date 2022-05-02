package com.utn.nerdypedia.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utn.nerdypedia.R
import com.utn.nerdypedia.adapters.ScientistAdapter
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

    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.main_fragment, container, false)

        nameTextView = v.findViewById(R.id.nameTextView)
        recyclerScientists = v.findViewById(R.id.recyclerScientists)
        return v
    }

    override fun onStart() {
        super.onStart()

        recyclerScientists.setHasFixedSize(true)
        recyclerScientists.layoutManager = LinearLayoutManager(context)

        scientistAdapter = ScientistAdapter(repository.scientistList) { position ->
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(repository.scientistList[position])
            v.findNavController().navigate(action)
        }

        recyclerScientists.adapter = scientistAdapter

        //val user = mainFragmentArgs.fromBundle(requireArguments()).logedUsr

        nameTextView.text = "user.name"// + '!'
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}