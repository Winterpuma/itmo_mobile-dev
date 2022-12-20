package com.example.mobile_dev.lab3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_dev.INTENT_ID
import com.example.mobile_dev.R
import com.example.mobile_dev.showToast

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
        val editTextId: EditText = findViewById(R.id.editText_id)
        val inputtedId = editTextId.text.toString().toIntOrNull()

        if (inputtedId == null) {
            showToast(getString(R.string.err_not_valid), applicationContext)
            return
        }

        val user = Requests().getUser(inputtedId.toInt())

        if (user == null) {
            showToast(getString(R.string.err_not_found), applicationContext)
        }
        else {
            val intent = Intent(this@ThirdActivity, ProfileActivity::class.java)
            intent.putExtra(INTENT_ID, user.id)
            startActivity(intent)
        }
    }

    private fun register() {
        val user = Requests().createUser()
        val intent = Intent(this@ThirdActivity, ProfileActivity::class.java)
        intent.putExtra(INTENT_ID, user.id)
        startActivity(intent)
    }

}