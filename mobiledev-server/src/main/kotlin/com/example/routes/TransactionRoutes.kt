package com.example.routes

import com.example.models.*
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.transactionRouting() {
    get("/transaction") {
        val id = getUserId(call.request.queryParameters)

        if (id != null) {
            val res = transactionStorage.filter { it.userId == id }
            call.respond(res)
        }
        
        call.respond(transactionStorage)
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

private fun getUserId(parameters: Parameters): Int? {
    return parameters["userid"]?.toInt()
}