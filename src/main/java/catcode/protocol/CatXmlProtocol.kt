@file:JvmName("CatXmlProtocol")
package catcode.protocol

import catcode.Neko


private const val XML_SINGLE_NODE_PRE = '<'
private const val XML_SINGLE_NODE_POST = "/>"
private const val XML_SINGLE_NODE_SPLIT = ' '
private const val XML_SINGLE_NODE_KV = '='
private const val XML_SINGLE_NODE_VALUE_PRE = '"'
private const val XML_SINGLE_NODE_VALUE_POST = XML_SINGLE_NODE_VALUE_PRE



/**
 * 将一个 [Neko] 实例转化为一个单条xml协议消息。
 * TODO xml转义
 */
@JvmOverloads
@Deprecated("imperfect, There is no xml escaping.")
public fun Neko.toXml(appender: Appendable = StringBuilder()): String {

    appender.append(XML_SINGLE_NODE_PRE).append(type).append(' ')

    if (isEmpty()) {
        appender.append(XML_SINGLE_NODE_SPLIT)
    } else {
        forEach { k, v ->
            appender
                .append(k)
                .append(XML_SINGLE_NODE_KV)
                .append(XML_SINGLE_NODE_VALUE_PRE)
                .append(v)
                .append(XML_SINGLE_NODE_VALUE_POST)
                .append(XML_SINGLE_NODE_SPLIT)
        }
    }

    appender.append(XML_SINGLE_NODE_POST)

    return appender.toString()
}


