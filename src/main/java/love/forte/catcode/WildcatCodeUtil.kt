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


package love.forte.catcode

import love.forte.catcode.codes.MapNoraNeko
import love.forte.catcode.codes.NoraNyanko


/**
 * 野良猫码的操作工具类。
 *
 * 构建此工具类需要提供一个 `codeType`参数以代表此野良猫码的类型。
 *
 * 所谓野良猫，即code类型不一定是`CAT`的cat码。
 * 例如：`[CAT:at,code=123]`, 此码的类型为`CAT`, 所以是标准猫猫码，
 * 而例如`[CQ:at,code=123]`, 此码的类型为`CQ`, 不是标准猫猫码，即为野良猫码。
 *
 * > 野良猫 -> のらねこ -> 野猫 -> wildcat
 *
 */
@Suppress("unused", "DeprecatedCallableAddReplaceWith")
public class WildcatCodeUtil
private constructor(codeType: String) : NekoAibo(codeType) {

    companion object {
        @JvmStatic
        fun getInstance(codeType: String): WildcatCodeUtil = WildcatCodeUtil(codeType)
    }


    /**
     *  获取一个String为载体的[模板][CodeTemplate]
     *  @see StringTemplate
     */
    override val stringTemplate: WildcatTemplate<String> = WildcatStringTemplate(codeType, this)

    /**
     *  获取[Neko]为载体的[模板][CodeTemplate]
     *  @see NekoTemplate
     */
    override val nekoTemplate: WildcatTemplate<Neko> = NoraNekoTemplate(codeType, stringTemplate)


    /**
     * 构建一个String为载体类型的[构建器][CodeBuilder]。默认开启转义。
     */
    override fun getStringCodeBuilder(type: String, encode: Boolean): CodeBuilder<String> = WildcatStringCodeBuilder(codeType, type, encode)

    /**
     * 构建一个[Neko]为载体类型的[构建器][CodeBuilder]。默认开启转义。
     */
    override fun getNekoBuilder(type: String, encode: Boolean): CodeBuilder<Neko> = NoraNekoBuilder(codeType, type)

    /**
     * 构建一个[Neko]为载体类型的[构建器][CodeBuilder]。默认开启转义。
     */
    override fun getLazyNekoBuilder(type: String, encode: Boolean): LazyCodeBuilder<Neko> = LazyNoraNekoBuilder(codeType, type)


    /**
     * 获取无参数的[Neko]
     * @param type 猫猫码的类型
     */
    override fun toNeko(type: String): NoraNeko = EmptyNoraNeko(codeType, type)

    /**
     * 根据[Map]类型参数转化为[Neko]实例
     *
     * @param type 猫猫码的类型
     * @param params 参数列表
     */
    override fun toNeko(type: String, params: Map<String, *>): NoraNeko {
        return if (params.isEmpty()) {
            toNeko(type)
        } else {
            MapNoraNeko.byMap(codeType, type, params.asSequence().map { it.key to it.value.toString() }.toMap())
        }
    }


    /**
     * 根据参数转化为[Neko]实例
     * @param type 猫猫码的类型
     * @param kv 参数列表
     */
    override fun toNeko(type: String, vararg kv: CatKV<String, *>): NoraNeko {
        return if (kv.isEmpty()) {
            toNeko(type)
        } else {
            MapNoraNeko.byMap(codeType, type, kv.asSequence().map { it.key to it.value.toString() }.toMap())
        }
    }


    /**
     * 根据参数转化为[Neko]实例
     * @param type 猫猫码的类型
     * @param paramText 参数列表, 例如："qq=123"
     */
    override fun toNeko(type: String, encode: Boolean, vararg paramText: String): NoraNeko {
        return if (paramText.isEmpty()) {
            toNeko(type)
        } else {
            if (encode) {
                NoraNyanko.byCode(toCat(type, encode, *paramText))
            } else {
                MapNoraNeko.byParamString(codeType, type, *paramText)
            }
        }
    }


    /**
     * 提取出文本中的猫猫码，并封装为[Neko]实例。
     * @param text 存在猫猫码的正文
     * @param type 要获取的猫猫码的类型，默认为所有类型
     * @param index 获取的索引位的猫猫码，默认为0，即第一个
     */
    override fun getNeko(text: String, type: String, index: Int): Neko? {
        val cat: String = getCat(text, type, index) ?: return null
        return Neko.of(cat)
    }


}






