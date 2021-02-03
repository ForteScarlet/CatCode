/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  parent
 * File     Msg.kt
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 */

package catcode



/**
 * msgs 消息链。
 */
@Deprecated("Not use.")
class Msgs
@JvmOverloads
constructor(private val delimiter: CharSequence = " ", private val delegate: () -> MutableList<CharSequence> = { mutableListOf() }): MutableList<CharSequence> by delegate() {
    @JvmOverloads
    constructor(delimiter: CharSequence = " ", collection: Collection<CharSequence>): this(delimiter){ mutableListOf(collection) }


    /** to string to show messages. delimiter is ' ' */
    override fun toString(): String = this.joinToString(delimiter)

    /** to list string to show messages.*/
    fun toListString(): String = this.joinToString(", ", "[", "]")

    /**
     * plus
     */
    operator fun plus(other: List<CharSequence?>): Msgs {
        other.forEach { this.add(it ?: "null") }
        return this
    }

    /**
     * plus
     */
    operator fun plus(other: CharSequence?): Msgs {
        this.add(other ?: "null")
        return this
    }
}