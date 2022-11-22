package com.example.mobile_dev.numbers

class Collatz(a: Int = 3) : ISequence {

    override var current = a

    override fun next(): Int {
        if (current % 2 == 0)
            current /= 2
        else
            current = current * 3 + 1

        return current
    }
}