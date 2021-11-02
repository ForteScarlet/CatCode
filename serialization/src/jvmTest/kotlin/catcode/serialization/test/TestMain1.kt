package catcode.serialization.test

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.decodeFromMap
import kotlinx.serialization.properties.encodeToMap

@Serializable
data class School(val name: String)

@Serializable
data class User(val name: String,
                val age: Int,
                val school: School,
                val schools: List<School>,
                val nums: List<Int>
                )


@OptIn(ExperimentalSerializationApi::class)
fun main() {

    val json: Json = Json

    // Json.encodeToString()

    val s = School("Sch!")
    val u = User("forli", 14, s, listOf(s, s, s), listOf(5, 6, 7))

    val p = Properties(EmptySerializersModule)

    val serial: SerializationStrategy<User> = User.serializer()
    val map = p.encodeToMap(u)
    println(map)

    val u2 = p.decodeFromMap<User>(map)

    println(u2)

}