package com.example.mobile_dev.lab3

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_dev.R

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_third_profile)

        // TODO: load current money
        setButtons()
    }

    private fun setButtons() {
        val buttonLuck: Button = findViewById(R.id.button_luck)
        val buttonWithdrawMoney: Button = findViewById(R.id.button_withdrawMoney)
        val buttonAddMoney: Button = findViewById(R.id.button_addMoney)

        buttonLuck.setOnClickListener { lucky() }
        buttonAddMoney.setOnClickListener { addMoney() }
        buttonWithdrawMoney.setOnClickListener { withdrawMoney() }
    }

    private fun lucky() {
        // TODO: api call
        showToast("Luck")
    }

    private fun addMoney() {
        // TODO: api call
        showToast("Add")
    }

    private fun withdrawMoney() {
        // TODO: api call
        showToast("Withdraw")
    }

    private fun showToast(str : String) {
        Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
    }
}