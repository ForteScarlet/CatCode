@file:JvmName("NoNeedInitializeLazies")
package love.forte.catcode.collection


/**
 * 不需要初始化的lazy实例。
 */
private data class NoNeedInitializeLazy<T>(override val value: T) : Lazy<T> {
    override fun isInitialized(): Boolean = true
}


/** 通过一个实例对象构建一个 [Lazy] 实例。 */
public fun <T> lazyValue(value: T): Lazy<T> = NoNeedInitializeLazy(value)
