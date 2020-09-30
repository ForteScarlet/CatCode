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

/**
 * test 1
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class ReadmeTest1 {
    public static void main(String[] args) {

        // get util instance.
        final CatCodeUtil catUtil = CatCodeUtil.INSTANCE;

        // 构建一个猫猫码 - string
        // 例如构建一个字符串类型的: [CAT:at,code=123456,name=forte,age=12]
        // 1. StringCodeBuilder
        final CodeBuilder<String> stringBuilder = catUtil.getStringCodeBuilder("at");
        String catCode1 = stringBuilder
                .key("code").value(123456)
                .key("name").value("forte")
                .key("age").value(12)
                .build();

        System.out.println(catCode1);

        // 构建一个猫猫码 - Neko
        // 例如构建一个Neko类型的: [CAT:image,file=1.jpg,dis=true]
        // 2. NekoBuilder
        final CodeBuilder<Neko> nekoBuilder = catUtil.getNekoBuilder("image");
        Neko catCode2 = nekoBuilder
                .key("file").value("1.jpg")
                .key("dis").value(true)
                .build();

        System.out.println(catCode2);
    }
}
