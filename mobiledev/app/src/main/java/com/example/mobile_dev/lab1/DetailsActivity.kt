package com.example.mobile_dev.lab1

import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_dev.*
import com.example.mobile_dev.lab1.numbers.Collatz
import com.example.mobile_dev.lab1.numbers.Fibonacci
import com.example.mobile_dev.lab1.numbers.Natural
import com.example.mobile_dev.lab1.numbers.ViewNumberHelper


class DetailsActivity : AppCompatActivity() {

    private var cat: Cat? = null
    private val numHelpers = mutableMapOf<String, ViewNumberHelper>()


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
        setContentView(R.layout.details)
        numHelpers[NAT_VALUE]?.restoreView(findViewById(R.id.textView_nat), findViewById(R.id.button_nat))
        numHelpers[FIB_VALUE]?.restoreView(findViewById(R.id.textView_fib), findViewById(R.id.button_fib))
        numHelpers[COL_VALUE]?.restoreView(findViewById(R.id.textView_col), findViewById(R.id.button_col))
        setCatFields(cat!!)
    }

    private fun setCatFields(cat: Cat) {
        val title: TextView = findViewById(R.id.textView_title)
        val descr: TextView = findViewById(R.id.textView_descr)

        title.text = cat.name
        descr.text = cat.description

        setImg()
    }

    private fun init() {
        setContentView(R.layout.details)

        numHelpers[NAT_VALUE] =
            ViewNumberHelper(Natural(), findViewById(R.id.textView_nat), findViewById(R.id.button_nat))
        numHelpers[FIB_VALUE] =
            ViewNumberHelper(Fibonacci(), findViewById(R.id.textView_fib), findViewById(R.id.button_fib))
        numHelpers[COL_VALUE] =
            ViewNumberHelper(Collatz(), findViewById(R.id.textView_col), findViewById(R.id.button_col))
    }

    private fun setImg() {
        val img: ImageView = findViewById(R.id.imageView)
        val imgSmall: ImageView = findViewById(R.id.imageView_small)
        val imgPath = cat?.imgPath
        img.setImageResource(resources.getIdentifier("@drawable/$imgPath", null, packageName))
        imgSmall.setImageResource(resources.getIdentifier("@drawable/$imgPath", null, packageName))
    }
}