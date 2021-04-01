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

package love.forte.test;

import catcode.*;

import java.util.Map;

/**
 * Readme test 1 for create a cat code by Builder.
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class ReadmeTest1 {
    public static void main(String[] args) {
        // 获取CatCodeUtil实例。
        CatCodeUtil util = CatCodeUtil.INSTANCE;

        String text = "hello, [CAT:at,code=123456]";

        // 获取 text中第一个CAT码字符串，并转化为Neko实例。
        Neko atNeko = util.getNeko(text);

        assert atNeko != null;

        System.out.println(atNeko); // [CAT:at,code=123456]
        System.out.println(atNeko.getType()); // at
        System.out.println(atNeko.get("code")); // 123456
        // 可以将neko中的各项参数转化为一个Map.
        // 需要注意的是，此Map一般情况下是不允许进行修改的。
        Map<String, String> nekoMap = atNeko.toMap();
        System.out.println(nekoMap); // NekoMap(delegate=[CAT:at,code=123456])
        // 强行修改元素会导致异常。
        // nekoMap.put("name", "forte"); // error: Operation is not supported for read-only collection

        // 将neko转化为可变Neko。
        MutableNeko mutableNeko = atNeko.asMutable();

        // 添加参数
        mutableNeko.put("name", "forte");
        System.out.println(mutableNeko); // [CAT:at,code=123456,name=forte]
        // 可变Neko可以转化为可变Map
        Map<String, String> mutableNekoMap = mutableNeko.toMap();

        System.out.println(mutableNekoMap); // {code=123456, name=forte}
        // 移除'code参数'
        mutableNekoMap.remove("code");
        System.out.println(mutableNekoMap); // {name=forte}


        // 不会对原本的Neko实例造成影响。
        System.out.println(mutableNeko);    // [CAT:at,code=123456,name=forte]

    }
}
