<div align="center">
    <img src='./logo/CatCodeLogo@0,1x.png'/>
    <h3>
        - 😺 CatCode 😺 -
    </h3>
    <span>
        <a href="https://github.com/ForteScarlet/CatCode">github</a>
    </span> 
    &nbsp;&nbsp; | &nbsp;&nbsp;
    <span>
        <a href="https://gitee.com/ForteScarlet/CatCode">gitee</a>
    </span> <br />
    <small> &gt; 感谢simple-robot开发团队成员制作的猫猫logo &lt; </small>
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





> Neko : ねこ(猫)，意为“猫”。
>
> NoraNeko : のらねこ(野良猫)，意为“野猫”。
>
> 未来计划通过kotlin发布全平台通用库，可通过JVM/JS/Native操作猫猫码。
> 代码移植为全平台没问题，native测试执行也没啥问题，但是我不知道怎么发布，所以。。再说吧。

