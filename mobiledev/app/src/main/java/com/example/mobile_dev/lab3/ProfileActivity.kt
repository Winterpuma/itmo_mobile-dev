package com.example.mobile_dev.lab3

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.mobile_dev.*
import io.ktor.http.*

class ProfileActivity : AppCompatActivity() {

    var id: Int = ERROR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.page_third_profile)
        setButtons()

        id = intent.getIntExtra(INTENT_ID, ERROR)
        val user = Requests().getUser(id)

        if (user == null) {
            showToast(getString(R.string.err_not_loaded), applicationContext)
        }
        else {
            setUserId(user.id)
            setMoney(user.money)
            fillListView(user.id)
        }
    }

    private fun setButtons() {
        val buttonLuck: Button = findViewById(R.id.button_luck)
        val buttonWithdrawMoney: Button = findViewById(R.id.button_withdrawMoney)
        val buttonAddMoney: Button = findViewById(R.id.button_addMoney)
        val buttonDeleteUser: Button = findViewById(R.id.button_delete)

        buttonLuck.setOnClickListener { lucky() }
        buttonAddMoney.setOnClickListener { addMoney() }
        buttonWithdrawMoney.setOnClickListener { withdrawMoney() }
        buttonDeleteUser.setOnClickListener { deleteUser() }
    }

    private fun lucky() {
        val res = Requests().luckyMoney(id)
        val user = res.first
        if (user == null)
            showToast(res.second, applicationContext)
        else {
            setMoney(user.money)
            fillListView(user.id)
        }
    }

    private fun addMoney() {
        val user = Requests().addMoney(id, ADD_MONEY_AMOUNT)
        setMoney(user.money)
        fillListView(user.id)
    }

    private fun withdrawMoney() {
        val user = Requests().withdrawMoney(id, WITHDRAW_MONEY_AMOUNT)
        setMoney(user.money)
        fillListView(user.id)
    }

    private fun deleteUser() {
        val response = Requests().deleteUser(id)

        if (response.status == HttpStatusCode.OK)
        {
            showToast(getString(R.string.delete_user_success), applicationContext)
            val intent = Intent(this@ProfileActivity, ThirdActivity::class.java)
            startActivity(intent)
        }
        else
        {
            showToast(getString(R.string.error), applicationContext)
        }
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

    private fun fillListView(idUser: Int) {
        val listView: ListView = findViewById(R.id.listView_transactions)
        val transactions = Requests().getAllUserTransactions(idUser).takeLast(10).reversed()
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, transactions
        )

        listView.adapter = adapter
    }
}