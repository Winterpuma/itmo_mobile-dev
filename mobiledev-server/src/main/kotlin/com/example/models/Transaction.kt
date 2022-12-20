package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(val id: String,
                       val userId: Int,
                       val description: String,
                       val moneyDif: Int)

val transactionStorage = mutableListOf<Transaction>()