/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  parent
 * File     WildcatCodeBuilder.kt
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 */

package love.forte.catcode

/**
 * 野猫码构建器。
 */
public interface WildcatCodeBuilder<T> : CodeBuilder<T> {
    val codeType: String
}
