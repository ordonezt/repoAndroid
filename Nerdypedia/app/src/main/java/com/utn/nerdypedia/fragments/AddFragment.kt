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
import com.utn.nerdypedia.database.scientistDao
import com.utn.nerdypedia.database.scientistsDataBase
import com.utn.nerdypedia.entities.Scientist
import com.utn.nerdypedia.viewmodels.AddViewModel

class AddFragment : Fragment() {

    companion object {
        fun newInstance() = AddFragment()
    }

    private lateinit var viewModel: AddViewModel

    private lateinit var buttonSave : Button
    private lateinit var editTextName : EditText
    private lateinit var editTextBiography : EditText
    private lateinit var editTextCitizenship : EditText
    private lateinit var editTextPictureUrl : EditText
    private lateinit var textViewTitle : TextView

    private var db : scientistsDataBase? = null
    private var scientistDao : scientistDao? = null

    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.add_fragment, container, false)

        buttonSave = v.findViewById(R.id.buttonSave)
        editTextName = v.findViewById(R.id.editTextName)
        editTextBiography = v.findViewById(R.id.editTextBiography)
        editTextCitizenship = v.findViewById(R.id.editTextCitizenship)
        editTextPictureUrl = v.findViewById(R.id.editTextPictureUrl)
        textViewTitle = v.findViewById(R.id.textViewTitle)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = scientistsDataBase.getAppDataBase(v.context)
        scientistDao = db?.scientistDao()

        val scientist = AddFragmentArgs.fromBundle((requireArguments())).selectedScientist

        if(scientist != null){
            textViewTitle.text = "Edit"
            editTextName.setText(scientist.name)
            editTextBiography.setText(scientist.biographyUrl)
            editTextCitizenship.setText(scientist.citizenship)
            editTextPictureUrl.setText(scientist.pictureUrl)
        }

        buttonSave.setOnClickListener{
            val name = editTextName.text.toString()
            val biography = editTextBiography.text.toString()
            val citizenship = editTextCitizenship.text.toString()
            val pictureUrl = editTextPictureUrl.text.toString()

            if(name         != "" &&
               biography    != "" &&
               citizenship  != "" &&
               pictureUrl   != ""){
                   if(scientist != null) {
                       scientist.name = name
                       scientist.biographyUrl = biography
                       scientist.citizenship = citizenship
                       scientist.pictureUrl = pictureUrl
                       scientistDao?.updateScientist(scientist)
                   } else {
                       scientistDao?.insertScientist(Scientist(name, biography, citizenship, pictureUrl))
                   }
                activity?.onBackPressed()
            } else {
                Snackbar.make(v, "Complete all fields", Snackbar.LENGTH_LONG).show()
            }
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        // TODO: Use the ViewModel
    }

}