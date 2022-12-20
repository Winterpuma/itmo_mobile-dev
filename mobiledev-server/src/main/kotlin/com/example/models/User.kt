package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Int, var money: Int)

val userStorage = mutableListOf<User>()
var userId = 0
