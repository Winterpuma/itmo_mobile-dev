package com.example.mobile_dev.lab3

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.mobile_dev.ERROR
import com.example.mobile_dev.INTENT_ID
import com.example.mobile_dev.R

class ProfileActivity : AppCompatActivity() {

    var id: Int = ERROR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.page_third_profile)
        setButtons()

        id = intent.getIntExtra(INTENT_ID, ERROR)
        val user = Requests().getUser(id)
        setUserId(user.id)
        setMoney(user.money)
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
        val user = Requests().luckyMoney(id)
        setMoney(user.money)
    }

    private fun addMoney() {
        val user = Requests().addMoney(id, 100)
        setMoney(user.money)
    }

    private fun withdrawMoney() {
        val user = Requests().withdrawMoney(id, 100)
        setMoney(user.money)
    }

    private fun showToast(str : String) {
        Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
    }

    private fun setUserId(id: Int) {
        val textView = findViewById<TextView>(R.id.textView_idValue)
        textView.text = id.toString()
    }

    private fun setMoney(money: Int) {
        val textViewMoney = findViewById<TextView>(R.id.textView_moneyVal)
        val textViewUpd = findViewById<TextView>(R.id.textView_moneyUpd)
        val dif = money - textViewMoney.text.toString().toInt()

        textViewMoney.text = money.toString()
        textViewUpd.text = moneyUpdString(dif)
        textViewUpd.setTextColor(moneyUpdColor(dif))
    }

    private fun moneyUpdString(moneyDif: Int): String {
        return if (moneyDif >= 0) "+$moneyDif" else moneyDif.toString()
    }

    private fun moneyUpdColor(moneyDif: Int): Int {
        return if (moneyDif > 0)
            ResourcesCompat.getColor(resources, R.color.money_inc, null)
        else if (moneyDif < 0)
            ResourcesCompat.getColor(resources, R.color.money_dec, null)
        else
            Color.GRAY
    }
}