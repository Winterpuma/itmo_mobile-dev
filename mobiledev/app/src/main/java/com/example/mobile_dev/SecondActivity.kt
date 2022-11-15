package com.example.mobile_dev

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread

class SecondActivity : AppCompatActivity() {

    private var run1 = AtomicBoolean(false)
    private var run2 = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_second)

        setButtons()
    }

    private fun setButtons() {
        val buttonRun: Button = findViewById(R.id.button_run)
        val buttonStop: Button = findViewById(R.id.button_stop)
        val buttonReset: Button = findViewById(R.id.button_reset)

        val textView1: TextView = findViewById(R.id.textView_thread1)
        val textView2: TextView = findViewById(R.id.textView_thread2)

        buttonRun.setOnClickListener {
            run1.set(true)
            run2.set(true)

            thread {
                while (run1.get()) {
                    runOnUiThread {
                        textView1.text = addToNumber(textView1.text.toString(), 50)
                    }
                    Thread.sleep(1000)
                }
            }

            thread {
                while (run2.get()) {
                    runOnUiThread {
                        textView2.text = addToNumber(textView2.text.toString(), 1)
                    }
                    Thread.sleep(100)
                }
            }
        }

        buttonStop.setOnClickListener {
            run1.compareAndSet(true, false)
            run2.compareAndSet(true, false)
        }

        buttonReset.setOnClickListener {
            run1.compareAndSet(true, false)
            run2.compareAndSet(true, false)

            textView1.text = "0"
            textView2.text = "0"

        }
    }

    private fun addToNumber(string: String, number: Int): String {
        var cur = string.toInt()
        cur += number
        return cur.toString()
    }
}