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

/**
 * Readme test 1 for create a cat code by Builder.
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class ReadmeTest1 {
    public static void main(String[] args) {
        // get util instance.

        Neko neko = CatCodeUtil.INSTANCE.toNeko("image", "id=aabbcc214", "type=jpeg");

        System.out.println(neko);

        Neko switchedNeko = Cats.switchCodeType(neko, "CQ");

        System.out.println(switchedNeko.getClass());

        System.out.println(switchedNeko.toString());
        System.out.println(switchedNeko.getCodeType());






    }
}
