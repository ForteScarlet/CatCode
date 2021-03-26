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

@file:Suppress("unused")
@file:JvmName("NekoCodes")
@file:JvmMultifileClass
package catcode.codes

import catcode.*
import catcode.collection.*


private val MAP_SPLIT_REGEX = Regex(CAT_KV)

/**
 * 猫猫码封装类, 以[Map]作为参数载体
 *
 * [MapNeko]通过[Map]保存各项参数，其对应的[可变类型][MutableNeko]实例为[MutableMapNeko],
 * 通过[mutable]与[immutable]进行相互转化。
 *
 * 相比较于[Nyanko], [MapNeko]在进行获取、迭代与遍历的时候表现尤佳，
 * 尤其是参数获取相比较于[Nyanko]的参数获取速度有好几百倍的差距。
 *
 * 但是在实例构建与静态参数获取的时候相比于[Nyanko]略逊一筹。
 *
 * @since 1.0-1.11
 * @since 1.8.0
 */
public open class LazyMapNeko
internal constructor(
    private val params: LazyMap<String, String>,
    override var type: String,
    // override val codeType: String
    ) :
    Neko,
    CodeTypeSwitchAble<LazyMapNeko>,
    Map<String, String> by params {

    // constructor(type: String) : this(LazyMap(emptyMap()), type)
    // constructor(type: String, params: Map<String, String>) : this(params.toLazyMap(), type)
    // constructor(type: String, vararg params: CatKV<String, String>) : this(mapOf(*params.toPair()).toLazyMap(), type)
    // constructor(type: String, vararg params: String) : this(mapOf(*params.map {
    //     val split = it.split(delimiters = CAT_KV_SPLIT_ARRAY, false, 2)
    //     split[0] to split[1]
    // }.toTypedArray()).toLazyMap(), type)

    //

    constructor(type: String) : this(lazyMapOf(emptyMap()), type)
    constructor(type: String, params: Map<String, String>) : this(params.toLazyMap(), type)
    constructor(type: String, vararg params: CatKV<String, String>) : this(mapOf(*params.toPair()).toLazyMap(), type)
    constructor(type: String, vararg params: String) : this(mapOf(*params.map {
        val split = it.split(ignoreCase = false, limit = 2, delimiters = CAT_KV_SPLIT_ARRAY)
        split[0] to split[1]
    }.toTypedArray()).toLazyMap(), type)

    // /** internal constructor for mutable kqCode */
    // constructor(mutableKQCode: MutableNeko) : this(mutableKQCode.toMap(), mutableKQCode.type)

    /**
     * 表示此 [Neko] 实例能够切换其 [Neko.codeType] 并得到一个对应的转化结果值。
     */
    override fun switchCodeType(codeType: String): LazyMapNeko {
        if (codeType == this.codeType) return this
        return LazyMapNoraNeko(codeType, params, type)
    }

    /**
     * Returns the length of this character sequence.
     */
    override val length: Int
        get() = toString().length


    /**
     * 获取转义后的字符串
     */
    override fun getNoDecode(key: String) = CatEncoder.encodeParamsOrNull(this[key])

    /**
     * Returns the character at the specified [index] in this character sequence.
     * @throws [IndexOutOfBoundsException] if the [index] is out of bounds of this character sequence.
     * Note that the [String] implementation of this interface in Kotlin/JS has unspecified behavior
     * if the [index] is out of its bounds.
     */
    override fun get(index: Int): Char = toString()[index]

    /**
     * get value or default.
     */
    override fun getOrDefault(key: String, defaultValue: String): String = params.getOrDefault(key, defaultValue)

    /**
     * Returns a new character sequence that is a subsequence of this character sequence,
     * starting at the specified [startIndex] and ending right before the specified [endIndex].
     *
     * @param startIndex the start index (inclusive).
     * @param endIndex the end index (exclusive).
     */
    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
        toString().subSequence(startIndex, endIndex)


    /**
     * toString的值记录。因为是不可变类，因此toString是不会变的
     * 在获取的时候才会去实际计算，且仅计算一次。
     */
    private val _toString: String by lazy(LazyThreadSafetyMode.PUBLICATION) {
        CatCodeUtil.toCat(type, true, map = this)
    }

    /** toString */
    override fun toString(): String = _toString


    /**
     * 转化为参数可变的[MutableNeko]
     */
    override fun asMutable(): MutableNeko = LazyMutableMapNeko(params.toMutableLazyMap(), type)

    /**
     * 转化为不可变类型[Neko]
     */
    override fun asImmutable(): Neko = this // LazyMapNeko(params, type)

    /**
     * 转化为参数可变的[MutableNeko]
     */
    override fun toMutable(): MutableNeko = LazyMutableMapNeko(params.toMutableLazyMap(), type)

    /**
     * 转化为不可变类型[Neko]
     */
    override fun toImmutable(): Neko = LazyMapNeko(params.copy(), type)

    /**
     * 获取 [params] 实例。
     */
    override fun toMap(): Map<String, String> = params.copy()


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LazyMapNeko

        if (params != other.params) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = params.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }


    /** [MapNeko] companion object. */
    companion object Of {
        /** 参数切割用的正则 */
        private val TEMP_SPLIT_REGEX = Regex(" *, *")

        /**
         * 将猫猫码字符串切割为参数列表
         * 返回的键值对为 `type to split`
         */
        @Suppress("NOTHING_TO_INLINE")
        private inline fun splitCode(code: String, codeType: String = CAT_TYPE): CatKV<String, List<String>> {
            var tempText = code.trim()
            // 不是[CAT:开头，或者不是]结尾都不行
            if (!tempText.startsWith(catHead(codeType)) || !tempText.endsWith(CAT_END)) {
                throw IllegalArgumentException("not starts with '$CAT_HEAD' or not ends with '$CAT_END'")
            }
            // 是[CAT:开头，]结尾，切割并转化
            tempText = tempText.substring(4, tempText.lastIndex)

            val split = tempText.split(TEMP_SPLIT_REGEX)
            val type = split[0]
            return type cTo split
        }

        /**
         * 根据猫猫码字符串获取[MapNeko]实例
         */
        @JvmStatic
        @JvmOverloads
        fun byCode(code: String, decode: Boolean = true): LazyMapNeko {
            val (type, split) = splitCode(code)

            return if (split.size > 1) {
                if (decode) {
                    // 参数解码
                    val map = split.subList(1, split.size).map {
                        val sp = it.split(Regex("="), 2)
                        sp[0] to sp[1].deCatParam()
                    }.toMap()
                    LazyMapNeko(map.toLazyMap(), type)
                } else {
                    LazyMapNeko(type, *split.subList(1, split.size).toTypedArray())
                }
            } else {
                LazyMapNeko(type)
            }
        }

        /** 通过map参数获取 */
        @JvmStatic
        fun byMap(type: String, params: Map<String, *>): MapNeko =
            MapNeko(type, params.mapNotNull {
                if (it.value == null) null else it.key to it.value.toString()
            }.toMap())

        /**
         * by lazy map.
         * @return LazyMapNeko
         */
        fun byLazyMap(type: String, params: LazyMap<String, String>): LazyMapNeko =
            LazyMapNeko(params.copy(), type)


        /** 通过键值对获取 */
        @JvmStatic
        fun byKV(type: String, vararg params: CatKV<String, *>): MapNeko =
            MapNeko(type, params.mapNotNull {
                if (it.value == null) null else it.key to it.value.toString()
            }.toMap())

        /** 通过键值对字符串获取 */
        @JvmStatic
        fun byParamString(type: String, vararg params: String): MapNeko = MapNeko(type, *params)

        /**
         * 根据猫猫码字符串获取[MapNeko]实例
         */
        @JvmStatic
        @JvmOverloads
        fun mutableByCode(code: String, decode: Boolean = true): MutableMapNeko {
            val (type, split) = splitCode(code)

            return if (split.size > 1) {
                if (decode) {
                    // 参数解码
                    val map: MutableMap<String, String> = split.subList(1, split.size).map {
                        val sp = it.split(Regex("="), 2)
                        sp[0] to CatDecoder.decodeParams(sp[1])
                    }.toMap().toMutableMap()
                    MutableMapNeko(type, map)
                } else {
                    MutableMapNeko(type, *split.subList(1, split.size).toTypedArray())
                }
            } else {
                MutableMapNeko(type)
            }
        }


        /** 通过map参数获取 */
        @JvmStatic
        fun mutableByMap(type: String, params: Map<String, String>): MutableMapNeko =
            MutableMapNeko(type, params)

        /** 通过键值对获取 */
        @JvmStatic
        fun mutableByKV(type: String, vararg params: CatKV<String, String>): MutableMapNeko =
            MutableMapNeko(type, *params)

        /** 通过键值对字符串获取 */
        @JvmStatic
        fun mutableByParamString(type: String, vararg params: String): MutableMapNeko =
            MutableMapNeko(type, *params)
    }

}

