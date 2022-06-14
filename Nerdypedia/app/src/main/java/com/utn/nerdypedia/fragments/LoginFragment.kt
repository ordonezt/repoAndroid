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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.utn.nerdypedia.R
import com.utn.nerdypedia.activities.MainActivity
import com.utn.nerdypedia.database.appDataBase
import com.utn.nerdypedia.database.userDao
import com.utn.nerdypedia.entities.Session
import com.utn.nerdypedia.entities.User
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

    private var db : appDataBase? = null
    private var userDao : userDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.login_fragment, container, false)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        loginButton = v.findViewById(R.id.loginButton)
        signInButton = v.findViewById(R.id.signInButton)
        userEditText = v.findViewById(R.id.userEditText)
        passEditText = v.findViewById(R.id.passEditText)


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
        //Los datos del usuario estan vacios
        viewModel.flagEmptyData.observe(viewLifecycleOwner, Observer { result ->
            if(result){
                hideKeyboard()
                Snackbar.make(v, "Complete both fields", Snackbar.LENGTH_LONG).show()
            }
        })

        //Usuario no encontrado
        viewModel.flagUserUnknown.observe(viewLifecycleOwner, Observer { result ->
            if(result){
                hideKeyboard()
                Snackbar.make(v, "The password you’ve entered is incorrect", Snackbar.LENGTH_LONG).show()
            }
        })

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