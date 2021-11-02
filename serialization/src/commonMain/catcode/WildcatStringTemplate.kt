/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  parent
 * File     WildcatCodeTemplate.kt
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 */

package catcode

import catcode.codes.NoraNyanko


/**
 * 野猫码模板接口。
 */
public interface WildcatTemplate<T> : CodeTemplate<T>


/**
 * 野良猫的 [CodeTemplate] 模板实现，以字符串为载体。
 */
public class WildcatStringTemplate(codeType: String, private val utilInstance: WildcatCodeUtil) : WildcatTemplate<String> {


    /**
     * at别人
     */
    override fun at(code: String): String =
        utilInstance.toCat("at", false, "code=${code.enCatParam()}")

    override fun at(code: Long): String =
        utilInstance.toCat("at", false, "code=$code")

    private val atAll: String by lazy(LazyThreadSafetyMode.PUBLICATION) { utilInstance.toCat("at", false, "all=true") }

    /**
     * at所有人
     */
    override fun atAll(): String = atAll

    /**
     * face
     */
    override fun face(id: String): String =
        utilInstance.toCat("face", false, "id=${id.enCatParam()}")

    override fun face(id: Long): String =
        utilInstance.toCat("face", false, "id=$id")

    /**
     * big face
     */
    override fun bface(id: String): String =
        utilInstance.toCat("bface", false, "id=${id.enCatParam()}")

    override fun bface(id: Long): String =
        utilInstance.toCat("bface", false, "id=$id")

    /**
     * small face
     */
    override fun sface(id: String): String =
        utilInstance.toCat("sface", false, "id=${id.enCatParam()}")

    override fun sface(id: Long): String =
        utilInstance.toCat("sface", false, "id=$id")


    /**
     * image
     * @param file id
     * @param flash 闪图
     */
    override fun image(file: String, flash: Boolean): String =
        utilInstance.toCat("image", false, "file=${file.enCatParam()}", "flash=$flash")

    override fun image(file: String): String =
        utilInstance.toCat("image", false, "file=${file.enCatParam()}")

    /**
     * 语言
     * [CAT:record,[file]={1},[magic]={2}] - 发送语音
     * {1}为音频文件名称，音频存放在酷Q目录的data\record\下
     * {2}为是否为变声，若该参数为true则显示变声标记。该参数可被忽略。
     * 举例：[CAT:record,file=1.silk，magic=true]（发送data\record\1.silk，并标记为变声）
     */
    override fun record(file: String, magic: Boolean): String =
        utilInstance.toCat("record", false, "file=${file.enCatParam()}", "magic=$magic")

    override fun record(file: String): String =
        utilInstance.toCat("record", false, "file=${file.enCatParam()}")


    /**
     * rps 猜拳
     * [CAT:rps,[type]={1}] - 发送猜拳魔法表情
     * {1}为猜拳结果的类型，暂不支持发送时自定义。该参数可被忽略。
     * 1 - 猜拳结果为石头
     * 2 - 猜拳结果为剪刀
     * 3 - 猜拳结果为布
     */
    override fun rps(type: String): String =
        utilInstance.toCat("rps", false, "type=${type.enCatParam()}")

    override fun rps(type: Int): String =
        utilInstance.toCat("rps", false, "type=$type")


    private val rps: String by lazy(LazyThreadSafetyMode.PUBLICATION) { utilInstance.toCat("rps") }
    override fun rps(): String = rps


    private val dice: String by lazy(LazyThreadSafetyMode.PUBLICATION) { utilInstance.toCat("dice") }
    override fun dice(): String = dice

    /**
     * 骰子
     * [CAT:dice,type={1}] - 发送掷骰子魔法表情
     * {1}对应掷出的点数，暂不支持发送时自定义。该参数可被忽略。
     */
    override fun dice(type: String): String = utilInstance.toCat("dice", false, "type=${type.enCatParam()}")
    override fun dice(type: Int): String = utilInstance.toCat("dice", false, "type=$type")


    /**
     * 戳一戳（原窗口抖动，仅支持好友消息使用）
     */
    override fun shake(): String = shake
    private val shake: String by lazy(LazyThreadSafetyMode.PUBLICATION) { utilInstance.toCat("shake") }

    /**
     * 音乐
     * [CAT:music,type={1},id={2},style={3}]
     * {1} 音乐平台类型，目前支持qq、163
     * {2} 对应音乐平台的数字音乐id
     * {3} 音乐卡片的风格。仅 Pro 支持该参数，该参数可被忽略。
     * 注意：音乐只能作为单独的一条消息发送
     * 例子
     * [CAT:music,type=qq,id=422594]（发送一首QQ音乐的“Time after time”歌曲到群内）
     * [CAT:music,type=163,id=28406557]（发送一首网易云音乐的“桜咲く”歌曲到群内）
     */
    override fun music(type: String, id: String, style: String?): String =
        utilInstance.toCat(
            "music",
            false,
            "type=${type.enCatParam()}",
            "id=${id.enCatParam()}",
            "style=${style?.enCatParam() ?: ""}"
        )

