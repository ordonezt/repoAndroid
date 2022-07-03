package com.utn.nerdypedia.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.utn.nerdypedia.R
import com.utn.nerdypedia.activities.MainActivity
import com.utn.nerdypedia.viewmodels.SignInViewModel

class SignInFragment : Fragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var viewModel: SignInViewModel
    private lateinit var v : View

    private lateinit var signInButton : Button
    private lateinit var nameText: EditText
    private lateinit var usernameText: EditText
    private lateinit var passwordText : EditText
    private lateinit var passwordText2 : EditText
    private lateinit var emailText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.sign_in_fragment, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        signInButton = v.findViewById(R.id.signInButton2)
        nameText = v.findViewById(R.id.nameText)
        usernameText = v.findViewById(R.id.userNameText)
        passwordText = v.findViewById(R.id.passwordText)
        passwordText2 = v.findViewById(R.id.passwordText2)
        emailText = v.findViewById(R.id.emailText)

        /* Llamadas a viewModel */
        //Quieren crear nuevo usuario
        signInButton.setOnClickListener {
            viewModel.signInUser(nameText.text.toString(), usernameText.text.toString(),
                                 passwordText.text.toString(), passwordText2.text.toString(),
                                 emailText.text.toString())
        }

        /* Observadores del viewModel */
        //Error en la creacion del usuario
        viewModel.wrongSignInText.observe(viewLifecycleOwner, Observer { text ->
            if(text.isNotEmpty()) {
                hideKeyboard()
                Snackbar.make(v, text, Snackbar.LENGTH_LONG).show()
            }
        })

        //Registro exitoso
        viewModel.flagSignIn.observe(viewLifecycleOwner, Observer { result ->
            if(result){
                hideKeyboard()
                val intent = Intent(v.context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })

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