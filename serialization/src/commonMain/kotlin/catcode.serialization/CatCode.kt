package catcode.serialization

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule



/**
 *
 * @author ForteScarlet
 */
class CatCode(
        override val serializersModule: SerializersModule = EmptySerializersModule,
) : StringFormat {
    override fun <T> decodeFromString(deserializer: DeserializationStrategy<T>, string: String): T {
        TODO("Not yet implemented")
    }

    override fun <T> encodeToString(serializer: SerializationStrategy<T>, value: T): String {
        val encoder = CatEncoder(serializersModule)
        encoder.encodeSerializableValue(serializer, value)
        return "..."
    }
}