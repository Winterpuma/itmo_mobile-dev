package com.example.mobile_dev.lab3.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Int, val money: Int)