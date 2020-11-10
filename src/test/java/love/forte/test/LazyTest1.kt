@file:JvmName("LazyMaps")
package love.forte.test


private data class SimpleEntry<K, V>(override val key: K, override val value: V) : Map.Entry<K, V>
private data class SimpleMutableEntry<K, V>(override val key: K, override var value: V) : MutableMap.MutableEntry<K, V> {
    override fun setValue(newValue: V): V = value.apply { value = newValue }
}


/**
 * 不需要初始化的lazy实例。
 */
private data class NoNeedInitializeLazy<T>(override val value: T) : Lazy<T> {
    override fun isInitialized(): Boolean = true
}

public fun <T> lazyValue(value: T) : Lazy<T> = NoNeedInitializeLazy(value)



public class LazyMap<K, V>(
    private val map: Map<K, Lazy<V>>,
) : Map<K, V> {
    @Suppress("UNCHECKED_CAST")
    override val entries: Set<Map.Entry<K, V>>
        get() = map.entries.asSequence().map { SimpleEntry(it.key, it.value) as Map.Entry<K, V> }.toSet()

    override val keys: Set<K>
        get() = map.keys

    override val size: Int
        get() = map.size

    override val values: Collection<V>
        get() = map.values.map { it.value }


    override fun containsKey(key: K): Boolean = map.containsKey(key)

    /**
     * 判断是否存在某个value。
     * 尽可能不要使用此方法，会导致大量的lazy-value被初始化。
     */
    override fun containsValue(value: V): Boolean = map.values.any { it.value == value }

    override operator fun get(key: K): V? = map[key]?.value

    override fun isEmpty(): Boolean = map.isEmpty()
}




public class MutableLazyMap<K, V>(
    private val mode: LazyThreadSafetyMode = LazyThreadSafetyMode.PUBLICATION,
    private val map: MutableMap<K, Lazy<V>>,
) : MutableMap<K, V> {

    override val size: Int
        get() = map.size

    override fun containsKey(key: K): Boolean = map.containsKey(key)

    override fun containsValue(value: V): Boolean = map.values.any { it.value == value }

    override operator fun get(key: K): V? = map[key]?.value

    override fun isEmpty(): Boolean = map.isEmpty()

    override val keys: MutableSet<K>
        get() = map.keys

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = map.entries.asSequence().map { SimpleMutableEntry(it.key, it.value.value) }.toMutableSet()


    override val values: MutableCollection<V>
        get() = map.values.asSequence().map { it.value }.toMutableList()



    override fun clear() {
        map.clear()
    }

    override fun put(key: K, value: V): V? {
        return map.put(key, lazyValue(value))?.value
    }


    fun put(key: K, initializer: () -> V): V? {
        return map.put(key, lazy(mode = mode, initializer = initializer))?.value
    }

    override fun putAll(from: Map<out K, V>) {
        map.putAll(from.mapValues { lazyValue(it.value) })
    }



    override fun remove(key: K): V? = map.remove(key)?.value


}