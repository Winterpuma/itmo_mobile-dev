package com.example.mobile_dev.lab3.models

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(val id: String,
                       val userId: String,
                       val description: String,
                       val moneyDif: Int) {
    override fun toString(): String = "$description: $moneyDif"
}