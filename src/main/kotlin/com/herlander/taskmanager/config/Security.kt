
package com.herlander.taskmanager.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    val env = dotenv()

    val jwtSecret = env["JWT_SECRET"]
        ?: error("JWT_SECRET is not configured")

    val jwtIssuer = env["JWT_ISSUER"]
        ?: error("JWT_ISSUER is not configured")

    val jwtAudience = env["JWT_AUDIENCE"]
        ?: error("JWT_AUDIENCE is not configured")

    val jwtRealm = env["JWT_REALM"]
        ?: error("JWT_REALM is not configured")

    install(Authentication) {
        jwt("auth-jwt") {
            realm = jwtRealm

            verifier(
                JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withIssuer(jwtIssuer)
                    .withAudience(jwtAudience)
                    .build()
            )

            validate { credential ->
                if (jwtAudience in credential.payload.audience) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}

