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
    internal val map: Map<K, Lazy<V>> = mapOf(),
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


public fun <K, V> Map<K, V>.toLazyMap(): LazyMap<K, V> {
    return LazyMap(mapValues { lazyValue(it.value) })
}


public fun <K, V> Map<K, V>.toMutableLazyMap(): MutableLazyMap<K, V> {
    return MutableLazyMap(mapValues { lazyValue(it.value) }.toMutableMap())
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
    internal val map: MutableMap<K, Lazy<V>> = mutableMapOf(),
    private val mode: LazyThreadSafetyMode = LazyThreadSafetyMode.PUBLICATION,
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


/**
 * 将一个 [MutableLazyMap] 作为一个 [LazyMap].
 * 不会复制其中的什么属性，因此如果原来的 [MutableLazyMap] 发生变动，[LazyMap] 也会变。
 * @receiver MutableLazyMap<K, V>
 * @return LazyMap<K, V>
 */
public fun <K, V> MutableLazyMap<K, V>.asLazyMap(): LazyMap<K, V> = LazyMap(map)

/**
 * 将一个 [MutableLazyMap] 作为一个 [LazyMap].
 *
 * 会将 [MutableLazyMap.map] 复制一份，原 [MutableLazyMap] 发送变化不会变动结果。
 *
 * @receiver MutableLazyMap<K, V>
 * @return LazyMap<K, V>
 */
public fun <K, V> MutableLazyMap<K, V>.toLazyMap(): LazyMap<K, V> = LazyMap(map.toMap())

/**
 * 将一个 [LazyMap] 转化为一个 [MutableLazyMap].
 *
 * 会将 [LazyMap.map] 复制一份。
 *
 * @receiver LazyMap<K, V>
 * @return MutableLazyMap<K, V>
 */
public fun <K, V> LazyMap<K, V>.toMutableMap(): MutableLazyMap<K, V> = MutableLazyMap(map.toMutableMap())
