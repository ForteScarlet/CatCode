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
@file:JvmName("Cats")
package catcode

import kotlin.contracts.contract
import catcode.WildcatCodeUtil.Companion.getInstance as wildcatCodeUtilGetInstanceFunc

/*
& -> &amp;
[ -> &#91;
] -> &#93;
 */
/*
& -> &amp;
[ -> &#91;
] -> &#93;
, -> &#44;
 */

internal val CAT_KV_SPLIT_ARRAY: Array<String> = arrayOf(CAT_KV)
internal val CAT_PS_SPLIT_ARRAY: Array<String> = arrayOf(CAT_PS)

/** Cat Decoder */
@Suppress("MemberVisibilityCanBePrivate")
object CatDecoder {

    @JvmStatic
    val instance
        get() = this

    /** 非猫猫码文本消息解义 */
    fun decodeText(str: String): String =
        str .replace("&#91;", "[")
            .replace("&#93;", "]")
            .replace("&#09;", "\t")
            .replace("&#10;", "\r")
            .replace("&#13;", "\n")
            .replace("&amp;", "&")

    /** 非猫猫码文本消息解义，如果[str]为null则返回null */
    fun decodeTextOrNull(str: String?) : String? = str?.let { decodeText(it) }


    /** 猫猫码参数值消息解义 */
    fun decodeParams(str: String): String =
        str .replace("&#91;", "[")
            .replace("&#93;", "]")
            .replace("&#44;", ",")
            .replace("&#61;", "=")
            .replace("&#09;", "\t")
            .replace("&#10;", "\r")
            .replace("&#13;", "\n")
            .replace("&amp;", "&")

    /** 猫猫码参数值消息解义，如果[str]为null则返回null */
    fun decodeParamsOrNull(str: String?): String? = str?.let { decodeParams(it) }

}


public fun String.deCatParam(): String = CatDecoder.decodeParams(this)
public fun String.deCatText(): String = CatDecoder.decodeText(this)


/** Cat Encoder */
@Suppress("MemberVisibilityCanBePrivate")
object CatEncoder {

    @JvmStatic
    val instance
        get() = this

    /** 非猫猫码文本消息转义 */
    fun encodeText(str: String): String =
        str.replace("&", "&amp;")
            .replace("[", "&#91;")
            .replace("]", "&#93;")
            .replace("\t", "&#09;")
            .replace("\r", "&#10;")
            .replace("\n", "&#13;")

    /** 非猫猫码文本消息转义。如果[str]为null则返回null */
    fun encodeTextOrNull(str: String?): String? = str?.let { encodeText(it) }

    /** 猫猫码参数值消息转义 */
    fun encodeParams(str: String): String =
        str.replace("&", "&amp;")
            .replace("[", "&#91;")
            .replace("]", "&#93;")
            .replace("=", "&#61;")
            .replace(",", "&#44;")
            .replace("\t", "&#09;")
            .replace("\r", "&#10;")
            .replace("\n", "&#13;")

    /** 猫猫码参数值消息转义。如果[str]为null则返回null */
    fun encodeParamsOrNull(str: String?): String? = str?.let { encodeParams(it) }

}

public fun String.enCatParam(): String = CatEncoder.encodeParams(this)
public fun String.enCatText(): String = CatEncoder.encodeText(this)





/**
 * 转变一个[Neko]的[codeType][Neko.codeType]。
 *
 * 例如：
 * ```
 * [CAT:at,code=123] -> [CQ:at,code=123]
 * ```
 *
 * 即
 * ```
 * WildCatCodeUtil.getInstance("CQ")
 * neko.switchCodeType("CQ", /* ... */)
 * ```
 *
 *
 *
 * 如果方法的目标[Neko]不是一个[CodeTypeSwitchAble]实例，
 * 那么会通过 [aibo] 参数得到一个对应的构建工具类，并通过此工具复制一个Neko对象。
 *
 * 其中，[aibo] 中可传入的参数最好不是随时随地实例化的参数，因为他的实例是完全可控的。
 */
@JvmOverloads
@org.jetbrains.annotations.Contract(pure = true)
public fun Neko.switchCodeType(codeType: String, aibo: (codeType: String) -> NekoAibo = ::wildcatCodeUtilGetInstanceFunc ): Neko {

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
 * 猫猫码的操作工具类
 */
public object CatCodeUtil : NekoAibo("CAT") {
    @JvmStatic
    val instance
        get() = this

    override val catCodeHead: String = CAT_HEAD
    /**
     *  获取一个String为载体的[模板][CodeTemplate]
     *  @see StringTemplate
     */
    override val stringTemplate: CodeTemplate<String> get() = StringTemplate

    /**
     *  获取[Neko]为载体的[模板][CodeTemplate]
     *  @see NekoTemplate
     */
    override val nekoTemplate: CodeTemplate<Neko> get() = NekoTemplate

    /**
     * 构建一个String为载体类型的[构建器][CodeBuilder]
     */
    override fun getStringCodeBuilder(type: String, encode: Boolean): CodeBuilder<String> = StringCodeBuilder(codeType, type, encode)

    /**
     * 构建一个[Neko]为载体类型的[构建器][CodeBuilder]
     */
    override fun getNekoBuilder(type: String, encode: Boolean): CodeBuilder<Neko> = NekoBuilder(codeType, type)

    /**
     * 构建一个[Neko]为载体类型的[懒加载构建器][LazyCodeBuilder]
     */
    override fun getLazyNekoBuilder(type: String, encode: Boolean): LazyCodeBuilder<Neko> = LazyNekoBuilder(codeType, type)

}







