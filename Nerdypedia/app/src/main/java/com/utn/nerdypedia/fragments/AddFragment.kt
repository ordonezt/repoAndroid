package com.utn.nerdypedia.fragments

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.utn.nerdypedia.R
import com.utn.nerdypedia.viewmodels.AddViewModel
import androidx.lifecycle.Observer
import com.utn.nerdypedia.entities.ViewState

class AddFragment : Fragment() {

    companion object {
        fun newInstance() = AddFragment()
    }

    private lateinit var viewModel: AddViewModel

    private lateinit var buttonSave : Button
    private lateinit var editTextName : EditText
    private lateinit var editTextBiography : EditText
    private lateinit var textViewTitle : TextView
    private lateinit var progressBarAdd: ProgressBar

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
        progressBarAdd = v.findViewById(R.id.progressBarAdd)

        return v
    }

    override fun onStart() {
        super.onStart()

        viewModel.onStart()

        /* Observadores del viewModel */
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

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            hideKeyboard()
            when(state){
                ViewState.RESET -> {
                    progressBarAdd.visibility       = View.INVISIBLE
                    buttonSave.visibility           = View.VISIBLE
                    editTextName.visibility         = View.VISIBLE
                    editTextBiography.visibility    = View.VISIBLE
                }
                ViewState.LOADING -> {
                    progressBarAdd.visibility       = View.VISIBLE
                    buttonSave.visibility           = View.INVISIBLE
                    editTextName.visibility         = View.INVISIBLE
                    editTextBiography.visibility    = View.INVISIBLE
                }
                ViewState.FAILURE -> {
                    progressBarAdd.visibility       = View.INVISIBLE
                    buttonSave.visibility           = View.VISIBLE
                    editTextName.visibility         = View.VISIBLE
                    editTextBiography.visibility    = View.VISIBLE
                    Snackbar.make(v, viewModel.failureText, Snackbar.LENGTH_LONG).show()
                }
                ViewState.SUCCESS -> {
                    progressBarAdd.visibility       = View.INVISIBLE
                    buttonSave.visibility           = View.VISIBLE
                    editTextName.visibility         = View.VISIBLE
                    editTextBiography.visibility    = View.VISIBLE
                }
            }
        })
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}