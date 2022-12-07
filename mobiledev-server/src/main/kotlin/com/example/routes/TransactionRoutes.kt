package com.example.routes

import com.example.models.*
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.transactionRouting() {
    get("/transaction") {
        if (transactionStorage.isNotEmpty()) {
            call.respond(transactionStorage)
        }
    }

    get("/transaction/withdraw") {
        val res = transactionStorage.filter { it.moneyDif < 0 }
        call.respond(res)
    }

    get("/transaction/deposit") {
        val res = transactionStorage.filter { it.moneyDif > 0 }
        call.respond(res)
    }
}