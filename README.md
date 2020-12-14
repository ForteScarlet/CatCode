<div align="center">
    <img src="./logo/CatCodeLogo@0,1x.png"/>
    <h3>
        - 😺 CatCode 😺 -
    </h3>
    <span>
        <a href="https://github.com/ForteScarlet/CatCode" target="_blank">github</a>
    </span> 
    &nbsp;&nbsp; | &nbsp;&nbsp;
    <span>
        <a href="https://gitee.com/ForteScarlet/CatCode" target="_blank">gitee</a>
    </span> <br />
    <small> &gt; 感谢 <a href="https://github.com/ForteScarlet/simpler-robot" target="_blank">simple-robot</a> 开发团队成员制作的猫猫logo &lt; </small> <br />
    <a href="https://repo1.maven.org/maven2/love/forte/catcode/" target="_blank" >
        <img src="https://img.shields.io/maven-central/v/love.forte/catcode" />
    </a>

</div>

*****

<div align="center">
    <h3>
        猫猫码（Cat code），一个可爱的通用特殊码，CQ码的精神延续。
    </h3>
</div>   





猫猫码是一个具有特定格式的字符串格式特殊码，规则为`[CAT:xxx,param=value,param=value,...]`的格式，其中：

- 以`[`开头，`]`结尾。
- 开头后跟猫猫码的大类型(大小写数字或下划线，标准应为`CAT`)
- 开大类型后为此码的小类型(大小写数字或下划线)，与大类型之间使用冒号`:`分割。
- 参数为多个key不重复的键值对，一对参数使用`=`连接键与值，多对参数使用`,`分割。
- 可以没有参数。
- 区分大小写。

## 应用

