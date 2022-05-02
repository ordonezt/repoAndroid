package com.utn.clase3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.utn.clase3.viewmodels.LoginViewModel
import com.utn.clase3.R
import com.utn.clase3.entities.User

class loginFragment : Fragment() {

    companion object {
        fun newInstance() = loginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    private lateinit var loginButton : Button
    private lateinit var userEditText : EditText
    private lateinit var passEditText : EditText
    private lateinit var layout : ConstraintLayout

    private lateinit var v : View

    private var tomas : User = User("Tomás", "1234", "ordonezt")
    private var julian : User = User("Julian", "1234", "julian1234")
    private var asdf : User = User("asdf", "asdf", "asdf")
    private var qwerty : User = User("qwerty", "qwerty", "qwerty")
    private var dataBase : MutableList<User> = mutableListOf(tomas, julian, asdf, qwerty)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.login_fragment, container, false)

        loginButton = v.findViewById(R.id.loginButton)
        userEditText = v.findViewById(R.id.userEditText)
        passEditText = v.findViewById(R.id.passEditText)
        layout = v.findViewById(R.id.layout)

        return v
    }

    override fun onStart() {
        super.onStart()

        loginButton.setOnClickListener {
            if((userEditText.length() > 0) and (passEditText.length() > 0)) {
                var result : Boolean = false
                for (i in dataBase) {
                    if ((i.username == userEditText.text.toString()) && (i.password == passEditText.text.toString())) {
                        val action = loginFragmentDirections.actionLoginFragmentToWelcomeFragment(i)
                        v.findNavController().navigate(action)
                        result = true
                    }
                }
                if(!result) {
                    Snackbar.make(layout, "The password you’ve entered is incorrect", Snackbar.LENGTH_LONG).show()
                }
            } else {
                Snackbar.make(layout, "Complete both fields", Snackbar.LENGTH_LONG).show()
            }
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}