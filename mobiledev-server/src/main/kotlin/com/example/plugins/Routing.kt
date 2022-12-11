package com.example.plugins

import com.example.routes.transactionRouting
import com.example.routes.userMoneyRouting
import com.example.routes.userRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        userRouting()
        userMoneyRouting()
        transactionRouting()
    }
}
