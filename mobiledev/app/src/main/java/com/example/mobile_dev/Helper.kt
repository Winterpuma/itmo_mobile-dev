package com.example.mobile_dev

import android.content.Context
import android.widget.Toast


fun showToast(str : String, applicationContext: Context) {
    Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
}