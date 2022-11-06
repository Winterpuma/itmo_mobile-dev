package com.example.mobile_dev

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
            setContentView(R.layout.details)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
            setContentView(R.layout.details)
        }
        else {
            Toast.makeText(this, "xz", Toast.LENGTH_SHORT).show()
        }
    }

}