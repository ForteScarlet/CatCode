/*
 * Copyright (c) 2020. ForteScarlet
 *
 * catCode库相关代码使用 MIT License 开源，请遵守协议相关条款。
 *
 * about MIT: https://opensource.org/licenses/MIT
 *
 *
 *
 *
 */

package catcode.collection

/**
 * 一个类似[Map]的猫猫Map。
 *
 * 因为[Map]在`Java`中进行实现还是会有些问题，所以杂事使用原生Map，并提供一个转化为[Map]的接口。
 *
 * 这样也可以为`Java`的调用提供更多的限制。
 */
public interface CatMap<K, out V> {

    /**
     * 转化为 [Map]。
     */
    public fun toMap(): Map<K, V>

    /**
     * 此码中的所有键值对。
     */
    public val entries: Set<Map.Entry<K, V>>

    /**
     * 此码中的所有键。
     */
    public val keys: Set<K>

    /**
     * 此码中的所有值。
     */
    public val values: Collection<V>

    /**
     * 此码中的键值对参数数量。
     */
    public val size: Int

    /**
     * 是否包含某个键。
     */
    public fun containsKey(key: K): Boolean

    /**
     * 是否包含某个值。
     */
    public fun containsValue(value: @UnsafeVariance V): Boolean

    /**
     * 是否没有参数。
     */
    public fun isEmpty(): Boolean

    /**
     * 获取某个键对应的值。
     */
    public operator fun get(key: K): V?

    /**
     * 获取某个键对应的值。如果不存在则返回一个默认值。
     */
    public fun getOrDefault(key: K, defaultValue: @UnsafeVariance V): V = get(key) ?: defaultValue

    /**
     * foreach entries.
     */
    public fun forEach(action: (K, V) -> Unit) {
        entries.forEach {
            action(it.key, it.value)
        }
    }
}


/**
 * save as [MutableCatMap.put], but is operator fun.
 */
public operator fun <K, V> MutableCatMap<K, V>.set(key: K, value: V): V? = put(key, value)


/**
 * [CatMap] 委托为 [Map]
 */
public open class CatMapDelegation<K, V>(protected val catMap: CatMap<K, V>) : Map<K, V> {
    override val entries: Set<Map.Entry<K, V>>
        get() = catMap.entries
    override val keys: Set<K>
        get() = catMap.keys
    override val size: Int
        get() = catMap.size
    override val values: Collection<V>
        get() = catMap.values

    override fun containsKey(key: K): Boolean = catMap.containsKey(key)
    override fun containsValue(value: V): Boolean = catMap.containsValue(value)
    override fun get(key: K): V? = catMap[key]
    override fun isEmpty(): Boolean = catMap.isEmpty()
}


/**
 * [CatMap] 委托为 [Map]
 */
public fun <K, V> catToMap(catMap: CatMap<K, V>): Map<K, V> = CatMapDelegation(catMap)

/**
 * [CatMap] 委托为 [Map]
 */
public fun <K, V> CatMap<K, V>.mapDelegation(): Map<K, V> = catToMap(this)


/**
 * [CatMap] 委托为 [Map]
 */
public open class MutableCatMapDelegation<K, V>(catMap: MutableCatMap<K, V>) : CatMapDelegation<K, V>(catMap),
    MutableMap<K, V> {

    private val mutableNekoMap get() = (catMap as MutableCatMap)

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = mutableNekoMap.entries
    override val keys: MutableSet<K>
        get() = mutableNekoMap.keys
    override val values: MutableCollection<V>
        get() = mutableNekoMap.values

    override fun clear() {
        mutableNekoMap.clear()
    }

    override fun put(key: K, value: V): V? = mutableNekoMap.put(key, value)

    override fun putAll(from: Map<out K, V>) {
        from.forEach { (k: K, v: V) ->
            mutableNekoMap.put(k, v)
        }

    }

    override fun remove(key: K): V? = mutableNekoMap.remove(key)
}


/**
 * 一个类似[MutableMap]的猫猫可变Map。
 *
 * 因为[Map]在`Java`中进行实现还是会有些问题，所以杂事使用原生Map，并提供一个转化为[Map]的接口。
 *
 * 这样也可以为`Java`的调用提供更多的限制。
 */
public interface MutableCatMap<K, V> : CatMap<K, V> {
    /**
     * 转化为[MutableMap]。
     */
    override fun toMap(): MutableMap<K, V>

    /**
     * 置入一个键对应的值。
     *
     * @return 如果存在旧值，返回旧值。
     */
    public fun put(key: K, value: V): V?

    /**
     * 移除一个可能存在的键对应的值。
     *
     * @return 被移除的值。可能不存在。
     */
    public fun remove(key: K): V?

    /**
     * 存入多个键值对。
     */
    public fun putAll(from: CatMap<out K, V>) {
        from.forEach { k, v ->
            put(k, v)
        }
    }

    /**
     * 存入多个键值对。
     */
    public fun putAll(from: Map<out K, V>) {
        from.forEach { (k: K, v: V) ->
            put(k, v)
        }
    }


    /**
     * 此码中的所有键值对。
     */
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>

    /**
     * 此码中的所有键。
     */
    override val keys: MutableSet<K>

    /**
     * 此码中的所有值。
     */
    override val values: MutableCollection<V>

    /**
     * 清除所有的键值对。
     */
    public fun clear()

}