package com.example.mobile_dev.lab3

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.mobile_dev.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProfileActivity : AppCompatActivity(), CoroutineScope {

    var id: Int = ERROR

    var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.page_third_profile)
        setButtons()

        id = intent.getIntExtra(INTENT_ID, ERROR)
        launch() {
            val user = requests.getUser(id)

            if (user == null) {
                showToast(getString(R.string.err_not_loaded), applicationContext)
            } else {
                setUserId(user.id)
                setMoney(user.money)
                fillListView(user.id)
            }
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
        CoroutineScope(Dispatchers.IO).launch {
            val res = requests.luckyMoney(id)
            CoroutineScope(Dispatchers.Main).launch {
                val user = res.first
                if (user == null)
                    showToast(res.second, applicationContext)
                else {
                    setMoney(user.money)
                    fillListView(user.id)
                }
            }
        }

    }

    private fun addMoney() {
        launch() {
            val user = requests.addMoney(id, ADD_MONEY_AMOUNT)
            setMoney(user.money)
            fillListView(user.id)
        }
    }

    private fun withdrawMoney() {
        launch() {
            val user = requests.withdrawMoney(id, WITHDRAW_MONEY_AMOUNT)
            setMoney(user.money)
            fillListView(user.id)
        }
    }

    private fun deleteUser() {
        launch() {
            val response = requests.deleteUser(id)

            if (response.status == HttpStatusCode.OK) {
                showToast(getString(R.string.delete_user_success), applicationContext)
                val intent = Intent(this@ProfileActivity, ThirdActivity::class.java)
                startActivity(intent)
            } else {
                showToast(getString(R.string.error), applicationContext)
            }
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
        val cntxt = this
        launch() {
            val transactions = requests.getAllUserTransactions(idUser).takeLast(10).reversed()
            val adapter = ArrayAdapter(
                cntxt,
                android.R.layout.simple_list_item_1, transactions
            )

            listView.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}