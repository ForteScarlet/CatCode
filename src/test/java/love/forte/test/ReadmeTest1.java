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

import love.forte.catcode.CatCodeUtil;
import love.forte.catcode.CodeBuilder;
import love.forte.catcode.Neko;
import love.forte.catcode.WildcatCodeUtil;

import java.util.List;

/**
 * Readme test 1 for create a cat code by Builder.
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class ReadmeTest1 {
    public static void main(String[] args) {
        // get util instance.
        final CatCodeUtil catUtil = CatCodeUtil.INSTANCE;

        String at = "添加管理员[CAT:at,code=2473125346]";

        List<Neko> nekos = catUtil.getNekoList(at, "at");

        for (Neko neko : nekos) {
            System.out.println(neko);
        }


    }
}