    override fun music(type: String, id: String): String =
        utilInstance.toCat(
            "music",
            false,
            "type=${type.enCatParam()}",
            "id=${id.enCatParam()}",
        )

    /**
     * [CAT:music,type=custom,url={1},audio={2},title={3},content={4},image={5}] - 发送音乐自定义分享
     * 注意：音乐自定义分享只能作为单独的一条消息发送
     * @param url   {1}为分享链接，即点击分享后进入的音乐页面（如歌曲介绍页）。
     * @param audio {2}为音频链接（如mp3链接）。
     * @param title  {3}为音乐的标题，建议12字以内。
     * @param content  {4}为音乐的简介，建议30字以内。该参数可被忽略。
     * @param image  {5}为音乐的封面图片链接。若参数为空或被忽略，则显示默认图片。
     *
     */
    override fun customMusic(url: String, audio: String, title: String, content: String?, image: String?): String =
        utilInstance.toCat(
            "music",
            false,
            "type=custom",
            "url=${url.enCatParam()}",
            "audio=${audio.enCatParam()}",
            "title=${title.enCatParam()}",
            "content=${content?.enCatParam() ?: ""}",
            "image=${image?.enCatParam() ?: ""}",
        )

    override fun customMusic(url: String, audio: String, title: String): String =
        utilInstance.toCat(
            "music",
            false,
            "type=custom",
            "url=${url.enCatParam()}",
            "audio=${audio.enCatParam()}",
            "title=${title.enCatParam()}",
        )

    /**
     * [CAT:share,url={1},title={2},content={3},image={4}] - 发送链接分享
     * {1}为分享链接。
     * {2}为分享的标题，建议12字以内。
     * {3}为分享的简介，建议30字以内。该参数可被忽略。
     * {4}为分享的图片链接。若参数为空或被忽略，则显示默认图片。
     * 注意：链接分享只能作为单独的一条消息发送
     */
    override fun share(url: String, title: String, content: String?, image: String?): String =
        utilInstance.toCat(
            "share", false,
            "url=${url.enCatParam()}",
            "title=${title.enCatParam()}",
            "content=${content?.enCatParam() ?: ""}",
            "image=${image?.enCatParam() ?: ""}",
        )

    override fun share(url: String, title: String): String =
        utilInstance.toCat(
            "share", false,
            "url=${url.enCatParam()}",
            "title=${title.enCatParam()}",
        )

}


/**
 * 野良猫码以 [Neko] 作为载体的 [CodeTemplate] 实现。
 */
public class NoraNekoTemplate(codeType: String, private val wildcatStringTemplate: WildcatTemplate<String>) : WildcatTemplate<Neko> {


    /**
     * at别人
     */
    override fun at(code: String): Neko = NoraNyanko.byCode(wildcatStringTemplate.at(code))
    override fun at(code: Long): Neko = NoraNyanko.byCode(wildcatStringTemplate.at(code))

    private val atAll by lazy(LazyThreadSafetyMode.PUBLICATION) { NoraNyanko.byCode(wildcatStringTemplate.atAll()) }

    /**
     * at所有人
     */
    override fun atAll(): Neko = atAll

    /**
     * face
     */
    override fun face(id: String): Neko = NoraNyanko.byCode(wildcatStringTemplate.face(id))
    override fun face(id: Long): Neko = NoraNyanko.byCode(wildcatStringTemplate.face(id))

    /**
     * big face
     */
    override fun bface(id: String): Neko = NoraNyanko.byCode(wildcatStringTemplate.bface(id))
    override fun bface(id: Long): Neko = NoraNyanko.byCode(wildcatStringTemplate.bface(id))

    /**
     * small face
     */
    override fun sface(id: String): Neko = NoraNyanko.byCode(wildcatStringTemplate.sface(id))
    override fun sface(id: Long): Neko = NoraNyanko.byCode(wildcatStringTemplate.sface(id))

    /**
     * image
     * @param file id
     * @param flash 闪图
     */
    override fun image(file: String, flash: Boolean): Neko =
        NoraNyanko.byCode(wildcatStringTemplate.image(file, flash))

    override fun image(file: String): Neko =
        NoraNyanko.byCode(wildcatStringTemplate.image(file))

