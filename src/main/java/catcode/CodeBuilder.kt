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

@file:Suppress("RedundantInnerClassModifier", "RedundantVisibilityModifier")

package catcode

import catcode.CodeBuilder.CodeBuilderKey
import catcode.codes.LazyMapNeko
import catcode.codes.MapNeko
import catcode.collection.MutableLazyMap
import catcode.collection.asLazyMap

/**
 *
 * 针对于猫猫码的构造器.
 *
 * 通过此构造器的实例, 来以
 * [builder][CodeBuilder].[key(String)][key].[value(Any?)][CodeBuilderKey.value].[build][CodeBuilder.build]
 * 的形式快速构造一个猫猫码实例并作为指定载体返回。
 *
 * @see StringCodeBuilder 以字符串为载体的builder。
 * @see NekoBuilder 以[Neko]作为载体的builder。
 *
 * @author ForteScarlet <ForteScarlet@163.com>
 * @since 1.8.0
 **/
public interface CodeBuilder<T> {

    /**
     * type类型
     */
    val type: String

    /**
     * 指定一个code的key, 并通过这个key设置一个value.
     */
    fun key(key: String): CodeBuilderKey<T>

    /**
     * 构建一个猫猫码, 并以其载体实例[T]返回.
     */
    fun build(): T

    /**
     * [CodeBuilder]在一次指定了[Key][key]之后得到的Key值载体.
     * 通过调用此类的[value]方法来得到自身所在的[CodeBuilder]
     *
     * 此类一般来讲是属于一次性临时类.
     *
     */
    interface CodeBuilderKey<T> {
        /**
         * 为当前Key设置一个value值并返回.
         */
        fun value(value: Any?): CodeBuilder<T>

        /**
         * 为当前Key设置一个空的value值并返回.
         */
        fun emptyValue(): CodeBuilder<T>
    }

}

/**
 * 懒加载CodeBuilder。
 * @param T
 */
public interface LazyCodeBuilder<T> : CodeBuilder<T> {

    /**
     * 指定一个code的key, 并通过这个key设置一个value.
     */
    override fun key(key: String): LazyCodeBuilderKey<T>


    /**
     * 懒加载codeBuilderKey
     * @param T
     */
    interface LazyCodeBuilderKey<T> : CodeBuilderKey<T> {
        override fun value(value: Any?): LazyCodeBuilder<T>
        fun value(value: () -> Any?): LazyCodeBuilder<T>
    }
}



//**************************************
//*          string builder
//**************************************


/**
 * 以`String`为载体的[CodeBuilder]实现类, 需要在构建实例的时候指定[类型][type]
 *
 * 以对字符串的拼接为主要构建形式, 且不是线程安全的。
 *
 * 如果[encode] == true, 则会对value值进行转义
 */
public class StringCodeBuilder
@JvmOverloads
constructor(override val type: String, private val encode: Boolean = true) : CodeBuilder<String> {
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
    override fun key(key: String): CodeBuilderKey<String> {
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
    private inner class StringCodeBuilderKey : CodeBuilderKey<String> {
        /**
         * 为当前Key设置一个value值并返回.
         */
        override fun value(value: Any?): CodeBuilder<String> {
            return key?.let { k ->
                appender.append(CAT_PS).append(k).append(CAT_KV)
                if (value != null) {
                    appender.append(CatEncoder.encodeParams(value.toString()))
                }
                this@StringCodeBuilder
            }?.also { this@StringCodeBuilder.key = null }
                ?: throw NullPointerException("The 'key' has not been specified.")
        }

        /**
         * 为当前Key设置一个空的value值并返回.
         */
        override fun emptyValue(): CodeBuilder<String> = value(null)
    }

}


//**************************************
//*         Neko Builder
//**************************************

/**
 * 以[Neko]为载体的[CodeBuilder]实现类, 需要在构建实例的时候指定[类型][type]。
 *
 * 通过[哈希表][MutableMap]来进行[Neko]的构建, 且不是线程安全的。
 */
public class NekoBuilder(override val type: String) : CodeBuilder<Neko> {

    /** 当前参数map */
    private val params: MutableMap<String, String> = mutableMapOf()

    /** 当前等待设置的key值 */
    private var key: String? = null

    /** [NekoBuilderKey]实例 */
    private val builderKey: NekoBuilderKey = NekoBuilderKey()

    /**
     * 指定一个code的key, 并通过这个key设置一个value.
     */
    override fun key(key: String): CodeBuilderKey<Neko> {
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
    private inner class NekoBuilderKey : CodeBuilderKey<Neko> {
        /**
         * 为当前Key设置一个value值并返回.
         */
        override fun value(value: Any?): CodeBuilder<Neko> {
            return key?.let { k ->
                params[k] = value?.toString() ?: ""
                this@NekoBuilder
            }?.also { this@NekoBuilder.key = null }
                ?: throw NullPointerException("The 'key' has not been specified.")
        }

        /**
         * 为当前Key设置一个空的value值并返回.
         */
        override fun emptyValue(): CodeBuilder<Neko> = value("")
    }

}



/**
 * 以[Neko]为载体的[LazyCodeBuilder]实现类, 需要在构建实例的时候指定[类型][type]。
 *
 * 通过[懒加载哈希表][MutableLazyMap]来进行[Neko]的构建, 且不是线程安全的。
 */
public class LazyNekoBuilder(
    override val type: String,
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.PUBLICATION
) : LazyCodeBuilder<Neko> {

    /** 当前参数map */
    private val params: MutableLazyMap<String, String> = MutableLazyMap(mode = mode)

    /** 当前等待设置的key值 */
    private var key: String? = null

    /** [LazyNekoBuilderKey]实例 */
    private val builderKey: LazyNekoBuilderKey = LazyNekoBuilderKey()

    /**
     * 指定一个code的key, 并通过这个key设置一个value.
     */
    override fun key(key: String): LazyCodeBuilder.LazyCodeBuilderKey<Neko> {
        return builderKey.also { this.key = key }
    }

    /**
     * 构建一个猫猫码, 并以其载体实例[T]返回.
     */
    override fun build(): Neko {
        return LazyMapNeko.byLazyMap(type, params.asLazyMap())
    }

    /**
     * 以[Neko]作为载体的[CodeBuilder.CodeBuilderKey]实现类
     */
    private inner class LazyNekoBuilderKey : LazyCodeBuilder.LazyCodeBuilderKey<Neko> {
        /**
         * 为当前Key设置一个value值并返回.
         */
        override fun value(value: Any?): LazyCodeBuilder<Neko> {
            return key?.let { k ->
                params[k] = value?.toString() ?: ""
                this@LazyNekoBuilder
            }?.also { this@LazyNekoBuilder.key = null }
                ?: throw NullPointerException("The 'key' has not been specified.")
        }

        /**
         * 为当前Key设置一个value值并返回.
         */
        override fun value(value: () -> Any?): LazyCodeBuilder<Neko> {
            return key?.let { k ->
                @Suppress("ReplacePutWithAssignment")
                params.put(k, initializer = { value()?.toString() ?: "" })
                this@LazyNekoBuilder
            }?.also { this@LazyNekoBuilder.key = null }
                ?: throw NullPointerException("The 'key' has not been specified.")
        }

        /**
         * 为当前Key设置一个空的value值并返回.
         */
        override fun emptyValue(): LazyCodeBuilder<Neko> = value("")
    }

}
