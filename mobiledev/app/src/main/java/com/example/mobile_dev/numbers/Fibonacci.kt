package com.example.mobile_dev.numbers

import kotlin.math.roundToInt
import kotlin.math.sqrt

class Fibonacci(a: Int = 0, b: Int = 1) : ISequence {

    private var _previous = a
    override var current = b
        set(value) {
            _previous = getPreviousNumber(value)
            field = value
        }

    override fun next(): Int {
        val tmp = _previous

        _previous = current
        current += tmp

        return current
    }

    private fun getPreviousNumber(n: Int): Int {
        val a = n / ((1 + sqrt(5.0)) / 2.0)
        return a.roundToInt()
    }
}