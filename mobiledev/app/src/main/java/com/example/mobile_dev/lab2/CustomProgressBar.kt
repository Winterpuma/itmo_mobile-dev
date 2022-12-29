package com.example.mobile_dev.lab2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.mobile_dev.R

class CustomProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint: Paint = Paint()

    var progress: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint.color = ResourcesCompat.getColor(resources, R.color.purple_500, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (progress == 0f)
            return

        canvas.drawCircle(
            width.toFloat()/2,
            height.toFloat()/2,
            progress,
            paint
        )
    }
}