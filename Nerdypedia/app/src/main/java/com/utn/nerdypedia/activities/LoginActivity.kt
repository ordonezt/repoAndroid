package com.utn.nerdypedia.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.utn.nerdypedia.R
import com.utn.nerdypedia.entities.User

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton : Button
    private lateinit var userEditText : EditText
    private lateinit var passEditText : EditText
    private lateinit var layout : ConstraintLayout

    private var tomas : User = User(0, "Tomás", "1234", "ordonezt")
    private var julian : User = User(1, "Julian", "1234", "julian1234")
    private var asdf : User = User(2, "asdf", "asdf", "asdf")
    private var qwerty : User = User(3, "qwerty", "qwerty", "qwerty")
    private var usrDataBase : MutableList<User> = mutableListOf(tomas, julian, asdf, qwerty)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.loginButton)
        userEditText = findViewById(R.id.userEditText)
        passEditText = findViewById(R.id.passEditText)

        loginButton.setOnClickListener {
            if((userEditText.length() > 0) and (passEditText.length() > 0)) {
                var result : Boolean = false
                for (i in usrDataBase) {
                    if ((i.username == userEditText.text.toString()) && (i.password == passEditText.text.toString())) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("USER_ID", i.id)
                        startActivity(intent)
                        finish()

                        result = true
                    }
                }
                if(!result) {
                    Snackbar.make(findViewById(android.R.id.content), "The password you’ve entered is incorrect", Snackbar.LENGTH_LONG).show()
                }
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Complete both fields", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}