猫猫码将会被作为[simpler-robot](https://github.com/ForteScarlet/simpler-robot)框架 (即[simple-robot](https://github.com/ForteScarlet/simple-robot-core)的2.x版本) 的送信特殊码使用，以取代曾经的CQ码。

当然，猫猫码也属于一种消息格式，某种程度上猫猫码也可以表示为类似于`json`等消息类型，但是我不认为他会比json更好用。

但是如果你觉得它很可爱，也欢迎使用~


**如果你愿意送给猫猫一颗右上角的星星，猫猫会很开心的喔~**


### **猫猫是严格的。** 
为了避免混淆整个存在猫猫码的文本中不允许出现：`[`、`]`、`&` 字符和制表符与换行符，猫猫码文本内中除了上述字符外，还不允许出现`,`字符。
因此，猫猫码的转义规则为：
- `&` ->   `&amp;`
- `[` ->   `&#91;`
- `]` ->   `&#93;`
- `,` ->   `&#44;`
- `\n` ->  `&#13;`
- `\r` ->  `&#10;`
- `\t` ->  `&#09;`


### **猫猫是慵懒的。** 
本库提供了丰富地猫猫码解析、封装相关内容。

一切工具类相关的均以`Cat`开头(或包含)，例如`CatDecoder`、`CatCodeUtil`等，
而针对于猫猫码的封装类相关，大部分均以`Neko`为开头(或包含)命名。例如`Neko`、`Nyanko(FastNeko)`、`MapNeko`。

### **猫猫是多样的。** 
猫猫码封装接口有`Neko`（保守猫猫，即参数不可变）、`MutableNeko`（善变猫猫，可动态变更参数，但是不再基于字符串操作）、`EmptyNeko`（佛系猫猫，即静态的空参实例）、
以及上述几个类型所各自对应的`NoraNeko`（野良猫猫，即猫猫码大类型不是`CAT`但是规则与猫猫码一致的野良猫猫码）

### **猫猫是警觉的。** 
猫猫码工具主要基于字符串操作，减少资源占用与解析封装损耗，效率更高（尤其是在使用不变猫猫的时候）。

### **猫猫是包容的。** 
任何符合规则：`[TYPE:type,param=value,param=value,...]`的特殊码均可以视为猫猫码，尽管`TYPE`并不是`CAT`。

这类其他类型的“猫猫码”称为`WildcatCode(野良猫码)`, 可以通过`WildcatCodeUtil`工具来实现对应的解析, 通过`NoraNeko`实例类型作为封装。




## 使用方式

## JVM 

### Maven

※ 版本可参考上面的版本号小图标。

```xml
<dependency>
    <groupId>love.forte</groupId>
    <artifactId>catcode</artifactId>
    <version>${version}</version>
</dependency>
```

## JS
未来将会通过kotlin多平台实现

## Native
未来将会通过kotlin多平台实现

## 其他语言
敬请期待...

> 你可以基于标准CAT码格式自行实现。


## 常用猫猫

猫猫码存在一些十分常用的类型，这些类型的参数拥有已经约定俗成的参数格式。

#### text 文本
text类型是一个基础的类型，其代表了一段不包含cat码的普通文本。
text类型的cat码只有一个参数 `text`, 其代表了文本的正文内容。

之所以会存在text类型的猫猫码是因为有些场景下可能会需要将一个文本内容表示为一个 `Neko` 实例。


#### at 艾特
    
at类型可以说是十分常见了，其代表 **@** 了某个人。
at类型存在几个常见参数：`code`、`all`。

`code`代表其对应被at的用户的账号，是字符串或者数字类型。

例如：`[CAT:at,code=1149159218]`。

而`all`代表此CAT是否代表**at全体**，类型为bool类型。

例如：`[CAT:at,all=true]`。

当然，也存在使用`code=all`来代表at全体的效果。

通常来讲，`code`参数与`at`参数二者只会存在一个。
    
    
#### image 图片

image类型代表一个**图片类型**的消息。
image类型存在几个常见参数：`id`、`file`、`url`、`flash`。

`id`一般代表这个图片的一个网络服务器标识。

`file`一般代表这个图片的本地文件路径，有时候也会作为`id`参数的代替。

`url`代表这个图片的网络路径。

`flash`是bool类型，代表是否作为一个**闪照**或者**可销毁**图片。

例如：`[CAT:image,id=1,file=1.jpg,flash=true]`

通常来讲，`file`、`url`两个参数可能同时存在，或至少出现其中一种，而`id`则根据是否实际存在id值而决定。


#### voice 语音
voice类型代表一个**语音类型**的消息。
voice类型存在几个常见参数：`id`、`file`、`url`，其含义与`image`类型基本一致。


#### face 表情
face类型也是一个十分常见的类型，其代表了一个通讯应用中的普通默认表情。
其存在一个 `id` 参数代表表情的标识。

例如：`[CAT:face,id=1]`


#### share 分享
share类型是一种十分常见的类型，但是它不一定能够被所有平台支持。尽管如此，他依然十分常见。
分享类型一般会存在4种常见参数：`title`、`content`、`url`、`image`,
其中：

`title` 代表分享链接的标题。

`content` 代表分享链接的简述。

`url` 代表分享的链接。

`image` 代表分享的链接的封面。

而这其中，`url`参数是必然存在的。

例如：`[CAT:share,url=www.baidu.com,title=百度一下,content=百度一下你就知道]`




## 构建Cat码

### Java

#### 1. 通过Builder构建CatCode

```java
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
```

#### 2. 直接获取部分无参模板
```java
// NekoObjects中的可获取值均为部分无参数常量，例如at全体。
final Neko alAll = NekoObjects.getNekoAtAll();
```




<br/>
<br/>
<br/>



> Neko : ねこ(猫)，意为“猫”。
>
> NoraNeko : のらねこ(野良猫)，意为“野猫”。
>
> 
> 未来计划通过kotlin发布全平台通用库，可通过JVM/JS/Native操作猫猫码。
> 代码移植为全平台没问题，native测试执行也没啥问题，但是我不知道怎么发布，所以。。再说吧。


### LICENSE 
see [LICENSE](./LICENSE) .
