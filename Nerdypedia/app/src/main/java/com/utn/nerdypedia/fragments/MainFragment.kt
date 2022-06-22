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
import com.utn.nerdypedia.viewmodels.MainViewModel
import androidx.lifecycle.Observer

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var nameTextView: TextView
    private lateinit var recyclerScientists : RecyclerView

    private lateinit var fab : FloatingActionButton

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

        viewModel.onStart()

        recyclerScientists.setHasFixedSize(true)
        recyclerScientists.layoutManager = LinearLayoutManager(context)

        fab.setOnClickListener{
            viewModel.addItem()
        }

        /* Observadores del viewModel */
        viewModel.nameText.observe(viewLifecycleOwner, Observer { name ->
            nameTextView.text = name
        })

        viewModel.flagGoDetails.observe(viewLifecycleOwner, Observer { result ->
            if(result){
                val action = MainFragmentDirections.actionMainFragmentToDetailsFragment()
                v.findNavController().navigate(action)
            }
        })

        viewModel.flagGoEdit.observe(viewLifecycleOwner, Observer { result ->
            if(result){
                var action = MainFragmentDirections.actionMainFragmentToAddFragment()
                v.findNavController().navigate(action)
            }
        })

        viewModel.flagGoAdd.observe(viewLifecycleOwner, Observer { result ->
            if(result){
                val action = MainFragmentDirections.actionMainFragmentToAddFragment()
                v.findNavController().navigate(action)
            }
        })

        viewModel.scientistAdapter.observe(viewLifecycleOwner, Observer { adapter ->
            recyclerScientists.adapter = adapter

            if(adapter.itemCount > 0){
                listEmptyText.visibility = View.INVISIBLE
                recyclerScientists.visibility = View.VISIBLE
            } else {
                listEmptyText.visibility = View.VISIBLE
                recyclerScientists.visibility = View.INVISIBLE
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }
}