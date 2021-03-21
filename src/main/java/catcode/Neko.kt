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
@file:JvmName("CatSymbolConstant")

package catcode

import catcode.codes.MapNeko
import catcode.codes.Nyanko
import catcode.collection.MutableNekoMap
import catcode.collection.NekoMap


public const val CAT_TYPE = "CAT"
public const val CAT_HEAD = "[$CAT_TYPE:"
public const val CAT_END = "]"

/** cat params split. */
public const val CAT_PS = ","

/** cat key-value. */
public const val CAT_KV = "="


/**
 * cat码匹配正则。
 *
 * 匹配为：`[codeType:type(,param=value)*]`, 其中不可能出现其他的 `[` 或 `]` 字符。
 * 例如：
 * - 正确的：`[CAT:image,file=abc.jpg]`
 * - 正确的：`[CQ:image,file=abc.jpg]`
 * - 错误的：`[CAT:image,[file=abc.jpg]`
 * - 错误的：`[CQ:image;file=abc.jpg]`
 *
 * cat码中：
 * - codeType标准应为`CAT`, 非标准则为大小写字母、数字或下划线。
 * - type标准应为大小写字母、数字或下划线。
 * - codeType与type使用 `:` 分割。
 * - 尽可能不应出现空格。
 * - 不应出现换行。
 *
 */
public val nekoMatchRegex: Regex = Regex("\\[(\\w+:\\w+(,((?![\\[\\]]).)+?)*)]")


/**
 * 获取一个[NoraNeko]的code head。
 * 建议大写。
 */
public fun catHead(codeType: String): String = "[$codeType:"

/**
 * 此接口应实现于一个 [Neko] 实现类上，表示其可以修改Cat码类型。
 *
 * 此修改最终得到一个新的实例。
 *
 */
public interface CodeTypeSwitchAble<N : Neko> {
    /**
     * 表示此 [Neko] 实例能够切换其 [Neko.codeType] 并得到一个对应的转化结果值。
     */
    @org.jetbrains.annotations.Contract(pure = true)
    fun switchCodeType(codeType: String): N
}


/**
 * 转化一个Neko的codeType。
 *
 * 如果方法的目标[Neko]不是一个[CodeTypeSwitchAble]实例，
 * 那么会通过 [aibo] 参数得到一个对应的构建工具类，并通过此工具复制一个Neko对象。
 *
 * 其中，[aibo] 中可传入的参数最好不是随时随地实例化的参数，因为他的实例是完全可控的。
 */
public fun Neko.switchCodeType(codeType: String, aibo: (codeType: String) -> NekoAibo): Neko {
    return if (this is CodeTypeSwitchAble<*>) {
        // It is, switch codeType.
        this.switchCodeType(codeType)
    } else {
        val util = aibo(codeType)

        if (this.isEmpty()) {
            // is Empty
            util.toNeko(type)
        } else {
            util.getNekoBuilder(type, true).build {
                apply {
                    this@switchCodeType.forEach { k, v ->
                        key(k) { v }
                    }
                }
            }
        }

    }
}


/**
 * 定义一个不可变的 Neko码 标准接口
 * - KQCode实例应当实现[Map]接口，使其可以作为一个**不可变**Map使用。
 * - KQCode实例应当实现[CharSequence]接口，其可以作为一个字符序列以得到猫猫码字符串
 *
 * 其参数是不可变的，如果需要一个可变参数的实例，参考方法[mutable]与其返回的接口类型[MutableNeko]
 * 如果想要获得一个纯空参的实例，参考[EmptyNeko]
 *
 * 建议子类通过私有构造+ 静态/伴生对象 方法来获取实例，例如 [MapNeko.byCode] [Nyanko.byCode]
 * 而不是直接通过构造方法。
 *
 * @since 1.8.0
 */
public interface Neko : NekoMap<String, String>, CharSequence {

    @JvmDefault
    val codeType: String
        get() = CAT_TYPE

    /**
     * 获取Code的类型。例如`at`
     */
    val type: String

    /**
     * 获取转义前的值。一般普通的[get]方法得到的是反转义后的。
     * 此处为保留原本的值不做转义。
     */
    fun getNoDecode(key: String): String?

    /**
     * 与其他字符序列拼接为[Msgs]实例
     */
    operator fun plus(other: CharSequence): Msgs = Msgs(collection = listOf(this, other))

    /**
     * 转化为可变参的[MutableNeko]。应当返回一个新的实例。
     */
    fun mutable(): MutableNeko

    /**
     * 转化为不可变类型[Neko]。应当返回一个新的实例。
     */
    fun immutable(): Neko


    companion object Of {

        /**
         * 得到一个空参的[Neko]实例。
         */
        @JvmStatic
        fun ofType(type: String): Neko = EmptyNeko(type)

        /**
         * 通过猫猫码字符串得到一个[Neko]实例
         */
        @JvmStatic
        fun of(code: String): Neko = Nyanko.byCode(code)

        /**
         * 从猫猫码字符串转到KQCode
         *
         * 1.8.0开始默认使用[Nyanko]作为静态工厂方法的[Neko]实例载体。
         * [Nyanko]是以字符串操作为基础的，因此不需要进行额外的转义。
         *
         * @since 1.1-1.11
         * @since 1.8.0
         * @param text 猫猫码字符串的正文
         * @param decode 因为这段猫猫码字符串可能已经转义过了，此处是否指定其转化的时候解码一次。默认为true
         */
        @JvmStatic
        @Deprecated("just use of(text)", ReplaceWith("FastKQCode(text)", "com.simplerobot.modules.utils.FastKQCode"))
        fun of(text: String, decode: Boolean = true): Neko {
            return Nyanko.byCode(text)
        }
    }


}