    /**
     * 语言
     * [CAT:record,[file]={1},[magic]={2}] - 发送语音
     * {1}为音频文件名称，音频存放在酷Q目录的data\record\下
     * {2}为是否为变声，若该参数为true则显示变声标记。该参数可被忽略。
     * 举例：[CAT:record,file=1.silk，magic=true]（发送data\record\1.silk，并标记为变声）
     */
    override fun record(file: String, magic: Boolean): Neko =
        NoraNyanko.byCode(wildcatStringTemplate.record(file, magic))

    override fun record(file: String): Neko =
        NoraNyanko.byCode(wildcatStringTemplate.record(file))

    /**
     * rps 猜拳
     * [CAT:rps,[type]={1}] - 发送猜拳魔法表情
     * {1}为猜拳结果的类型，暂不支持发送时自定义。该参数可被忽略。
     * 1 - 猜拳结果为石头
     * 2 - 猜拳结果为剪刀
     * 3 - 猜拳结果为布
     */
    override fun rps(type: String): Neko =
        NoraNyanko.byCode(wildcatStringTemplate.rps(type))

    override fun rps(type: Int): Neko =
        NoraNyanko.byCode(wildcatStringTemplate.rps(type))

    private val rps by lazy(LazyThreadSafetyMode.PUBLICATION) { NoraNyanko.byCode(wildcatStringTemplate.rps()) }

    override fun rps(): Neko = rps

    private val dice by lazy(LazyThreadSafetyMode.PUBLICATION) { NoraNyanko.byCode(wildcatStringTemplate.dice()) }

    /**
     * 骰子
     * [CAT:dice,type={1}] - 发送掷骰子魔法表情
     * {1}对应掷出的点数，暂不支持发送时自定义。该参数可被忽略。
     */
    override fun dice(): Neko = dice

    override fun dice(type: String): Neko =
        NoraNyanko.byCode(wildcatStringTemplate.dice(type))

    override fun dice(type: Int): Neko =
        NoraNyanko.byCode(wildcatStringTemplate.dice(type))

    private val shake by lazy(LazyThreadSafetyMode.PUBLICATION) { NoraNyanko.byCode(wildcatStringTemplate.shake()) }

    /**
     * 戳一戳（原窗口抖动，仅支持好友消息使用）
     */
    override fun shake(): Neko = shake

    /**
     * 音乐
     * [CAT:music,type={1},id={2},style={3}]
     * {1} 音乐平台类型，目前支持qq、163
     * {2} 对应音乐平台的数字音乐id
     * {3} 音乐卡片的风格。仅 Pro 支持该参数，该参数可被忽略。
     * 注意：音乐只能作为单独的一条消息发送
     * 例子
     * [CAT:music,type=qq,id=422594]（发送一首QQ音乐的“Time after time”歌曲到群内）
     * [CAT:music,type=163,id=28406557]（发送一首网易云音乐的“桜咲く”歌曲到群内）
     */
    override fun music(type: String, id: String, style: String?): Neko =
        NoraNyanko.byCode(wildcatStringTemplate.music(type, id, style))

    /**
     * [CAT:music,type=custom,url={1},audio={2},title={3},content={4},image={5}] - 发送音乐自定义分享
     * 注意：音乐自定义分享只能作为单独的一条消息发送
     * @param url   {1}为分享链接，即点击分享后进入的音乐页面（如歌曲介绍页）。
     * @param audio {2}为音频链接（如mp3链接）。
     * @param title  {3}为音乐的标题，建议12字以内。
     * @param content  {4}为音乐的简介，建议30字以内。该参数可被忽略。
     * @param image  {5}为音乐的封面图片链接。若参数为空或被忽略，则显示默认图片。
     *
     */
    override fun customMusic(url: String, audio: String, title: String, content: String?, image: String?): Neko =
        NoraNyanko.byCode(wildcatStringTemplate.customMusic(url, audio, title, content, image))

    override fun customMusic(url: String, audio: String, title: String): Neko =
        NoraNyanko.byCode(wildcatStringTemplate.customMusic(url, audio, title))

    /**
     * [CAT:share,url={1},title={2},content={3},image={4}] - 发送链接分享
     * {1}为分享链接。
     * {2}为分享的标题，建议12字以内。
     * {3}为分享的简介，建议30字以内。该参数可被忽略。
     * {4}为分享的图片链接。若参数为空或被忽略，则显示默认图片。
     * 注意：链接分享只能作为单独的一条消息发送
     */
    override fun share(url: String, title: String, content: String?, image: String?): Neko =
        NoraNyanko.byCode(wildcatStringTemplate.share(url, title, content, image))

    override fun share(url: String, title: String): Neko =
        NoraNyanko.byCode(wildcatStringTemplate.share(url, title))

}
