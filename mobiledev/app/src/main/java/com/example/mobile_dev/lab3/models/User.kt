package com.example.mobile_dev.lab3.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: String, var money: Int)