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
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.utn.nerdypedia.R
import com.utn.nerdypedia.activities.MainActivity
import com.utn.nerdypedia.database.appDataBase
import com.utn.nerdypedia.database.scientistDao
import com.utn.nerdypedia.database.userDao
import com.utn.nerdypedia.entities.User
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

    private var db : appDataBase? = null
    private var userDao : userDao? = null

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

        db = appDataBase.getAppDataBase(v.context)
        userDao = db?.userDao()

        signInButton = v.findViewById(R.id.signInButton2)
        nameText = v.findViewById(R.id.nameText)
        usernameText = v.findViewById(R.id.userNameText)
        passwordText = v.findViewById(R.id.passwordText)
        passwordText2 = v.findViewById(R.id.passwordText2)

        signInButton.setOnClickListener {
            if(nameText.text.isEmpty() or usernameText.text.isEmpty() or passwordText.text.isEmpty() or passwordText2.text.isEmpty()) {
                hideKeyboard()
                Snackbar.make(v, "Complete all fields", Snackbar.LENGTH_LONG).show()
            } else if(passwordText.text.toString() != passwordText2.text.toString()){
                hideKeyboard()
                Snackbar.make(v, "Both passwords must match", Snackbar.LENGTH_LONG).show()
            } else if (userDao?.loadUserByUsername(usernameText.text.toString()) != null){
                hideKeyboard()
                Snackbar.make(v, "Username already exists", Snackbar.LENGTH_LONG).show()
            } else {
                val user = User(nameText.text.toString(),
                                passwordText.text.toString(),
                                usernameText.text.toString())

                userDao?.insertUser(user)

                val intent = Intent(v.context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}