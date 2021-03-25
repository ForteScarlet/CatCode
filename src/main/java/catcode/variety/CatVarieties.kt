@file:JvmName("CatVarieties")

package catcode.codes

import catcode.CatCodeRuntimeException
import catcode.Neko

/**
 * 猫猫品种转化异常。
 */
public open class CatVarietyConvertException : CatCodeRuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(
        message,
        cause,
        enableSuppression,
        writableStackTrace)
}



/*
    Cat variety
    猫的品种

    即各种已知模板下的Neko类型定义。

 */

/**
 * 猫猫品种标记接口，标记于指定的猫猫品种类型。
 */
public interface CatVariety : Neko


/**
 * 猫猫品种转化器接口，由猫猫模板类型的类型伴生实现或者object实现。
 *
 */
public interface CatVarietyConverter<T : CatVariety> {

    /**
     * 尝试通过一个Neko进行转化。会检测其 [Neko.type] 和各项所需的必要参数。
     *
     * @throws CatVarietyConvertException 类型不匹配或参数缺失时。
     */
    fun tryAsByNeko(neko: Neko): T
}


/**
 * 委托Neko
 */
internal abstract class DelegateCatVariety(protected val delegate: Neko) : CatVariety, Neko by delegate








