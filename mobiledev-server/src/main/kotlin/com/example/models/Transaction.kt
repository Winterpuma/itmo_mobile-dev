package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

@Serializable
data class Transaction(val id: String,
                       val userId: Int,
                       val description: String,
                       val moneyDif: Int)

object Transactions : Table() {
    val id = uuid("id")
    val userId = integer("userId")
    val description = varchar("description", 128)
    val moneyDif = integer("moneyDif")

    override val primaryKey = PrimaryKey(id)
}
