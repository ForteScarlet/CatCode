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

package catcode



/**
 *
 * @author ForteScarlet <ForteScarlet@163.com>
 * 2020/8/12
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.BINARY)
@DslMarker
annotation class NekoDsl

//
// /**
//  * Params
//  * @since 1.0-1.11
//  */
// @NekoDsl
// open class Params {
//     private val plist: MutableList<Pair<String, String>> = mutableListOf()
//     var param: Pair<String, String>
//         get() = plist.last()
//         set(value) { plist.add(value) }
//
//     operator fun set(param: String, value: String) {
//         this.param = param to value
//     }
//
//     /** 添加全部 */
//     open fun addTo(neko: MutableNeko): MutableNeko {
//         plist.forEach {
//             neko[it.first] = it.second
//         }
//         return neko
//     }
//
//     override fun toString(): String = plist.toString()
// }
//
// /**
//  * Builder
//  * @since 1.0-1.11
//  */
// @NekoDsl
// class Builder {
//     var type: String = ""
//     internal val _params = Params()
//     var param: Pair<String, String>
//         get() = _params.param
//         set(value) { _params.param = value }
//     /** 添加全部 */
//     fun build(): Neko {
//         val kqCode = MapNeko.byCode(type)
//         return _params.addTo(kqCode.mutable())
//     }
//
//     override fun toString(): String = "$type:$_params"
// }
//
//
//
// /**
//  *
//  * 考虑使用 [CodeBuilder]。
//  * DSL将更替为使用 [CodeBuilder]。
//  *
//  * @since 1.0-1.11
//  * @see CodeBuilder
//  *
//  */
// @NekoDsl
// @Deprecated("see CodeBuilder")
// fun kqCode(type: String, block: Params.() -> Unit): Neko {
//     val kqCode = MapNeko(type = type)
//     return Params().apply(block).addTo(kqCode.mutable())
// }
//
// /**
//  * DSL构建KQCode的参数列表
//  * @since 1.0-1.11
//  *
//  * @see CodeBuilder
//  *
//  */
// @NekoDsl
// @Deprecated("see CodeBuilder")
// fun kqCode(block: Builder.() -> Unit) = Builder().apply(block).build()
//
// /**
//  * DSL构建Builder中的params, 例如
//  * ```
// kqCode {
// type = "at"
// params {
// param = "qq" to "1149"
// param = "file" to "neko.jpg"
// }
// }
//  * ```
//  * @since 1.0-1.11
//  */
// @NekoDsl
// fun Builder.params(block: Params.() -> Unit) {
//     this._params.apply(block)
// }
