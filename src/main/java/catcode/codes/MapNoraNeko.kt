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
open class MapNoraNeko
protected constructor(override val codeType: String, protected open val params: Map<String, String>, override var type: String) :
    NoraNeko,
    Map<String, String> by params {
    constructor(codeType: String, type: String) : this(codeType, emptyMap(), type)
    constructor(codeType: String, type: String, params: Map<String, String>) : this(codeType, params.toMap(), type)
    constructor(codeType: String, type: String, vararg params: CatKV<String, String>) : this(codeType, mapOf(*params.toPair()), type)
    constructor(codeType: String, type: String, vararg params: String) : this(codeType, mapOf(*params.map {
        val split = it.split(delimiters = CAT_KV_SPLIT_ARRAY, false, 2)
        split[0] to split[1]
    }.toTypedArray()), type)

    // /** internal constructor for mutable kqCode */
    // constructor(mutableKQCode: MutableNeko) : this(mutableKQCode.toMap(), mutableKQCode.type)

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
    private val _toString: String by lazy { CatCodeUtil.toCat(type, map = this) }

    /** toString */
    override fun toString(): String = _toString


    /**
     * 转化为参数可变的[MutableNeko]
     */
    override fun mutable(): MutableNeko = MutableMapNoraNeko(codeType, type, this.toMutableMap())

    /**
     * 转化为不可变类型[Neko]
     */
    override fun immutable(): Neko = MapNoraNeko(codeType, params, type)

    /**
     * 获取 [params] 实例。
     */
    override fun toMap(): Map<String, String> = params


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MapNoraNeko

        if (params != other.params) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result: Int = params.hashCode()
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
        private inline fun splitCode(code: String): CatKV<String, List<String>> {
            var tempText = code.trim()
            // 不是[CAT:开头，或者不是]结尾都不行
            if (!tempText.startsWith(CAT_HEAD) || !tempText.endsWith(CAT_END)) {
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
        fun byCode(codeType: String, code: String, decode: Boolean = true): MapNoraNeko {
            val (type, split) = splitCode(code)

            return if (split.size > 1) {
                if (decode) {
                    // 参数解码
                    val map = split.subList(1, split.size).map {
                        val sp = it.split(Regex("="), 2)
                        sp[0] to CatDecoder.decodeParams(sp[1])
                    }.toMap()
                    MapNoraNeko(codeType, map, type)
                } else {
                    MapNoraNeko(codeType, type, *split.subList(1, split.size).toTypedArray())
                }
            } else {
                MapNoraNeko(codeType, type)
            }
        }

        /** 通过map参数获取 */
        @JvmStatic
        fun byMap(codeType: String, type: String, params: Map<String, String>): MapNoraNeko =
            MapNoraNeko(codeType, type, params)

        /** 通过键值对获取 */
        @JvmStatic
        fun byKV(codeType: String, type: String, vararg params: CatKV<String, String>): MapNoraNeko =
            MapNoraNeko(codeType, type, *params)

        /** 通过键值对字符串获取 */
        @JvmStatic
        fun byParamString(codeType: String, type: String, vararg params: String): MapNoraNeko =
            MapNoraNeko(codeType, type, *params)

        /**
         * 根据猫猫码字符串获取[MapNeko]实例
         */
        @JvmStatic
        @JvmOverloads
        fun mutableByCode(codeType: String, code: String, decode: Boolean = true): MutableMapNoraNeko {
            val (type, split) = splitCode(code)

            return if (split.size > 1) {
                if (decode) {
                    // 参数解码
                    val map: MutableMap<String, String> = split.subList(1, split.size).map {
                        val sp = it.split(Regex("="), 2)
                        sp[0] to CatDecoder.decodeParams(sp[1])
                    }.toMap().toMutableMap()
                    MutableMapNoraNeko(codeType, type, map)
                } else {
                    MutableMapNoraNeko(codeType, type, *split.subList(1, split.size).toTypedArray())
                }
            } else {
                MutableMapNoraNeko(codeType, type)
            }
        }


        /** 通过map参数获取 */
        @JvmStatic
        fun mutableByMap(codeType: String, type: String, params: Map<String, String>): MutableMapNoraNeko =
            MutableMapNoraNeko(codeType, type, params)

        /** 通过键值对获取 */
        @JvmStatic
        fun mutableByKV(codeType: String, type: String, vararg params: CatKV<String, String>): MutableMapNoraNeko =
            MutableMapNoraNeko(codeType, type, *params)

        /** 通过键值对字符串获取 */
        @JvmStatic
        fun mutableByParamString(codeType: String, type: String, vararg params: String): MutableMapNoraNeko =
            MutableMapNoraNeko(codeType, type, *params)
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
public class MutableMapNoraNeko
private constructor(codeType: String, protected override val params: MutableMap<String, String>, type: String) :
    MapNoraNeko(codeType, params, type),
    MutableNeko,
    MutableMap<String, String> by params {
    constructor(codeType: String, type: String) : this(codeType, mutableMapOf(), type)
    constructor(codeType: String, type: String, params: Map<String, String>) : this(codeType, params.toMutableMap(), type)
    constructor(codeType: String, type: String, vararg params: CatKV<String, String>) : this(codeType, mutableMapOf(*params.toPair()), type)
    constructor(codeType: String, type: String, vararg params: String) : this(codeType, mutableMapOf(*params.map {
        val split = it.split(delimiters = CAT_KV_SPLIT_ARRAY, false, 2)
        split[0] to split[1]
    }.toTypedArray()), type)

    /**
     * 转化为参数可变的[MutableNeko]
     */
    override fun mutable(): MutableNeko = MutableMapNoraNeko(codeType, params, type)


    /**
     * 转化为不可变类型[Neko]
     */
    override fun immutable(): Neko = MapNoraNeko(codeType, type, this)

    /** toString */
    override fun toString(): String = CatCodeUtil.toCat(type, map = this)


    /**
     * params map.
     */
    override fun toMap(): MutableMap<String, String> = params


}
