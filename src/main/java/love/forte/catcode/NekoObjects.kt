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
@file:JvmName("NekoObjects")
package love.forte.catcode

import love.forte.catcode.codes.Nyanko

/*
    提供一些可以作为单例使用的[KQCode]实例
 */

/**
 * at all
 * `[CAT:at,all=true]`
 */
val NekoAtAll : Neko = Nyanko.byCode("${CAT_HEAD}at,all=true$CAT_END")

/**
 * rps 猜拳
 * 发送用的猜拳
 * `[CAT:rps]`
 */
val NekoRps : Neko = EmptyNeko("rps")


/**
 * dice 骰子
 * 发送用的骰子
 * `[CAT:dice]`
 */
val NekoDice : Neko = EmptyNeko("dice")


/**
 * 窗口抖动，戳一戳
 * `[CAT:shake]`
 */
val NekoShake : Neko = EmptyNeko("shake")






