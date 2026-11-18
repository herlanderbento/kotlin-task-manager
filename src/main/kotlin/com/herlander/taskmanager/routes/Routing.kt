package com.herlander.taskmanager.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello, World!")
        }

        authenticate("auth-jwt") {
            get("/tasks"){
                call.respondText("Welcome to your tasks!", status = HttpStatusCode.OK)
            }
        }
    }
}