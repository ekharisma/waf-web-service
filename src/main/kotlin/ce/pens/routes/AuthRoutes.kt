package ce.pens.routes

import ce.pens.entity.User
import ce.pens.entity.userStorage
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.authRoute() {
    route("/login") {
        post {
            val user = call.receive<User>()
            val userDB = userStorage.find { it.username == user.username }?:
                return@post call.respondText("User not found", status=HttpStatusCode.NotFound)
            val token = JWT.create()
                .withClaim("username", userDB.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 600_000L))
                .sign(Algorithm.HMAC256("secret"))
            call.respond(hashMapOf("token" to token))
        }
    }
    route("/register") {
        post {
            val user = call.receive<User>()
            userStorage.add(user)
            call.respondText("User registered", status = HttpStatusCode.Created)
        }
    }
}