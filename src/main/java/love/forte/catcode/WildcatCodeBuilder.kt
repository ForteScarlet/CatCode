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

import love.forte.catcode.codes.MapNeko
import love.forte.catcode.collection.MutableLazyMap

/**
 * 野猫码构建器。
 */
public interface WildcatCodeBuilder<T> : CodeBuilder<T> {
    /**
     * 野猫码的大类型。
     */
    val codeType: String

    /**
     * catcode param key.
     */
    override fun key(key: String): WildcatCodeBuilderKey<T>

    /**
     * wildcat builder key like [CodeBuilder.CodeBuilderKey]
     */
    public interface WildcatCodeBuilderKey<K> : CodeBuilder.CodeBuilderKey<K> {
        override fun value(value: Any?): WildcatCodeBuilder<K>
        override fun emptyValue(): WildcatCodeBuilder<K>
    }

}

/**
 * 支持懒加载的 [WildcatCodeBuilder].
 * @param T
 */
public interface LazyWildcatCodeBuilder<T> : WildcatCodeBuilder<T>, LazyCodeBuilder<T> {

    /**
     * 指定一个code的key, 并通过这个key设置一个value.
     */
    override fun key(key: String): LazyWildcatCodeBuilderKey<T>


    /**
     * 懒加载codeBuilderKey
     * @param T
     */
    interface LazyWildcatCodeBuilderKey<T> : WildcatCodeBuilder.WildcatCodeBuilderKey<T>,
        LazyCodeBuilder.LazyCodeBuilderKey<T> {
        override fun value(value: Any?): LazyWildcatCodeBuilder<T>
        override fun value(value: () -> Any?): LazyWildcatCodeBuilder<T>
    }
}


/**
 * 以字符串为载体的 [WildcatCodeBuilder] 实现。
 */
public class WildcatStringCodeBuilder
@JvmOverloads
constructor(
    override val codeType: String,
    override val type: String,
    private val encode: Boolean = true
) : WildcatCodeBuilder<String> {

    /** [StringBuilder] */
    private val appender: StringBuilder = StringBuilder(CAT_HEAD).append(type)

    /** [StringCodeBuilderKey]实例 */
    private val builderKey: StringCodeBuilderKey = StringCodeBuilderKey()

    /** 当前等待设置的key值 */
    @Volatile
    private var key: String? = null

    /**
     * 指定一个code的key, 并通过这个key设置一个value.
     */
    override fun key(key: String): WildcatCodeBuilder.WildcatCodeBuilderKey<String> {
        return builderKey.also { this.key = key }
    }

    /**
     * 构建一个猫猫码, 并以其载体实例String返回.
     */
    override fun build(): String = appender.toString() + CAT_END


    /**
     * [StringCodeBuilder]中[CodeBuilder.CodeBuilderKey]的实现类。
     * 此类在[StringCodeBuilder]中只会存在一个实例，因此 **线程不安全**。
     */
    private inner class StringCodeBuilderKey : WildcatCodeBuilder.WildcatCodeBuilderKey<String> {
        /**
         * 为当前Key设置一个value值并返回.
         */
        override fun value(value: Any?): WildcatCodeBuilder<String> {
            return key?.let { k ->
                appender.append(CAT_PS).append(k).append(CAT_KV)
                if (value != null) {
                    appender.append(CatEncoder.encodeParams(value.toString()))
                }
                this@WildcatStringCodeBuilder
            }?.also { this@WildcatStringCodeBuilder.key = null }
                ?: throw NullPointerException("The 'key' has not been specified.")
        }

        /**
         * 为当前Key设置一个空的value值并返回.
         */
        override fun emptyValue(): WildcatCodeBuilder<String> = value(null)
    }


}


/**
 * 以[Neko]为载体的[CodeBuilder]实现类, 需要在构建实例的时候指定[类型][type]。
 *
 * 通过[哈希表][MutableMap]来进行[Neko]的构建, 且不是线程安全的。
 */