/**
 * [Neko]对应的可变类型, 以[MutableMap]作为载体
 *
 * 目前来讲唯一的[MutableNeko]实例. 通过[MutableMap]作为参数载体需要一定程度的资源消耗，
 * 因此我认为最好应该避免频繁大量的使用[可变类型][MutableMap].
 *
 * 如果想要动态的构建一个[Neko], 也可以试试[CodeBuilder],
 * 其中[StringCodeBuilder]则以字符串操作为主而避免了构建内部[Map]
 *
 * 但是无论如何, 都最好在构建之前便决定好参数
 *
 * @since 1.8.0
 */
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
public class LazyMutableMapNeko
internal constructor(
    private val params: MutableLazyMap<String, String>,
    type: String,
    // codeType: String,
) :
    LazyMapNeko(params, type),
    MutableNeko,
    MutableMap<String, String> by params {
    constructor(type: String) : this(mutableLazyMapOf(), type)
    constructor(type: String, params: Map<String, String>) : this(params.toMutableLazyMap(), type)
    constructor(type: String, vararg params: CatKV<String, String>) : this(mutableMapOf(*params.toPair()).toMutableLazyMap(), type)
    constructor(type: String, vararg params: String) : this(mutableMapOf(*params.map {
        val split = it.split(ignoreCase = false, limit = 2, delimiters = CAT_KV_SPLIT_ARRAY)
        split[0] to split[1]
    }.toTypedArray()).toMutableLazyMap(), type)

    ////

    // constructor(codeType: String, type: String) : this(mutableLazyMapOf(), type, codeType)
    // constructor(codeType: String, type: String, params: Map<String, String>) : this(params.toMutableLazyMap(), type, codeType)
    // constructor(codeType: String, type: String, vararg params: CatKV<String, String>) : this(mutableMapOf(*params.toPair()).toMutableLazyMap(), type, codeType)
    // constructor(codeType: String, type: String, vararg params: String) : this(mutableMapOf(*params.map {
    //     val split = it.split(ignoreCase = false, limit = 2, delimiters = CAT_KV_SPLIT_ARRAY)
    //     split[0] to split[1]
    // }.toTypedArray()).toMutableLazyMap(), type, codeType)

    /**
     * 转化为参数可变的[MutableNeko]
     */
    override fun asMutable(): LazyMutableMapNeko = this
        // LazyMutableMapNeko(params, type)


    /**
     * 转化为不可变类型[Neko]
     */
    override fun asImmutable(): LazyMapNeko = this
        // LazyMapNeko(params, type)

    /**
     * 转化为参数可变的[MutableNeko]
     */
    override fun toMutable(): LazyMutableMapNeko = // this
        LazyMutableMapNeko(params.copy(), type)


    /**
     * 转化为不可变类型[Neko]
     */
    override fun toImmutable(): LazyMapNeko = // this
        LazyMapNeko(params.copy(), type)


    /** toString */
    override fun toString(): String =
        CatCodeUtil.toCat(type, encode = true, map = this)


    /**
     * params map.
     */
    override fun toMap(): MutableMap<String, String> = params.toMutableMap()


    override fun switchCodeType(codeType: String): LazyMapNeko {
        if (codeType == this.codeType) return this
        return LazyMutableMapNoraNeko(codeType, params, type)
    }

}
