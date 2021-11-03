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

import catcode.collection.MutableCatMap
import catcode.collection.CatMap
import kotlin.jvm.JvmName


public const val CAT_TYPE: String = "CAT"
public const val CAT_HEAD: String = "[$CAT_TYPE:"
public const val CAT_END: String = "]"

/** cat params split. */
public const val CAT_PS: String = ","

/** cat key-value. */
public const val CAT_KV: String = "="

/*
    一只可爱的小猫咪

    A cute kitten
    A cute kitty

    Kitten!
 */


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
 * - 不应出现空格。
 * - 不应出现换行。
 *
 */
public val nekoMatchRegex: Regex = Regex("\\[(\\w+:\\w+(,((?![\\[\\]]).)+?)*)]")


/**
 * 获取一个 code head。
 * 建议大写。
 */
public fun catHead(codeType: String): String = "[$codeType:"


/**
 * 一个不可变的 CatCode 标准接口
 *
 *
 */
public interface Cat : CatMap<String, String>, CharSequence {

    /**
     * 猫猫码的头部标识。即 `[CAT:xx]` 中的这个 `CAT`。
     */
    public val catType: String

    /**
     * 获取Code的类型。例如`at`
     */
    public val type: String

    /**
     * 获取猫猫码中的指定参数。
     */
    override fun get(key: String): String?

    /**
     * 获取转义前的值。一般普通的[get]方法得到的是反转义后的。
     * 此处为保留原本的值不做转义。
     */
    public fun getWithoutDecode(key: String): String?

    /**
     * 转化为可变参的[MutableCat]。应当返回一个新的实例。
     */
    public fun mutable(): MutableCat

    /**
     * 转化为不可变类型[Cat]。应当返回一个新的实例。
     */
    public fun immutable(): Cat


}

/**
 * 定义一个可变的[Cat]标准接口。
 * - `MutableNeko`实例应当实现[MutableMap]接口，使其可以作为一个 **可变** Map使用。
 */
public interface MutableCat : Cat, MutableCatMap<String, String> {
    override var catType: String
    override var type: String
}

/**
 * 定义一个任意类型的[Cat]实例。
 *
 * > nora neko -> のらねこ -> 野良猫 -> 野猫 , 即不是标准意义的cat code。
 *
 * 例如，`[CAT:at,code=123]`即为标准cat code,
 * 而`[CQ:at,code=123]` 则不是标注cat code, 但是除了code类型以外的规则全部一样。
 *
 * [NoraCat] 接口继承自 [Cat] 接口, 并提供一个 [catType] 属性以指定code类型。
 *
 */
public interface NoraCat : Cat {
    override val catType: String
}


/**
 * 一个纯空参的[Cat]实例。
 *
 * 此类只有**不可变**状态, 并且应当为无参[Cat]的优先使用类。由于没有参数，因此不存在任何多余的计算与转义。
 *
 * 由于不存在对应的**可变状态**,
 * 因此[mutable]所得到的实例为[catcode.codes.MutableMapNeko]实例。
 *
 */
public data class EmptyCat(override val catType: String, override val type: String) : Cat {

    private val codeText = "${catHead(catType)}$type$CAT_END"

    override fun toString(): String = codeText

    /**
     * 转化为可变参的[MutableCat]
     */
    override fun mutable(): MutableCat = TODO() // MapNeko.mutableByCode(codeText)

    /**
     * 转化为不可变类型[Cat]
     */
    override fun immutable(): Cat = copy()

    override fun toMap(): Map<String, String> = emptyMap()
    override val entries: Set<Map.Entry<String, String>> = emptySet()
    override val keys: Set<String> = emptySet()
    override val size: Int = 0
    override val values: Collection<String> = emptyList()
    override fun containsKey(key: String): Boolean = false
    override fun containsValue(value: String): Boolean = false
    override operator fun get(key: String): String? = null
    override fun getWithoutDecode(key: String): String? = null
    override val length: Int = codeText.length
    override operator fun get(index: Int): Char = codeText[index]
    override fun isEmpty(): Boolean = true
    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = codeText.subSequence(startIndex, endIndex)

    public companion object {

        /**
         * Standard catcode: `[CAT:$type]`
         */
        public fun standard(type: String): EmptyCat = EmptyCat(CAT_TYPE, type)
    }
}







