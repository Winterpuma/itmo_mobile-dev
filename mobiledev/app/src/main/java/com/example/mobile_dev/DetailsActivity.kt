package com.example.mobile_dev

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_dev.numbers.Collatz
import com.example.mobile_dev.numbers.Fibonacci
import com.example.mobile_dev.numbers.Natural
import com.example.mobile_dev.numbers.ViewNumberHelper


class DetailsActivity : AppCompatActivity() {

    private var cat: Cat? = null
    private val numHelpers = mutableListOf<ViewNumberHelper>()


    companion object {
        const val NAT_VALUE = "nat"
        const val FIB_VALUE = "fib"
        const val COL_VALUE = "col"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        val catName = intent.getStringExtra(INTENT_CAT_NAME)

        if (catName != null) {
            cat = JsonHelper().readJsonCatData(catName, assets, resources.configuration)
        }

        setCatFields(cat!!)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        saveNumbers()
        init()
        getNumbers()
        setCatFields(cat!!)
    }

    private fun setCatFields(cat: Cat) {
        val title: TextView = findViewById(R.id.textView_title)
        val descr: TextView = findViewById(R.id.textView_descr)

        title.text = cat.name
        descr.text = cat.description

        setImg()
    }

    private fun saveNumbers() {
        val settings: SharedPreferences = getSharedPreferences(SHARED_PREF_NUMBERS, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = settings.edit()

        numHelpers.forEach { it.saveNumber(editor) }

        editor.apply()
    }

    private fun getNumbers() {
        val settings: SharedPreferences = getSharedPreferences(SHARED_PREF_NUMBERS, Context.MODE_PRIVATE)

        numHelpers.forEach { it.setNumberFromSettings(settings) }
    }

    private fun init() {
        setContentView(R.layout.details)

        numHelpers.add(
            ViewNumberHelper(Natural(), findViewById(R.id.textView_nat), findViewById(R.id.button_nat), NAT_VALUE))
        numHelpers.add(
            ViewNumberHelper(Fibonacci(), findViewById(R.id.textView_fib), findViewById(R.id.button_fib), FIB_VALUE))
        numHelpers.add(
            ViewNumberHelper(Collatz(), findViewById(R.id.textView_col), findViewById(R.id.button_col), COL_VALUE))
    }

    private fun setImg() {
        val img: ImageView = findViewById(R.id.imageView)
        val imgSmall: ImageView = findViewById(R.id.imageView_small)
        val imgPath = cat?.imgPath
        img.setImageResource(resources.getIdentifier("@drawable/$imgPath", null, packageName))
        imgSmall.setImageResource(resources.getIdentifier("@drawable/$imgPath", null, packageName))
    }
}