package love.forte.catcode.collection


/** Simple entry implementation. */
internal data class SimpleEntry<K, V>(override val key: K, override val value: V) : Map.Entry<K, V>

/** Simple mutable entry implementation. */
internal data class SimpleMutableEntry<K, V>(override val key: K, override var value: V) :
    MutableMap.MutableEntry<K, V> {
    override fun setValue(newValue: V): V = value.apply { value = newValue }
}