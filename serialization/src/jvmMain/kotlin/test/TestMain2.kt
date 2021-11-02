package test

import catcode.serialization.CatCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy

@Serializable
data class User(val name: String, val age: Int, @SerialName("u") val user: User? = null)


fun main() {

    // Json.encodeToString()
    val u = User("forli", 14)

    val serial: SerializationStrategy<User> = User.serializer()

    println(CatCode().encodeToString(serial, User("Forte", 18, u)))


}