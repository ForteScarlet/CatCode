@file:JvmName("CodeBuilderDslUtil")

package catcode


fun main() {
    val util = CatCodeUtil

    val code = util.getStringCodeBuilder("5", true).build {

        key("name") {
            "jack"
        }
    }

    println(code)


}

public fun <T> CodeBuilder<T>.build(block: CodeBuilderDSL<T>.() -> CodeBuilderDSL<T>): T {
    return CodeBuilderDSL(this).block().builder.build()
}


@NekoDsl
public inline class CodeBuilderDSL<T>(internal val builder: CodeBuilder<T>)

@NekoDsl
public inline class CodeBuilderKeyDSL<T>(internal val key: CodeBuilder.CodeBuilderKey<T>)


public val <T> CodeBuilderDSL<T>.type: String get() = builder.type

public infix fun <T> CodeBuilderDSL<T>.key(k: String): CodeBuilderKeyDSL<T> = CodeBuilderKeyDSL(builder key k)
public infix fun <T> CodeBuilderKeyDSL<T>.value(v: Any?): CodeBuilderDSL<T> = CodeBuilderDSL(key value v)
public fun <T> CodeBuilderKeyDSL<T>.emptyValue(): CodeBuilderDSL<T> = CodeBuilderDSL(key.emptyValue())

@NekoDsl
public inline fun <T> CodeBuilderDSL<T>.key(k: String, vBlock: () -> Any?): CodeBuilderDSL<T> {
    return this key k value vBlock()
}




















