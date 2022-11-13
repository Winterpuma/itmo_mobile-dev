package com.example.mobile_dev

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
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

        setImg()
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

        setImg()
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

    private fun readJsonCatData(catName: String) {
        val lang: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0).language
        } else {
            resources.configuration.locale.language
        }
        val text: String = loadData("catData-$lang.json") ?: loadData("catData.json")!!
        val ob = JSONObject(text)
        val cat = ob.getJSONObject(catName)

        descrtiption = cat.getString("description")
        imgPath = cat.getString("img")
    }

    private fun loadData(inFile: String): String? {
        val tContents: String?
        try {
            val stream: InputStream = assets.open(inFile)
            val size: Int = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            tContents = String(buffer)
        } catch (e: IOException) {
            return null
        }
        return tContents
    }

    private fun setImg() {
        val img: ImageView = findViewById(R.id.imageView)
        img.setImageResource(resources.getIdentifier("@drawable/$imgPath", null, packageName))
    }
}