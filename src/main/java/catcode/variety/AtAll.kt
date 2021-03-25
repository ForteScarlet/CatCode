@file:Suppress("MemberVisibilityCanBePrivate")

package catcode.variety

import catcode.*
import catcode.codes.*
import catcode.codes.DelegateCatVariety
import catcode.collection.SimpleEntry

/**
 * 一个 **at全体** 的模板实例接口。
 *
 * at全体，优先标准为存在参数 `all=true`, 次级标准为存在参数 `code=all`。
 *
 */
public interface AtAll : CatVariety {

    companion object Variety : CatVarietyConverter<AtAll> {
        const val TYPE = "at"
        /**
         * 将一个[Neko] 作为一个 [AtAll]
         * @throws CatVarietyConvertException 类型不匹配或参数缺失时。
         */
        @JvmStatic
        override fun tryAsByNeko(neko: Neko): AtAll {
            if (neko is AtAll) return neko

            // type
            if (neko.type != TYPE) {
                throw CatVarietyConvertException("Neko type '${neko.type}' != $TYPE")
            }
            // params
            if (!(neko["code"] == "all" || neko["all"] == "true")) {
                throw CatVarietyConvertException("Neko params does not exist [code=all] or [all=true].")
            }

            return NekoAtAll(neko)
        }

        /**
         * 根据 [codeType] 直接获取一个AtAll实例.
         */
        fun getInstance(codeType: String = CAT_TYPE): AtAll {
            return if (codeType == CAT_TYPE) AtAllObj else AtAllImpl(codeType)
        }

        /**
         * 得到一个 `codeType` == `cat` 的 [AtAll] 实例。
         */
        val instance: AtAll = AtAllObj

    }
}

internal class NekoAtAll(delegate: Neko) : DelegateCatVariety(delegate), AtAll {
    val all: Boolean get() = true

    override fun toString(): String = "${catHead(codeType)}at,all=true$CAT_END"

    override fun get(key: String): String? {
        return if (key == "all") "true" else super.get(key)
    }
}



internal class AtAllImpl(codeType: String) : AtAll, DelegateCatVariety(
    if (codeType == CAT_TYPE) CatCodeUtil.nekoTemplate.atAll()
    else WildcatCodeUtil.getInstance(codeType).nekoTemplate.atAll()
)

/**
 * 用于直接获取的 [AtAll] 对象.
 */
internal object AtAllObj : AtAll {

    private const val TYPE = "at"
    private const val KEY = "all"
    private const val VALUE = "true"
    private const val CODE = "[CAT:at,$KEY=$VALUE]"

    override val length: Int
        get() = CODE.length

    override fun get(index: Int): Char = CODE[index]

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
        CODE.subSequence(startIndex, endIndex)

    /**
     * 获取Code的类型。
     */
    override val type: String
        get() = TYPE

    /**
     * 与 [get] 一致。
     */
    override fun getNoDecode(key: String): String? {
        return get(key)
    }

    override fun asMutable(): MutableNeko {
        return MutableMapNeko(TYPE, KEY cTo VALUE)
    }
    override fun asImmutable(): Neko = this

    override fun toMutable(): MutableNeko {
        return MutableMapNeko(TYPE, KEY cTo VALUE)
    }
    override fun toImmutable(): Neko = Nyanko.byCode(CODE)

    /**
     * 转化为 [Map]。
     */
    override fun toMap(): Map<String, String> {
        return mapOf(KEY to VALUE)
    }

    /**
     * 此码中的所有键值对。
     */
    override val entries: Set<Map.Entry<String, String>>
        = setOf(SimpleEntry(KEY, VALUE))

    /**
     * 此码中的所有键。
     */
    override val keys: Set<String>
        = setOf(KEY)

    /**
     * 此码中的所有值。
     */
    override val values: Collection<String>
        get() = listOf(VALUE)

    /**
     * 此码中的键值对参数数量。
     */
    override val size: Int
        get() = 1

    /**
     * 是否包含某个键。
     */
    override fun containsKey(key: String): Boolean = key == KEY

    /**
     * 是否包含某个值。
     */
    override fun containsValue(value: String): Boolean = value == VALUE

    /**
     * 是否没有参数。
     */
    override fun isEmpty(): Boolean = false

    /**
     * 获取某个键对应的值。
     */
    override fun get(key: String): String? = key.takeIf { it == KEY }?.let { VALUE }

    override fun toString(): String = CODE
    override fun hashCode(): Int = CODE.hashCode()
    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Neko) return false
        if (other is AtAll) return true
        if (other["all"] == "true" || other["code"] == "all") return true
        return false
    }


}