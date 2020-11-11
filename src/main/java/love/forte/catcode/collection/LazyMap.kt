/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  parent
 * File     LazyMap.kt
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 */

@file:JvmName("LazyMaps")

package love.forte.catcode.collection

/** Simple entry implementation. */
private data class SimpleEntry<K, V>(override val key: K, override val value: V) : Map.Entry<K, V>
/** Simple mutable entry implementation. */
private data class SimpleMutableEntry<K, V>(override val key: K, override var value: V) :
    MutableMap.MutableEntry<K, V> {
    override fun setValue(newValue: V): V = value.apply { value = newValue }
}


/**
 * 不需要初始化的lazy实例。
 */
private data class NoNeedInitializeLazy<T>(override val value: T) : Lazy<T> {
    override fun isInitialized(): Boolean = true
}

/** 通过一个实例对象构建一个 [Lazy] 实例。 */
public fun <T> lazyValue(value: T): Lazy<T> = NoNeedInitializeLazy(value)


/**
 * lazy map, 可以通过一个 `Map<K, Lazy<V>>` 实例进行实例化，
 * 其内部的value是懒加载的。
 * @param K Key type.
 * @param V Value type.
 * @property map Map<K, Lazy<V>> map instance.
 * @constructor
 */
public class LazyMap<K, V>
@JvmOverloads
constructor(
    private val map: Map<K, Lazy<V>> = mutableMapOf(),
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


/**
 * lazy map, 可以通过一个 `MutableMap<K, Lazy<V>>` 实例进行实例化，
 * 其内部的value是懒加载的。
 *
 * 额外提供了一个 [put] 方法以实现存入可计算的懒加载值。
 *
 * @param K Key type.
 * @param V Value type.
 * @property mode Lazy的懒加载策略，在 [put] 的时候会使用此策略。默认为 [LazyThreadSafetyMode.PUBLICATION].
 * @property map Map<K, Lazy<V>> map instance.
 * @constructor
 */
public class MutableLazyMap<K, V>
@JvmOverloads
constructor(
    private val mode: LazyThreadSafetyMode = LazyThreadSafetyMode.PUBLICATION,
    private val map: MutableMap<K, Lazy<V>> = mutableMapOf(),
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

    /**
     * 添加一个待计算的懒加载值。
     * @param key Key.
     * @param initializer 初始化函数.
     * @return V?
     */
    fun put(key: K, initializer: () -> V): V? {
        return map.put(key, lazy(mode = mode, initializer = initializer))?.value
    }

    override fun putAll(from: Map<out K, V>) {
        map.putAll(from.mapValues { lazyValue(it.value) })
    }


    override fun remove(key: K): V? = map.remove(key)?.value


}