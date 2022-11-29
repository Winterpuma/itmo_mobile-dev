package com.example.mobile_dev.lab1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.mobile_dev.INTENT_CAT_NAME
import com.example.mobile_dev.R
import com.example.mobile_dev.TAG
import com.google.android.material.switchmaterial.SwitchMaterial


class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_first)

        setListViewOperations()
        setButtonsListeners()
        setImgResources()
    }

    private fun setListViewOperations() {
        val buttonHide: Button = findViewById(R.id.button_hide)
        val listView: ListView = findViewById(R.id.listView)
        val catNames = resources.getStringArray(R.array.cat_names)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, catNames
        )

        listView.adapter = adapter

        listView.onItemClickListener = OnItemClickListener { parent, _, position, _ ->
            val entry = parent.adapter.getItem(position)
            val intent = Intent(this@FirstActivity, DetailsActivity::class.java)
            val message: String = entry.toString()
            intent.putExtra(INTENT_CAT_NAME, message)
            startActivity(intent)
        }

        buttonHide.setOnClickListener {
            Log.i(TAG,"Button Hide pressed")
            if (listView.visibility == View.INVISIBLE)
                listView.visibility = View.VISIBLE
            else
                listView.visibility = View.INVISIBLE
        }
    }

    private fun setButtonsListeners() {
        val buttonToast: Button = findViewById(R.id.button_toast)
        val buttonChangeLabel: Button = findViewById(R.id.button_set_label)
        val switchColor: SwitchMaterial = findViewById(R.id.switch_color)

        buttonToast.setOnClickListener {
            Log.i(TAG,"Button Toast pressed")
            Toast.makeText(applicationContext, "Toast yes!", Toast.LENGTH_SHORT).show()
        }

        buttonChangeLabel.setOnClickListener {
            val labelText: TextView = findViewById(R.id.label_text)
            val editText: EditText = findViewById(R.id.edit_text)

            labelText.text = editText.text
        }

        switchColor.setOnCheckedChangeListener { _, isChecked ->
            val labelText: TextView = findViewById(R.id.label_text)

            if (isChecked) {
                labelText.setTextColor(ResourcesCompat.getColor(resources, R.color.teal_700, null))
            }
            else {
                labelText.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
            }
        }
    }

    private fun setImgResources() {
        val img1 = findViewById<ImageView>(R.id.image1)
        val img2 = findViewById<ImageView>(R.id.image2)
        val img3 = findViewById<ImageView>(R.id.image3)

        img1.setImageResource(resources.getIdentifier("@drawable/pic1", null, packageName))
        img2.setImageResource(resources.getIdentifier("@drawable/pic2", null, packageName))
        img3.setImageResource(resources.getIdentifier("@drawable/pic3", null, packageName))
    }

}