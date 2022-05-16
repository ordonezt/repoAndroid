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
import com.utn.nerdypedia.database.appDataBase
import com.utn.nerdypedia.entities.Scientist
import com.utn.nerdypedia.entities.Session
import com.utn.nerdypedia.viewmodels.AddViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddFragment : Fragment() {

    companion object {
        fun newInstance() = AddFragment()
    }

    private lateinit var viewModel: AddViewModel

    private lateinit var buttonSave : Button
    private lateinit var editTextName : EditText
    private lateinit var editTextBiography : EditText
    private lateinit var textViewTitle : TextView

    private var db : appDataBase? = null
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
        textViewTitle = v.findViewById(R.id.textViewTitle)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = appDataBase.getAppDataBase(v.context)
        scientistDao = db?.scientistDao()

        val scientist = AddFragmentArgs.fromBundle((requireArguments())).selectedScientist

        if(scientist != null){
            textViewTitle.text = "Edit"
            editTextName.setText(scientist.name)
            editTextBiography.setText(scientist.biographyUrl)
        }

        buttonSave.setOnClickListener{
            val name = editTextName.text.toString()
            val biography = editTextBiography.text.toString()

            if(name         != "" &&
               biography    != ""){
                   if(scientist != null) {
                       scientist.name = name
                       scientist.biographyUrl = biography
                       scientistDao?.updateScientist(scientist)
                   } else {
                       val currentTime = LocalDateTime.now()
                       val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                       val date = currentTime.format(formatter)

                       val author = Session.user.username
                       scientistDao?.insertScientist(Scientist(name, biography, date, author))
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