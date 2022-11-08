package com.example.mobile_dev

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible


class FirstActivity : AppCompatActivity() {

    private val TAG = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_first)

        val listView: ListView = findViewById(R.id.listView)
        val catNames = resources.getStringArray(R.array.cat_names)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, catNames
        )

        listView.adapter = adapter

        listView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val entry = parent.adapter.getItem(position)
            val intent = Intent(this@FirstActivity, DetailsActivity::class.java)
            val message: String = entry.toString()
            intent.putExtra("name", message)
            startActivity(intent)
        })

        val buttonHide: Button = findViewById(R.id.button_hide)
        val buttonToast: Button = findViewById(R.id.button_toast)
        val buttonChangeLabel: Button = findViewById(R.id.button_set_label)
        val switchColor: Switch = findViewById(R.id.switch_color)

        buttonHide.setOnClickListener {
            Log.i(TAG,"Button Hide pressed")
            listView.isVisible = !listView.isVisible
        }

        buttonToast.setOnClickListener {
            Log.i(TAG,"Button Toast pressed")
            Toast.makeText(applicationContext, "Toast yes!", Toast.LENGTH_SHORT).show()
        }

        buttonChangeLabel.setOnClickListener() {
            val labelText: TextView = findViewById(R.id.label_text)
            val editText: EditText = findViewById(R.id.edit_text)

            labelText.text = editText.text
        }

        switchColor.setOnCheckedChangeListener { buttonView, isChecked ->
            val labelText: TextView = findViewById(R.id.label_text)

            if (isChecked) {
                labelText.setTextColor(ResourcesCompat.getColor(resources, R.color.teal_700, null))
            }
            else {
                labelText.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
            }
        }

        val img1 = findViewById<ImageView>(R.id.image1)
        val img2 = findViewById<ImageView>(R.id.image2)
        val img3 = findViewById<ImageView>(R.id.image3)

        img1.setImageResource(resources.getIdentifier("@drawable/pic1", null, packageName))
        img2.setImageResource(resources.getIdentifier("@drawable/pic2", null, packageName))
        img3.setImageResource(resources.getIdentifier("@drawable/pic3", null, packageName))
    }

}