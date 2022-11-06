package com.example.mobile_dev

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        init()
    }

    private fun init() {
        setContentView(R.layout.details)

        val button_nat: Button = findViewById(R.id.button_nat)
        val button_fib: Button = findViewById(R.id.button_fib)
        val button_col: Button = findViewById(R.id.button_col)

        button_nat.setOnClickListener { addNumberToLabel(findViewById(R.id.textView_nat), 1) }
        button_fib.setOnClickListener { addNumberToLabel(findViewById(R.id.textView_fib), 2)  }
        button_col.setOnClickListener { addNumberToLabel(findViewById(R.id.textView_col), 3)  }

    }

    private fun addNumberToLabel(label : TextView, increment : Int) {
        var cur = label.text.toString().toInt()
        cur += increment
        label.text = cur.toString()
    }

}