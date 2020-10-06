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

package love.forte.catcode

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
    override fun getStringCodeBuilder(type: String): CodeBuilder<String> = StringCodeBuilder(type)

    /**
     * 构建一个[Neko]为载体类型的[构建器][CodeBuilder]
     */
    override fun getNekoBuilder(type: String): CodeBuilder<Neko> = NekoBuilder(type)

}


//
// /**
//  * 猫猫码的操作工具类
//  */
// object CatCodeUtil {
//
//
//     /**
//      *  获取一个String为载体的[模板][CodeTemplate]
//      *  @see StringTemplate
//      */
//     val stringTemplate: CodeTemplate<String> get() = StringTemplate
//
//     /**
//      *  获取[Neko]为载体的[模板][CodeTemplate]
//      *  @see NekoTemplate
//      */
//     val nekoTemplate: CodeTemplate<Neko> get() = NekoTemplate
//
//     /**
//      * 构建一个String为载体类型的[构建器][CodeBuilder]
//      */
//     fun getStringCodeBuilder(type: String): CodeBuilder<String> = CodeBuilder.stringCodeBuilder(type)
//
//
//     /**
//      * 构建一个[Neko]为载体类型的[构建器][CodeBuilder]
//      */
//     fun getNekoBuilder(type: String): CodeBuilder<Neko> = CodeBuilder.nekoBuilder(type)
//
//
//     @JvmStatic
//     val instance
//         get() = this
//
//     /**
//      * 仅通过一个类型获取一个猫猫码。例如`\[Cat:hi]`
//      */
//     fun toCat(type: String): String {
//         return "$CAT_HEAD$type$CAT_END"
//     }
//
//     /**
//      * 将参数转化为猫猫码字符串.
//      * 如果[encode] == true, 则会对[pair]的值进行[转义][CatEncoder.encodeParams]
//      *
//      * @since 1.0-1.11
//      */
//     @JvmOverloads
//     fun toCat(type: String, encode: Boolean = true, vararg pair: Pair<String, Any>): String {
//         val pre = "$CAT_HEAD$type"
//         return if (pair.isNotEmpty()) {
//             pair.joinToString(CAT_PV, "$pre$CAT_PV", CAT_END) {
//                 "${it.first}$CAT_KV${
//                     if (encode) CatEncoder.encodeParams(it.second.toString()) else it.second
//                 }"
//             }
//         } else {
//             pre + CAT_END
//         }
//     }
//
//     /**
//      * 将参数转化为猫猫码字符串
//      * @since 1.0-1.11
//      */
//     @JvmOverloads
//     fun toCat(type: String, encode: Boolean = true, map: Map<String, *>): String {
//         val pre = "$CAT_HEAD$type"
//         return if (map.isNotEmpty()) {
//             map.entries.joinToString(
//                 CAT_PV,
//                 "$pre$CAT_PV",
//                 CAT_END
//             ) {
//                 "${it.key}$CAT_KV${
//                     if (encode) CatEncoder.encodeParams(it.value.toString()) else it.value
//                 }"
//             }
//         } else {
//             pre + CAT_END
//         }
//     }
//
//     /**
//      * 将参数转化为猫猫码字符串, [params]的格式应当是`xxx=xxx`
//      * 如果[encode] == true, 则说明需要对`=`后的值进行转义。
//      * 如果[encode] == false, 则不会对参数值进行转义，直接拼接为Cat字符串
//      * @since 1.8.0
//      */
//     @JvmOverloads
//     fun toCat(type: String, encode: Boolean = true, vararg params: String): String {
//         // 如果参数为空
//         return if (params.isNotEmpty()) {
//             if (encode) {
//                 toCat(type, encode, *params.map {
//                     val split: List<String> = it.split(delimiters = CAT_KV_SPLIT_ARRAY, false, 2)
//                     split[0] to split[1]
//                 }.toTypedArray())
//             } else {
//                 // 不需要转义, 直接进行字符串拼接
//                 "$CAT_HEAD$type$CAT_PV${params.joinToString(CAT_PV)}$CAT_END"
//             }
//         } else {
//             "$CAT_HEAD$type$CAT_END"
//         }
//     }
//
//     /**
//      * 获取无参数的[Neko]
//      * @param type 猫猫码的类型
//      */
//     fun toNeko(type: String): Neko = EmptyNeko(type)
//
//     /**
//      * 根据[Map]类型参数转化为[Neko]实例
//      *
//      * @param type 猫猫码的类型
//      * @param params 参数列表
//      */
//     fun toNeko(type: String, params: Map<String, *>): Neko {
//         return if (params.isEmpty()) {
//             EmptyNeko(type)
//         } else {
//             MapNeko(type, params.asSequence().map { it.key to it.value.toString() }.toMap())
//         }
//     }
//
//
//     /**
//      * 根据参数转化为[Neko]实例
//      * @param type 猫猫码的类型
//      * @param params 参数列表
//      */
//     fun toNeko(type: String, vararg params: Pair<String, *>): Neko {
//         return if (params.isEmpty()) {
//             EmptyNeko(type)
//         } else {
//             MapNeko(type, params.asSequence().map { it.first to it.second.toString() }.toMap())
//         }
//     }
//
//
//     /**
//      * 根据参数转化为[Neko]实例
//      * @param type 猫猫码的类型
//      * @param paramText 参数列表, 例如："qq=123"
//      */
//     @JvmOverloads
//     fun toNeko(type: String, encode: Boolean = false, vararg paramText: String): Neko {
//         return if (paramText.isEmpty()) {
//             EmptyNeko(type)
//         } else {
//             if (encode) {
//                 Nyanko.byCode(toCat(type, encode, *paramText))
//             } else {
//                 MapNeko.byParamString(type, *paramText)
//             }
//         }
//     }
//
//     /**
//      * 将一段字符串根据字符串与猫猫码来进行切割。
//      * 不会有任何转义操作。
//      * @since 1.1-1.11
//      */
//     fun split(text: String): List<String> = split(text) { this }
//
//     /**
//      * 将一段字符串根据字符串与猫猫码来进行切割,
//      * 并可以通过[postMap]对切割后的每条字符串进行后置处理。
//      *
//      * 不会有任何转义操作。
//      *
//      * @param text 文本字符串
//      * @param postMap 后置转化函数
//      * @since 1.8.0
//      */
//     inline fun <T> split(text: String, postMap: String.() -> T): List<T> {
//         // 准备list
//         val list: MutableList<T> = mutableListOf()
//
//         val het = CAT_HEAD
//         val ent = CAT_END
//
//         // 查找最近一个[Cat:字符
//         var h = text.indexOf(het)
//         var le = -1
//         var e = -1
//         while (h >= 0) {
//             // 从头部开始查询尾部
//             if (e != -1) {
//                 le = e
//             }
//             e = text.indexOf(ent, h)
//             h = if (e < 0) {
//                 // 没找到，查找下一个[Cat:
//                 text.indexOf(het, h + 1)
//             } else {
//                 // 找到了，截取。
//                 // 首先截取前一段
//                 if (h > 0 && (le + 1) != h) {
//                     list.add(text.substring(le + 1, h).postMap())
//                 }
//                 // 截取猫猫码
//                 list.add(text.substring(h, e + 1).postMap())
//                 // 重新查询
//                 text.indexOf(het, e)
//             }
//         }
//         if (list.isEmpty()) {
//             list.add(text.postMap())
//         }
//         if (e != text.length - 1) {
//             if (e >= 0) {
//                 list.add(text.substring(e + 1, text.length).postMap())
//             }
//         }
//         return list
//     }
//
//     /**
//      * 从消息字符串中提取出猫猫码字符串
//      * @param text 消息字符串
//      * @param index 第几个索引位的猫猫码，默认为0，即第一个
//      * @since 1.1-1.11
//      */
//     @JvmOverloads
//     fun getCat(text: String, type: String = "", index: Int = 0): String? {
//         if (index < 0) {
//             throw IndexOutOfBoundsException("$index")
//         }
//
//         var i = -1
//         var ti: Int
//         var e = 0
//         val het = CAT_HEAD + type
//         val ent = CAT_END
//
//         do {
//             ti = text.indexOf(het, e)
//             if (ti >= 0) {
//                 e = text.indexOf(ent, ti)
//                 if (e >= 0) {
//                     i++
//                 } else {
//                     e = ti + 1
//                 }
//             }
//         } while (ti >= 0 && i < index)
//         return if (i == index) {
//             text.substring(ti, e + 1)
//         } else {
//             null
//         }
//     }
//
//     /**
//      * 从消息字符串中提取出猫猫码字符串
//      * @param text 消息字符串
//      * @param index 第几个索引位的猫猫码，默认为0，即第一个
//      * @since 1.1-1.11
//      */
// //    @JvmOverloads
//     fun getCat(text: String, index: Int = 0): String? = getCat(text = text, type = "", index = index)
//
//
//     /**
//      * 提取字符串中的全部猫猫码字符串
//      * @since 1.1-1.11
//      */
//     @JvmOverloads
//     fun getCats(text: String, type: String = ""): List<String> = getCats(text, type) { it }
//
//     /**
//      * 提取字符串中的全部猫猫码字符串
//      * @since 1.8.0
//      */
//     @JvmOverloads
//     inline fun <T> getCats(text: String, type: String = "", map: (String) -> T): List<T> {
//         var ti: Int
//         var e = 0
//         val het = CAT_HEAD + type
//         val ent = CAT_END
//         // temp list
//         val list: MutableList<T> = mutableListOf()
//
//         do {
//             ti = text.indexOf(het, e)
//             if (ti >= 0) {
//                 e = text.indexOf(ent, ti)
//                 if (e >= 0) {
//                     list.add(map(text.substring(ti, e + 1)))
//                 } else {
//                     e = ti + 1
//                 }
//             }
//         } while (ti >= 0 && e >= 0)
//
//         return list
//     }
//
//     /**
//      * 获取文本中的猫猫码的参数。
//      * 如果文本为null、找不到对应索引的猫猫码、找不到此key，返回null；如果找到了key但是无参数，返回空字符串
//      *
//      * 默认情况下获取第一个猫猫码的参数
//      * @since 1.1-1.11
//      */
//     fun getParam(text: String, paramKey: String, index: Int = 0): String? =
//         getParam(text = text, paramKey = paramKey, type = "", index = index)
//
//     /**
//      * 获取文本中的猫猫码的参数。
//      * 如果文本为null、找不到对应索引的猫猫码、找不到此key，返回null；如果找到了key但是无参数，返回空字符串
//      *
//      * 默认情况下获取第一个猫猫码的参数
//      * @since 1.1-1.11
//      */
//     @JvmOverloads
//     fun getParam(text: String, paramKey: String, type: String = "", index: Int = 0): String? {
//         val catHead = CAT_HEAD + type
//         val catEnd = CAT_END
//         val catSpl = CAT_PV
//
//         var from = -1
//         var end = -1
//         var i = -1
//         do {
//             from = text.indexOf(catHead, from + 1)
//             if (from >= 0) {
//                 // 寻找结尾
//                 end = text.indexOf(catEnd, from)
//                 if (end >= 0) {
//                     i++
//                 }
//             }
//         } while (from >= 0 && i < index)
//
//         // 索引对上了
//         if (i == index) {
//             // 从from开始找参数
//             val paramFind = ",$paramKey="
//             val phi = text.indexOf(paramFind, from)
//             if (phi < 0) {
//                 return null
//             }
//             // 找到了之后，找下一个逗号，如果没有，就用最终结尾的位置
//             val startIndex = phi + paramFind.length
//             var pei = text.indexOf(catSpl, startIndex)
//             // 超出去了
//             if (pei < 0 || pei > end) {
//                 pei = end
//             }
//             if (startIndex > text.lastIndex || startIndex > pei) {
//                 return null
//             }
//             return text.substring(startIndex, pei)
//         } else {
//             return null
//         }
//     }
//
//     /**
//      * 获取文本字符串中猫猫码字符串的迭代器
//      * @since 1.1-1.11
//      * @param text 存在猫猫码正文的文本
//      * @param type 要获取的猫猫码的类型，如果为空字符串则视为所有，默认为所有。
//      */
//     @JvmOverloads
//     fun getCatIter(text: String, type: String = ""): Iterator<String> = CatTextIterator(text, type)
//
//
//     /**
//      * 为一个猫猫码字符串得到他的key迭代器
//      * @param code 猫猫码字符串
//      * @since 1.8.0
//      */
//     fun getCatKeyIter(code: String): Iterator<String> = CatParamKeyIterator(code)
//
//     /**
//      * 为一个猫猫码字符串得到他的value迭代器
//      * @param code 猫猫码字符串
//      * @since 1.8.0
//      */
//     fun getCatValueIter(code: String): Iterator<String> = CatParamValueIterator(code)
//
//
//     /**
//      * 为一个猫猫码字符串得到他的key-value的键值对迭代器
//      * @param code 猫猫码字符串
//      * @since 1.8.0
//      */
//     fun getCatPairIter(code: String): Iterator<Pair<String, String>> = CatParamPairIterator(code)
//
//
//     /**
//      * @see getCats
//      */
//     @Suppress("DEPRECATION")
//     @Deprecated("param 'decode' not required.")
//     fun getNekoList(text: String, type: String, decode: Boolean): List<Neko> {
//         val iter = getCatIter(text, type)
//         val list = mutableListOf<Neko>()
//         iter.forEach { list.add(Neko.of(it, decode)) }
//         return list
//     }
//
//     /**
//      * 以[getCatIter]方法为基础获取字符串中全部的[Neko]对象
//      * @since 1.1-1.11
//      * @param text 存在猫猫码正文的文本
//      * @param type 要获取的猫猫码的类型，如果为空字符串则视为所有，默认为所有。
//      */
//     @JvmOverloads
//     fun getNekoList(text: String, type: String = ""): List<Neko> {
//         val iter: Iterator<String> = getCatIter(text, type)
//         val list: MutableList<Neko> = mutableListOf()
//         iter.forEach { list.add(Neko.of(it)) }
//         return list
//     }
//
//
//     /**
//      * 提取出文本中的猫猫码，并封装为[Neko]实例。
//      * @see getNeko
//      */
//     @Suppress("DEPRECATION")
//     @Deprecated("param 'decode' not required.")
//     fun getNeko(text: String, type: String = "", index: Int = 0, decode: Boolean = true): Neko? {
//         val cat = getCat(text, type, index) ?: return null
//         return Neko.of(cat, decode)
//     }
//
//     /**
//      * 提取出文本中的猫猫码，并封装为[Neko]实例。
//      * @param text 存在猫猫码的正文
//      * @param type 要获取的猫猫码的类型，默认为所有类型
//      * @param index 获取的索引位的猫猫码，默认为0，即第一个
//      */
//     @JvmOverloads
//     fun getNeko(text: String, type: String = "", index: Int = 0): Neko? {
//         val cat: String = getCat(text, type, index) ?: return null
//         return Neko.of(cat)
//     }
//
//     /**
//      * 获取指定索引位的猫猫码，并封装为[Neko]实例。
//      */
//     @Suppress("MemberVisibilityCanBePrivate")
//     fun getNeko(text: String, index: Int = 0): Neko? = getNeko(text = text, type = "", index = index)
//
//     /**
//      * 移除猫猫码，可指定类型
//      * 具体使用参考[remove] 和 [removeByType]
//      * @since 1.2-1.12
//      */
//     private fun removeCode(
//         type: String,
//         text: String,
//         trim: Boolean = true,
//         ignoreEmpty: Boolean = true,
//         delimiter: CharSequence = ""
//     ): String {
//         when {
//             text.isEmpty() -> {
//                 return text
//             }
//             else -> {
//                 val sb = StringBuilder(text.length)
//                 // 移除所有的猫猫码
//                 val head = CAT_HEAD + type
//                 val end = CAT_END
//
//                 var hi: Int = -1
//                 var ei = -1
//                 var nextHi: Int
//                 var sps = 0
//                 var sub: String
//                 var next: Char
//
//                 if (text.length < head.length + end.length) {
//                     return text
//                 }
//
//                 if (!text.contains(head)) {
//                     return text
//                 }
//
//                 do {
//                     hi++
//                     hi = text.indexOf(head, hi)
//                     next = text[hi + head.length]
//                     // 如果text存在内容，则判断：下一个不是逗号或者结尾
//                     if (type.isNotEmpty() && (next != ',' && next.toString() != end)) {
//                         continue
//                     }
//                     if (hi >= 0) {
//                         // 有一个头
//                         // 寻找下一个尾
//                         ei = text.indexOf(end, hi)
//                         if (ei > 0) {
//                             // 有一个尾，看看下一个头是不是在下一个尾之后
//                             nextHi = text.indexOf(head, hi + 1)
//                             // 如果中间包着一个头，则这个头作为当前头
//                             if (nextHi in 0 until ei) {
//                                 hi = nextHi
//                             }
//                             if (hi > 0) {
//                                 if (sps > 0) {
//                                     sps++
//                                 }
//                                 sub = text.substring(sps, hi)
//                                 if (!ignoreEmpty || (ignoreEmpty && sub.isNotBlank())) {
//                                     if (trim) {
//                                         sub = sub.trim()
//                                     }
//                                     if (sb.isNotEmpty()) {
//                                         sb.append(delimiter)
//                                     }
//                                     sb.append(sub)
//                                 }
//                                 sps = ei
//                             } else if (hi == 0) {
//                                 sps = ei
//
//                             }
//                         }
//                     }
//                 } while (hi >= 0 && ei > 0)
//
//                 // 没有头了
//                 if (sps != text.lastIndex) {
//                     sub = text.substring(sps + 1)
//                     if (!ignoreEmpty || (ignoreEmpty && sub.isNotBlank())) {
//                         if (trim) {
//                             sub = sub.trim()
//                         }
//                         if (sb.isNotEmpty()) {
//                             sb.append(delimiter)
//                         }
//                         sb.append(sub)
//                     }
//                 }
//                 return sb.toString()
//             }
//         }
//     }
//
//     /**
//      * 移除字符串中的所有的猫猫码，返回字符串。
//      * 必须是完整的\[Cat:...]。
//      * @param text 文本正文
//      * @param trim 是否对文本执行trim，默认为true
//      * @param ignoreEmpty 如果字符为纯空白字符，是否忽略
//      * @param delimiter 切割字符串
//      */
//     @JvmOverloads
//     fun remove(
//         text: String,
//         trim: Boolean = true,
//         ignoreEmpty: Boolean = true,
//         delimiter: CharSequence = ""
//     ): String {
//         return removeCode("", text, trim, ignoreEmpty, delimiter)
//     }
//
//     /**
//      * 移除某个类型的字符串中的所有的猫猫码，返回字符串。
//      * 必须是完整的\[Cat...]。
//      * @param type 猫猫码的类型
//      * @param text 文本正文
//      * @param trim 是否对文本执行trim，默认为true
//      * @param ignoreEmpty 如果字符为纯空白字符，是否忽略
//      * @param delimiter 切割字符串
//      */
//     @JvmOverloads
//     fun removeByType(
//         type: String,
//         text: String,
//         trim: Boolean = true,
//         ignoreEmpty: Boolean = true,
//         delimiter: CharSequence = ""
//     ): String {
//         return removeCode(type, text, trim, ignoreEmpty, delimiter)
//     }
//
//
// }






