package com.example.mobile_dev


import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        val button1:Button = findViewById(R.id.button1)
        val button2:Button = findViewById(R.id.button2)
        val button3:Button = findViewById(R.id.button3)

        button1.setOnClickListener { showToast("In first") }
        button2.setOnClickListener { showToast("In second") }
        button3.setOnClickListener { showToast("In third") }

        val numberText:TextView = findViewById(R.id.textView_counter)
        val buttonPlus:Button = findViewById(R.id.buttonPlus)
        val buttonMinus:Button = findViewById(R.id.buttonMinus)

        numberText.text = sharedPreference.getString("grade", "0")

        buttonPlus.setOnClickListener {
            var cur = numberText.text.toString().toInt()
            cur++
            numberText.text = cur.toString()
            sharedPreference.edit().putString("grade", cur.toString()).apply()
        }

        buttonMinus.setOnClickListener {
            var cur = numberText.text.toString().toInt()
            if (cur > 0)
            {
                cur--
                numberText.text = cur.toString()
                sharedPreference.edit().putString("grade", cur.toString()).apply()
            }
        }
    }

    private  fun showToast(str : String) {
        Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
    }

}