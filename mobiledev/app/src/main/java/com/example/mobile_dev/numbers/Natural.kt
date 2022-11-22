package com.example.mobile_dev.numbers

class Natural(a: Int = 0) : ISequence {

    override var current = a

    override fun next(): Int {
        return ++current
    }
}