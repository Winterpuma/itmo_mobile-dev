package com.example.mobile_dev

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.io.InputStream

class DetailsActivity : AppCompatActivity() {

    private var name: String = "cat name"
    private var descrtiption: String = "descr"
    private var imgPath: String = "pic1.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        val title: TextView = findViewById(R.id.textView_title)
        val catName = intent.getStringExtra("name")

        if (catName != null) {
            name = catName
            title.text = name
            readJsonCatData(name)
        }

        val descr: TextView = findViewById(R.id.textView_descr)
        descr.text = descrtiption

        val img: ImageView = findViewById(R.id.imageView)
        val path = "@drawable/$imgPath"
        img.setImageResource(resources.getIdentifier(path, null, packageName))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        saveNumbers()
        init()
        getNumbers()

        val title: TextView = findViewById(R.id.textView_title)
        val descr: TextView = findViewById(R.id.textView_descr)

        title.text = name
        descr.text = descrtiption
    }

    private fun saveNumbers() {
        val settings: SharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = settings.edit()

        editor.putString(NAT_VALUE, findViewById<TextView>(R.id.textView_nat).text.toString())
        editor.putString(FIB_VALUE, findViewById<TextView>(R.id.textView_fib).text.toString())
        editor.putString(COL_VALUE, findViewById<TextView>(R.id.textView_col).text.toString())

        editor.apply()
    }

    private fun getNumbers() {
        val settings: SharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)

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

    private fun readJsonCatData(catName: String) {
        val text = LoadData("catData.json")
        val ob = JSONObject(text)
        val cat = ob.getJSONObject(catName)

        descrtiption = cat.getString("description")
        imgPath = cat.getString("img")
    }

    private fun LoadData(inFile: String?): String? {
        var tContents: String? = ""
        try {
            val stream: InputStream = assets.open(inFile!!)
            val size: Int = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            tContents = String(buffer)
        } catch (e: IOException) {
            // Handle exceptions here
        }
        return tContents
    }
}