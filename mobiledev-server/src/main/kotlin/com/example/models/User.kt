package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: String, var money: Int)

val userStorage = mutableListOf<User>()