package com.utn.nerdypedia.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.utn.nerdypedia.R
import com.utn.nerdypedia.viewmodels.AddViewModel
import androidx.lifecycle.Observer

class AddFragment : Fragment() {

    companion object {
        fun newInstance() = AddFragment()
    }

    private lateinit var viewModel: AddViewModel

    private lateinit var buttonSave : Button
    private lateinit var editTextName : EditText
    private lateinit var editTextBiography : EditText
    private lateinit var textViewTitle : TextView

    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.add_fragment, container, false)

        buttonSave = v.findViewById(R.id.buttonSave)
        editTextName = v.findViewById(R.id.editTextName)
        editTextBiography = v.findViewById(R.id.editTextBiography)
        textViewTitle = v.findViewById(R.id.textViewTitle)

        return v
    }

    override fun onStart() {
        super.onStart()

        viewModel.onStart()

        /* Observadores del viewModel */
        //Los datos estan vacios
        viewModel.flagEmptyData.observe(viewLifecycleOwner, Observer { result ->
            if(result){
                Snackbar.make(v, "Complete all fields", Snackbar.LENGTH_LONG).show()
            }
        })

        viewModel.flagExit.observe(viewLifecycleOwner, Observer { result ->
            if(result){
                activity?.onBackPressed()
            }
        })

        viewModel.nameText.observe(viewLifecycleOwner, Observer { name ->
            editTextName.setText(name)
        })

        viewModel.urlText.observe(viewLifecycleOwner, Observer { url ->
            editTextBiography.setText(url)
        })

        viewModel.titleText.observe(viewLifecycleOwner, Observer { title ->
            textViewTitle.text = title
        })

        buttonSave.setOnClickListener{
            val name = editTextName.text.toString()
            val biography = editTextBiography.text.toString()

            viewModel.saveScientist(name, biography)
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        // TODO: Use the ViewModel
    }

}