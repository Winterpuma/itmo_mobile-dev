package com.example.mobile_dev.lab2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_dev.R
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

    override fun onPause() {
        super.onPause()
        stopThreads()
    }

    private fun setButtons() {
        val buttonRun: Button = findViewById(R.id.button_run)
        val buttonStop: Button = findViewById(R.id.button_stop)
        val buttonReset: Button = findViewById(R.id.button_reset)

        val textView1: TextView = findViewById(R.id.textView_thread1)
        val textView2: TextView = findViewById(R.id.textView_thread2)

        buttonRun.setOnClickListener {
            runThreadIfNotActive(run1, textView1, 50, 1000)
            runThreadIfNotActive(run2, textView2, 1, 100)
        }

        buttonStop.setOnClickListener { stopThreads() }

        buttonReset.setOnClickListener {
            stopThreads()

            textView1.text = "0"
            textView2.text = "0"
        }
    }

    private fun addToNumber(string: String, number: Int): String {
        var cur = string.toInt()
        cur += number
        return cur.toString()
    }

    private fun stopThreads() {
        run1.compareAndSet(true, false)
        run2.compareAndSet(true, false)
    }

    private fun runThreadIfNotActive(runFlag: AtomicBoolean, textView: TextView, increment: Int, sleep: Long) {
        if (!runFlag.get()) {
            runFlag.set(true)
            thread {
                while (runFlag.get()) {
                    runOnUiThread {
                        textView.text = addToNumber(textView.text.toString(), increment)
                    }
                    Thread.sleep(sleep)
                }
            }
        }
    }
}