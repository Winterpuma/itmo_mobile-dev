package com.example.mobile_dev.lab3

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking

class Requests {
    private val basicAddress = "http://10.0.2.2:8080"
    private val client = HttpClient(Android) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
    }

    fun getUser(id: Int): String {
        return runBlocking {
            return@runBlocking client.get("$basicAddress/user/$id").body()
        }
    }
}