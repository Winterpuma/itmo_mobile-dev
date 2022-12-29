package com.example.mobile_dev.lab2

class SpeedParameters(@Volatile var step: Int, @Volatile var sleep: Long) {
    fun increment(): String {
        step += 1
        return step.toString()
    }

    fun decrement(): String {
        if (step > 0)
            step -= 1
        return step.toString()
    }
}