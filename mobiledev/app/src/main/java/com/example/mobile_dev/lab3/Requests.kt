package com.example.mobile_dev.lab3

import com.example.mobile_dev.lab3.models.Transaction
import com.example.mobile_dev.lab3.models.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking

class Requests {
    private val basicAddress = "http://10.0.2.2:8080"
    private val client = HttpClient(Android) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        install(ContentNegotiation) {
            json()
        }
    }

    fun getAllUsers(): List<User>{
        return runBlocking {
            return@runBlocking client.get("$basicAddress/user").body()
        }
    }

    fun getUser(id: Int): User {
        return runBlocking {
            return@runBlocking client.get("$basicAddress/user/$id").body()
        }
    }

    fun createUser(id: String, money: Int): HttpResponse {
        return runBlocking {
            return@runBlocking client.post("$basicAddress/user") {
                contentType(ContentType.Application.Json)
                setBody(User(id, money))
            }
        }
    }

    fun deleteUser(id: String): HttpResponse {
        return runBlocking {
            return@runBlocking client.delete("$basicAddress/user/$id")
        }
    }

    fun addMoney(id: String, amount: Int): User {
        return runBlocking {
            return@runBlocking client.put("$basicAddress/user/$id/add/$amount").body()
        }
    }

    fun withdrawMoney(id: String, amount: Int): User {
        return runBlocking {
            return@runBlocking client.put("$basicAddress/user/$id/withdraw/$amount").body()
        }
    }

    fun luckyMoney(id: String, amount: Int): User {
        return runBlocking {
            return@runBlocking client.put("$basicAddress/user/$id/withdraw/$amount").body()
        }
    }

    fun getAllUserTransactions(id: String): List<Transaction>{
        return runBlocking {
            return@runBlocking client.get("$basicAddress/user/$id/transaction").body()
        }
    }
}