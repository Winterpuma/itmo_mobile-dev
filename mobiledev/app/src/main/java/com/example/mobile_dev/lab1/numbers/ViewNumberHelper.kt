package com.example.mobile_dev.lab1.numbers

import android.widget.Button
import android.widget.TextView

class ViewNumberHelper (private val number: ISequence, private var textView: TextView,
                        button: Button) {

    init {
        initButtonAndView(button)
    }

    fun restoreView(textView: TextView, button: Button) {
        this.textView = textView
        initButtonAndView(button)
    }

    private fun initButtonAndView(button: Button) {
        button.setOnClickListener { textView.text = number.next().toString() }
        textView.text = number.current.toString()
    }
}