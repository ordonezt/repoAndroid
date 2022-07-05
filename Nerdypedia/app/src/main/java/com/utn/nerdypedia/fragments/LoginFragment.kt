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
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.utn.nerdypedia.R
import com.utn.nerdypedia.activities.MainActivity
import com.utn.nerdypedia.entities.ViewState
import com.utn.nerdypedia.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var v : View

    private lateinit var loginButton : Button
    private lateinit var signInButton : Button
    private lateinit var userEditText : EditText
    private lateinit var passEditText : EditText
    private lateinit var progressBarLogin: ProgressBar
    private lateinit var textSignIn: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.login_fragment, container, false)
        loginButton = v.findViewById(R.id.loginButton)
        signInButton = v.findViewById(R.id.signInButton)
        userEditText = v.findViewById(R.id.emailEditText)
        passEditText = v.findViewById(R.id.passEditText)
        progressBarLogin = v.findViewById(R.id.progressBarLogin)
        textSignIn = v.findViewById(R.id.textSignIn)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        /* Llamadas a viewModel */
        //Limpio todos los flags
        viewModel.onStart()

        //Quisieron loguear
        loginButton.setOnClickListener {
            viewModel.authUser(userEditText.text.toString(), passEditText.text.toString())
        }

        //Quieren crear un nuevo usuario
        signInButton.setOnClickListener{
            viewModel.signIn()
        }

        /* Observadores del viewModel */
        //Login con exito
        viewModel.flagLogIn.observe(viewLifecycleOwner, Observer { result ->
            if(result){
                hideKeyboard()
                val intent = Intent(v.context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })

        //Nos vamos a la pantalla de sign in
        viewModel.flagSignIn.observe(viewLifecycleOwner, Observer { result ->
            if(result){
                var action = LoginFragmentDirections.actionLoginFragmentToSignInFragment()
                v.findNavController().navigate(action)
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            hideKeyboard()
            when(state){
                ViewState.RESET -> {
                    progressBarLogin.visibility = View.INVISIBLE
                    loginButton.visibility      = View.VISIBLE
                    signInButton.visibility     = View.VISIBLE
                    userEditText.visibility     = View.VISIBLE
                    passEditText.visibility     = View.VISIBLE
                    textSignIn.visibility       = View.VISIBLE
                }
                ViewState.LOADING -> {
                    progressBarLogin.visibility = View.VISIBLE
                    loginButton.visibility      = View.INVISIBLE
                    signInButton.visibility     = View.INVISIBLE
                    userEditText.visibility     = View.INVISIBLE
                    passEditText.visibility     = View.INVISIBLE
                    textSignIn.visibility       = View.INVISIBLE
                }
                ViewState.FAILURE -> {
                    progressBarLogin.visibility = View.INVISIBLE
                    loginButton.visibility      = View.VISIBLE
                    signInButton.visibility     = View.VISIBLE
                    userEditText.visibility     = View.VISIBLE
                    passEditText.visibility     = View.VISIBLE
                    textSignIn.visibility       = View.VISIBLE
                    Snackbar.make(v, viewModel.failureText, Snackbar.LENGTH_LONG).show()
                }
                ViewState.SUCCESS -> {
                    progressBarLogin.visibility = View.INVISIBLE
                    loginButton.visibility      = View.VISIBLE
                    signInButton.visibility     = View.VISIBLE
                    userEditText.visibility     = View.VISIBLE
                    passEditText.visibility     = View.VISIBLE
                    textSignIn.visibility       = View.VISIBLE
                }
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