/**
 * 定义一个可变的[Neko]标准接口。
 * - `MutableNeko`实例应当实现[MutableMap]接口，使其可以作为一个 **可变** Map使用。
 */
interface MutableNeko : Neko, MutableNekoMap<String, String> {
    /**
     * type 也是可变类型
     */
    override var type: String
}

/**
 * 定义一个任意类型的[Neko]实例。
 *
 * > nora neko -> のらねこ -> 野良猫 -> 野猫 , 即不是标准意义的cat code。
 *
 * 例如，`[CAT:at,code=123]`即为标准cat code,
 * 而`[CQ:at,code=123]` 则不是标注cat code, 但是除了code类型以外的规则全部一样。
 *
 * [NoraNeko] 接口继承自 [Neko] 接口, 并提供一个 [codeType] 属性以指定code类型。
 *
 */
interface NoraNeko : Neko {
    @JvmDefault
    override val codeType: String
}


/**
 * 定义一个任意类型的[MutableNeko]实例。
 *
 * > nora neko -> のらねこ -> 野良猫 -> 野猫 , 即不是标准意义的cat code。
 *
 * 例如，`[CAT:at,code=123]`即为标准cat code,
 * 而`[CQ:at,code=123]` 则不是标注cat code, 但是除了code类型以外的规则全部一样。
 *
 * [MutableNoraNeko] 接口继承自 [MutableNeko] 接口, 并提供一个 [codeType] 属性以指定code类型。
 *
 */
interface MutableNoraNeko : MutableNeko {
    /**
     * Code type也可变
     */
    @JvmDefault
    override var codeType: String
}

abstract class BaseMutableNoraNeko : MutableNoraNeko

/**
 * 一个纯空参的[Neko]实例。
 *
 * 此类只有**不可变**状态, 并且应当为无参[Neko]的优先使用类。由于没有参数，因此不存在任何多余的计算与转义。
 *
 * 由于不存在对应的**可变状态**,
 * 因此[mutable]所得到的实例为[love.forte.catcode.codes.MutableMapNeko]实例。
 *
 */
public data class EmptyNeko(override val type: String) : Neko {

    private val codeText = "$CAT_HEAD$type$CAT_END"

    override fun toString(): String = codeText

    /**
     * 转化为可变参的[MutableNeko]
     */
    override fun mutable(): MutableNeko = MapNeko.mutableByCode(codeType, codeText)

    /**
     * 转化为不可变类型[Neko]
     */
    override fun immutable(): Neko = copy()

    override fun toMap(): Map<String, String> = emptyMap()
    override val entries: Set<Map.Entry<String, String>> = emptySet()
    override val keys: Set<String> = emptySet()
    override val size: Int = 0
    override val values: Collection<String> = emptyList()
    override fun containsKey(key: String): Boolean = false
    override fun containsValue(value: String): Boolean = false
    override operator fun get(key: String): String? = null
    override fun getNoDecode(key: String): String? = null
    override val length: Int = codeText.length
    override operator fun get(index: Int): Char = codeText[index]
    override fun isEmpty(): Boolean = true
    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = codeText.subSequence(startIndex, endIndex)
}


/**
 * 一个纯空参的[NoraNeko]实例。
 *
 * 此类只有**不可变**状态, 并且应当为无参[NoraNeko]的优先使用类。由于没有参数，因此不存在任何多余的计算与转义。
 *
 * 由于不存在对应的**可变状态**,
 * 因此[mutable]所得到的实例为[love.forte.catcode.codes.MutableMapNeko]实例。
 *
 */
public data class EmptyNoraNeko(override val codeType: String, override val type: String) : NoraNeko {

    private val codeText = "${catHead(codeType)}$type$CAT_END"

    override fun toString(): String = codeText

    /**
     * 转化为可变参的[MutableNeko]
     */
    override fun mutable(): MutableNeko = MapNeko.mutableByCode(codeType, codeText)

    /**
     * 转化为不可变类型[Neko]
     */
    override fun immutable(): Neko = copy()

    override fun toMap(): Map<String, String> = emptyMap()
    override val entries: Set<Map.Entry<String, String>> = emptySet()
    override val keys: Set<String> = emptySet()
    override val size: Int = 0
    override val values: Collection<String> = emptyList()
    override fun containsKey(key: String): Boolean = false
    override fun containsValue(value: String): Boolean = false
    override operator fun get(key: String): String? = null
    override fun getNoDecode(key: String): String? = null
    override val length: Int = codeText.length
    override operator fun get(index: Int): Char = codeText[index]
    override fun isEmpty(): Boolean = true
    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = codeText.subSequence(startIndex, endIndex)
}






