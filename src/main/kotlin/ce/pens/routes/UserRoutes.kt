package ce.pens.routes

import ce.pens.entity.User
import ce.pens.entity.response.WebResponse
import ce.pens.repository.User.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.userRouting() {
    val dao = UserRepository()
    route("/user") {
        get {
            val users = dao.getAll()
            call.respond(status = HttpStatusCode.OK, users)
        }
        get("{id?}") {
            val id = call.parameters["id"]?:
                return@get call.respondText("Missing ID", status=HttpStatusCode.BadRequest)
            val users = dao.get(Integer.parseInt(id))
            if (users == null) {
                val response = WebResponse(
                    status = "not found",
                    message = ""
                )
                val payload = Json.encodeToString(response)
                call.respondText(payload, status = HttpStatusCode.NotFound)
            }
            val response = WebResponse(
                status = "success",
                message = users
            )
            val payload = Json.encodeToString(response)
            call.respond(status = HttpStatusCode.OK, payload)
        }
        post {
            val user = call.receive<User>()
            dao.create(user)
            call.respondText("User stored", status = HttpStatusCode.Created)
        }
    }
}