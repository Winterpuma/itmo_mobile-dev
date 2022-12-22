package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

@Serializable
data class User(val id: Int, var money: Int)

object Users : Table() {
    val id = integer("id").autoIncrement()
    val money = integer("money")

    override val primaryKey = PrimaryKey(id)
}
