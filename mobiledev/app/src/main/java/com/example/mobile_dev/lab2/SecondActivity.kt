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

    private val speedParameters1: SpeedParameters = SpeedParameters(1, 1000)
    private val speedParameters2: SpeedParameters = SpeedParameters(1, 1000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_second)

        setMainButtons()
        setSpeedButtons()
    }

    override fun onPause() {
        super.onPause()
        stopThreads()
    }

    private fun setMainButtons() {
        val buttonRun: Button = findViewById(R.id.button_run)
        val buttonStop: Button = findViewById(R.id.button_stop)
        val buttonReset: Button = findViewById(R.id.button_reset)

        val textView1: TextView = findViewById(R.id.textView_thread1)
        val textView2: TextView = findViewById(R.id.textView_thread2)

        val progressBar1: CustomProgressBar = findViewById(R.id.customProgressBar1)
        val progressBar2: CustomProgressBar = findViewById(R.id.customProgressBar2)

        buttonRun.setOnClickListener {
            runThreadIfNotActive(run1, textView1, progressBar1, speedParameters1)
            runThreadIfNotActive(run2, textView2, progressBar2, speedParameters2)
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

    private fun setSpeedButtons() {
        setSpeedButtons(
            findViewById(R.id.button1_up),
            findViewById(R.id.button1_down),
            findViewById(R.id.textView_thread1_speed),
            speedParameters1
        )

        setSpeedButtons(
            findViewById(R.id.button2_up),
            findViewById(R.id.button2_down),
            findViewById(R.id.textView_thread2_speed),
            speedParameters2
        )
    }

    private fun setSpeedButtons(buttonUp: Button, buttonDown: Button, textView: TextView, speedParameters: SpeedParameters) {
        textView.text = speedParameters.step.toString()

        buttonUp.setOnClickListener { textView.text = speedParameters.increment() }
        buttonDown.setOnClickListener { textView.text = speedParameters.decrement() }
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

    private fun runThreadIfNotActive(runFlag: AtomicBoolean, textView: TextView, progressBar: CustomProgressBar, speedParameters: SpeedParameters) {
        if (!runFlag.get()) {
            runFlag.set(true)
            thread {
                while (runFlag.get()) {
                    runOnUiThread {
                        textView.text = addToNumber(textView.text.toString(), speedParameters.step)
                        progressBar.progress += speedParameters.step
                    }
                    Thread.sleep(speedParameters.sleep)
                }
            }
        }
    }
}