public class NoraNekoBuilder(override val codeType: String, override val type: String) : WildcatCodeBuilder<Neko> {

    /** 当前参数map */
    private val params: MutableMap<String, String> = mutableMapOf()

    /** 当前等待设置的key值 */
    private var key: String? = null

    /** [NoraNekoBuilderKey]实例 */
    private val builderKey: NoraNekoBuilderKey = NoraNekoBuilderKey()

    /**
     * 指定一个code的key, 并通过这个key设置一个value.
     */
    override fun key(key: String): WildcatCodeBuilder.WildcatCodeBuilderKey<Neko> {
        return builderKey.also { this.key = key }
    }

    /**
     * 构建一个猫猫码, 并以其载体实例[T]返回.
     */
    override fun build(): Neko {
        return MapNeko.byMap(type, params.toMap())
    }

    /**
     * 以[Neko]作为载体的[CodeBuilder.CodeBuilderKey]实现类
     */
    private inner class NoraNekoBuilderKey : WildcatCodeBuilder.WildcatCodeBuilderKey<Neko> {
        /**
         * 为当前Key设置一个value值并返回.
         */
        override fun value(value: Any?): WildcatCodeBuilder<Neko> {
            return key?.let { k ->
                params[k] = value?.toString() ?: ""
                this@NoraNekoBuilder
            }?.also { this@NoraNekoBuilder.key = null }
                ?: throw NullPointerException("The 'key' has not been specified.")
        }

        /**
         * 为当前Key设置一个空的value值并返回.
         */
        override fun emptyValue(): WildcatCodeBuilder<Neko> = value("")
    }


}


/**
 * 以[Neko]为载体的[CodeBuilder]实现类, 需要在构建实例的时候指定[类型][type]。
 *
 * 通过[哈希表][MutableMap]来进行[Neko]的构建, 且不是线程安全的。
 */
public class LazyNoraNekoBuilder(override val codeType: String, override val type: String) :
    LazyWildcatCodeBuilder<Neko> {

    /** 当前参数map */
    private val params: MutableLazyMap<String, String> = MutableLazyMap()

    /** 当前等待设置的key值 */
    private var key: String? = null

    /** [NoraNekoBuilderKey]实例 */
    private val builderKey: LazyNoraNekoBuilderKey = LazyNoraNekoBuilderKey()

    /**
     * 指定一个code的key, 并通过这个key设置一个value.
     */
    override fun key(key: String): LazyWildcatCodeBuilder.LazyWildcatCodeBuilderKey<Neko> {
        return builderKey.also { this.key = key }
    }

    /**
     * 构建一个猫猫码, 并以其载体实例[T]返回.
     */
    override fun build(): Neko {
        return MapNeko.byMap(type, params.toMap())
    }

    /**
     * 以[Neko]作为载体的[CodeBuilder.CodeBuilderKey]实现类
     */
    private inner class LazyNoraNekoBuilderKey : LazyWildcatCodeBuilder.LazyWildcatCodeBuilderKey<Neko> {
        /**
         * 为当前Key设置一个value值并返回.
         */
        override fun value(value: Any?): LazyWildcatCodeBuilder<Neko> {
            return key?.let { k ->
                params[k] = value?.toString() ?: ""
                this@LazyNoraNekoBuilder
            }?.also { this@LazyNoraNekoBuilder.key = null }
                ?: throw NullPointerException("The 'key' has not been specified.")
        }

        /**
         * 为当前Key设置一个value值并返回.
         */
        override fun value(value: () -> Any?): LazyWildcatCodeBuilder<Neko> {
            return key?.let { k ->
                @Suppress("ReplacePutWithAssignment")
                params.put(k, initializer = { value()?.toString() ?: "" })
                this@LazyNoraNekoBuilder
            }?.also { this@LazyNoraNekoBuilder.key = null }
                ?: throw NullPointerException("The 'key' has not been specified.")
        }

        /**
         * 为当前Key设置一个空的value值并返回.
         */
        override fun emptyValue(): WildcatCodeBuilder<Neko> = value("")
    }
}
