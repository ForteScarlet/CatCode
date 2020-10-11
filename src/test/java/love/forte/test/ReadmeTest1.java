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

/**
 * Readme test 1 for create a cat code by Builder.
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class ReadmeTest1 {
    public static void main(String[] args) {

        // get util instance.
        final CatCodeUtil catUtil = CatCodeUtil.INSTANCE;

        String code = catUtil.toCat("at", false, "qq=");

        System.out.println(code);

        // get util instance.
        final WildcatCodeUtil cqUtil = WildcatCodeUtil.getInstance("cq");

        String cqcode = cqUtil.toCat("at", false, "qq=");

        System.out.println(cqcode);

    }
}