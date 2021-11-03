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

import kotlin.jvm.JvmName

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
public object CatDecoder {

    /** 非猫猫码文本消息解义 */
    public fun decodeText(str: String): String =
        str .replace("&#91;", "[")
            .replace("&#93;", "]")
            .replace("&#09;", "\t")
            .replace("&#10;", "\r")
            .replace("&#13;", "\n")
            .replace("&amp;", "&")

    /** 非猫猫码文本消息解义，如果[str]为null则返回null */
    public fun decodeTextOrNull(str: String?) : String? = str?.let { decodeText(it) }


    /** 猫猫码参数值消息解义 */
    public fun decodeParams(str: String): String =
        str .replace("&#91;", "[")
            .replace("&#93;", "]")
            .replace("&#44;", ",")
            .replace("&#61;", "=")
            .replace("&#09;", "\t")
            .replace("&#10;", "\r")
            .replace("&#13;", "\n")
            .replace("&amp;", "&")

    /** 猫猫码参数值消息解义，如果[str]为null则返回null */
    public fun decodeParamsOrNull(str: String?): String? = str?.let { decodeParams(it) }

}


public fun String.deCatParam(): String = CatDecoder.decodeParams(this)
public fun String.deCatText(): String = CatDecoder.decodeText(this)


/** Cat Encoder */
@Suppress("MemberVisibilityCanBePrivate")
public object CatEncoder {

    /** 非猫猫码文本消息转义 */
    public fun encodeText(str: String): String =
        str.replace("&", "&amp;")
            .replace("[", "&#91;")
            .replace("]", "&#93;")
            .replace("\t", "&#09;")
            .replace("\r", "&#10;")
            .replace("\n", "&#13;")

    /** 非猫猫码文本消息转义。如果[str]为null则返回null */
    public fun encodeTextOrNull(str: String?): String? = str?.let { encodeText(it) }

    /** 猫猫码参数值消息转义 */
    public fun encodeParams(str: String): String =
        str.replace("&", "&amp;")
            .replace("[", "&#91;")
            .replace("]", "&#93;")
            .replace("=", "&#61;")
            .replace(",", "&#44;")
            .replace("\t", "&#09;")
            .replace("\r", "&#10;")
            .replace("\n", "&#13;")

    /** 猫猫码参数值消息转义。如果[str]为null则返回null */
    public fun encodeParamsOrNull(str: String?): String? = str?.let { encodeParams(it) }

}

public fun String.enCatParam(): String = CatEncoder.encodeParams(this)
public fun String.enCatText(): String = CatEncoder.encodeText(this)


/**
 * 猫猫码的操作工具类
 */
public object CatCodeUtil : NekoAibo("CAT") {

    override val catCodeHead: String = CAT_HEAD
    /**
     *  获取一个String为载体的[模板][CodeTemplate]
     *  @see StringTemplate
     */
    override val stringTemplate: CodeTemplate<String> get() = StringTemplate

    /**
     *  获取[Cat]为载体的[模板][CodeTemplate]
     *  @see NekoTemplate
     */
    override val catTemplate: CodeTemplate<Cat> get() = NekoTemplate

    /**
     * 构建一个String为载体类型的[构建器][CodeBuilder]
     */
    override fun getStringCodeBuilder(type: String, encode: Boolean): CodeBuilder<String> = StringCodeBuilder(type, encode)

    /**
     * 构建一个[Cat]为载体类型的[构建器][CodeBuilder]
     */
    override fun getNekoBuilder(type: String, encode: Boolean): CodeBuilder<Cat> = NekoBuilder(type)

    /**
     * 构建一个[Cat]为载体类型的[懒加载构建器][LazyCodeBuilder]
     */
    override fun getLazyNekoBuilder(type: String, encode: Boolean): LazyCodeBuilder<Cat> = LazyNekoBuilder(type)

}






