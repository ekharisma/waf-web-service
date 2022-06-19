package ce.pens.routes

import ce.pens.entity.User
import ce.pens.entity.userStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRouting() {
    route("/user") {
        get {
            if (userStorage.isNotEmpty()) {
                call.respond(userStorage)
            }
            call.respondText("No user found", status = HttpStatusCode.NotFound)
        }
        get("{id?}") {
            val id = call.parameters["id"]?:
                return@get call.respondText("Missing ID", status=HttpStatusCode.BadRequest)
            val user = userStorage.find { it.id == id }?:
                return@get call.respondText("No customer found with id: $id", status = HttpStatusCode.NotFound)
            call.respond(user)
        }
        post {
            val user = call.receive<User>()
            userStorage.add(user)
            call.respondText("User stored", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = call.parameters["id"]?:
                return@delete call.respond(HttpStatusCode.BadRequest)
            if (userStorage.removeIf{it.id == id}) {
                call.respondText("User removed", status = HttpStatusCode.OK)
            }
            call.respondText("User not found", status = HttpStatusCode.NotFound)
        }
    }
}