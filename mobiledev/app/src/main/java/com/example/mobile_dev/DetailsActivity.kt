package com.example.mobile_dev

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class DetailsActivity : AppCompatActivity() {

    private var cat: Cat? = null

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
        descr.text = cat.descrtiption

        setImg()
    }

    private fun saveNumbers() {
        val settings: SharedPreferences = getSharedPreferences(SHARED_PREF_NUMBERS, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = settings.edit()

        editor.putString(NAT_VALUE, findViewById<TextView>(R.id.textView_nat).text.toString())
        editor.putString(FIB_VALUE, findViewById<TextView>(R.id.textView_fib).text.toString())
        editor.putString(COL_VALUE, findViewById<TextView>(R.id.textView_col).text.toString())

        editor.apply()
    }

    private fun getNumbers() {
        val settings: SharedPreferences = getSharedPreferences(SHARED_PREF_NUMBERS, Context.MODE_PRIVATE)

        findViewById<TextView>(R.id.textView_nat).text = settings.getString(NAT_VALUE, "1")
        findViewById<TextView>(R.id.textView_fib).text = settings.getString(FIB_VALUE, "1")
        findViewById<TextView>(R.id.textView_col).text = settings.getString(COL_VALUE, "1")
    }

    companion object {
        const val NAT_VALUE = "nat"
        const val FIB_VALUE = "fib"
        const val COL_VALUE = "col"
    }

    private fun init() {
        setContentView(R.layout.details)

        val buttonNat: Button = findViewById(R.id.button_nat)
        val buttonFib: Button = findViewById(R.id.button_fib)
        val buttonCol: Button = findViewById(R.id.button_col)

        buttonNat.setOnClickListener { addNumberToLabel(findViewById(R.id.textView_nat), 1) }
        buttonFib.setOnClickListener { addNumberToLabel(findViewById(R.id.textView_fib), 2)  }
        buttonCol.setOnClickListener { addNumberToLabel(findViewById(R.id.textView_col), 3)  }
    }

    private fun addNumberToLabel(label : TextView, increment : Int) {
        var cur = label.text.toString().toInt()
        cur += increment
        label.text = cur.toString()
    }

    private fun setImg() {
        val img: ImageView = findViewById(R.id.imageView)
        val imgSmall: ImageView = findViewById(R.id.imageView_small)
        val imgPath = cat?.imgPath
        img.setImageResource(resources.getIdentifier("@drawable/$imgPath", null, packageName))
        imgSmall.setImageResource(resources.getIdentifier("@drawable/$imgPath", null, packageName))
    }
}