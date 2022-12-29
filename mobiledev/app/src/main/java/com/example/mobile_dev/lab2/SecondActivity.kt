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

    private val increment1 = 50
    private val increment2 = 1
    private val sleep1: Long = 1000
    private val sleep2: Long = 100

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

        val progressBar1: CustomProgressBar = findViewById(R.id.customProgressBar1)
        val progressBar2: CustomProgressBar = findViewById(R.id.customProgressBar2)

        buttonRun.setOnClickListener {
            runThreadIfNotActive(run1, textView1, progressBar1, increment1, sleep1)
            runThreadIfNotActive(run2, textView2, progressBar2, increment2, sleep2)
        }

        buttonStop.setOnClickListener { stopThreads() }

        buttonReset.setOnClickListener {
            stopThreads()

            textView1.text = "0"
            textView2.text = "0"

            progressBar1.progress = 0F
            progressBar2.progress = 0F
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

    private fun runThreadIfNotActive(runFlag: AtomicBoolean, textView: TextView, progressBar: CustomProgressBar, increment: Int, sleep: Long) {
        if (!runFlag.get()) {
            runFlag.set(true)
            thread {
                while (runFlag.get()) {
                    runOnUiThread {
                        textView.text = addToNumber(textView.text.toString(), increment)
                        progressBar.progress += increment
                    }
                    Thread.sleep(sleep)
                }
            }
        }
    }
}