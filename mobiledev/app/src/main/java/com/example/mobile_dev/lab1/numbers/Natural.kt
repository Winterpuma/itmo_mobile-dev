package com.example.mobile_dev.lab1.numbers

class Natural(a: Int = 0) : ISequence {

    override var current = a

    override fun next(): Int {
        return ++current
    }
}