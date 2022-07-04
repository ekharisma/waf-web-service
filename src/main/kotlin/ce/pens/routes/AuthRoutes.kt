package ce.pens.routes

import ce.pens.entity.User
import ce.pens.entity.request.UserRequest
import ce.pens.entity.response.WebResponse
import ce.pens.repository.User.UserRepository
import ce.pens.util.HashUtil
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import java.util.*

fun Route.authRoute() {
    val logger = KotlinLogging.logger {}
    val dao = UserRepository()
    route("/login") {
        post {
            val user = call.receive<UserRequest>()
            val userDB = dao.getByUsername(user.username)
            if (userDB == null) {
                val payload = WebResponse(
                    status = "not found",
                    message = "fail to find user with username: ${user.username}"
                )
                return@post call.respond(HttpStatusCode.NotFound, payload)
            }
            logger.info { "Pass DB: ${userDB!!.password}" }
            logger.info { "Pass Request: ${user.password}" }
            if (HashUtil.sha1(user.password) != userDB!!.password) {
                logger.info { "password is not equal" }
                val payload = WebResponse(
                    status = "access denied",
                    message = "wrong username or password"
                )
                return@post call.respond(HttpStatusCode.Unauthorized, payload)
            }
            val token = JWT.create()
                .withClaim("username", userDB.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 600_000L))
                .sign(Algorithm.HMAC256("secret"))
            val payload = WebResponse(
                status = "success",
                message = token
            )
            return@post call.respond(HttpStatusCode.OK, payload)
        }
    }
    route("/register") {
        post {
            val userRequest = call.receive<UserRequest>()
            val user = User(
                username = userRequest.username,
                password = userRequest.password,
            )
            val userDb = dao.getByUsername(userRequest.username)
            if (userDb == null) {
                dao.create(user)
                val response = WebResponse(
                    status = "created",
                    message = "user: ${user.username} created"
                )
                return@post call.respond(HttpStatusCode.Created, response)
            } else {
                val response = WebResponse(
                    status = "failed",
                    message = "user ${user.username} already exist"
                )
                return@post call.respond(HttpStatusCode.Conflict, response)
            }
        }
    }
}