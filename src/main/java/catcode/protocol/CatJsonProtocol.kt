@file:JvmName("CatJsonProtocol")

package catcode.protocol

import catcode.Neko

/**
 * 较为'清晰'的结构。
 * ```
 * {
 *  "type": $type,
 *  "data": {
 *      $key: $value
 *      // ...
 *   }
 * }
 * ```
 *
 */
public const val DISTINCT: Int = 0

/**
 *
 * 较为'紧凑'的结构。
 * ```
 * {
 *   $type: {
 *      $key: $value
 *      // ...
 *   }
 * }
 * ```
 *
 */
public const val COMPACT: Int = 1


/**
 * 将一个 [Neko] 转化为一个 `json` 格式的消息。
 *
 * ### DISTINCT 模式：
 *
 * ```
 * {
 *  "type": $type,
 *  "data": {
 *      $key: $value
 *      // ...
 *   }
 * }
 * ```
 *
 * 其中，可以指定 [typeName] 与 [dataName].
 *
 *
 * ### COMPACT 模式：
 *
 * ```
 * {
 *   $type: {
 *      $key: $value
 *      // ...
 *   }
 * }
 * ```
 *
 * 需要注意的是，上述两种模式实际上都没有换行与缩进，注释中的示例仅为方便描述用。
 *
 */
@JvmOverloads
@Deprecated("Not implemented.")
public fun Neko.toJson(mode: Int = DISTINCT, typeName: String = "type", dataName: String = "data"): String {
    TODO("Not implemented.")
}
