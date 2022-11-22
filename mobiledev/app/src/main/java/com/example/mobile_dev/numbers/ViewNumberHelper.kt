package com.example.mobile_dev.numbers

import android.content.SharedPreferences
import android.widget.Button
import android.widget.TextView

class ViewNumberHelper (private val number: ISequence, private val textView: TextView,
                        button: Button, private val refString: String) {

    init {
        button.setOnClickListener { textView.text = number.next().toString() }
        textView.text = number.current.toString()
    }

    fun saveNumber (editor: SharedPreferences.Editor) {
        editor.putString(refString, textView.text.toString())
    }

    fun setNumberFromSettings (settings: SharedPreferences) {
        val res = settings.getString(refString, null)

        if (res == null)
            textView.text = number.next().toString()
        else {
            textView.text = res
            number.current = res.toInt()
        }
    }
}