package com.example.mobile_dev.lab3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_dev.R

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_third_login)

        setButtons()
    }

    private fun setButtons() {
        val buttonLogin: Button = findViewById(R.id.button_signIn)
        val buttonRegister: Button = findViewById(R.id.button_signup)

        buttonLogin.setOnClickListener { login() }
        buttonRegister.setOnClickListener { register() }
    }

    private fun login() {
        // TODO: api check up with provided id
        // TODO: send id to next page
        val intent = Intent(this@ThirdActivity, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun register() {
        // TODO: api create new user
        val intent = Intent(this@ThirdActivity, ProfileActivity::class.java)
        startActivity(intent)
    }

}