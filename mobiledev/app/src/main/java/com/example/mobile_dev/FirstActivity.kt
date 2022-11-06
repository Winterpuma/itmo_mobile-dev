package com.example.mobile_dev

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class FirstActivity : AppCompatActivity() {

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

    }

}