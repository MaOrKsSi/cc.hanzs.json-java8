#cc.hanzs.json
##.
###JSON介绍
JSON，全称JavaScript Object Notation。 是一种轻量级的数据交换格式。它基于ECMAScript的一个子集。 JSON采用完全独立于语言的文本格式，具有短小精悍、易于阅读的特点，这些特性使JSON成为理想的数据交换格式，常用于网路传输或软件之间的数据交换。因此，高效处理JSON是大家孜孜以求的。

cc.hanzs.json是一款简约的JSON处理工具，包含了绝大多数所需功能，力求体积小、速度快、功能全。该工具改编自[org.json](https://github.com/stleary/JSON-java)，其中JSONTokener.java还留有很多原作者所写代码的影子，其他文档则面目全非了，同时增加了JSONPath，用以处理路径以及循环引用。
##.
###所用编辑器
NetBeans
##.
###遵循协议
为尽量保障作者的辛劳与顾客的使用，此项目遵循LGPLv3协议：<BR>`1、对软件本身可以修改，但任何人不得将软件本身及修改版用于商业行为。`<BR>`2、任何人对软件的使用（调用、封装，包括修改後的）可免费用于商业软件。`<BR>`3、对软件的技术支持，技术支持者可收取费用。`
##.
###必要文档说明
`src/优化入口.java`即是使用样例，也是代码优化时，需要保留的对外方法。

`cc.hanzs.json.7z`解压後是编译并优化好的jar文档，可直接用于程序设计。
##.
###功能介绍
处理了路径问题。1、这里使用了大家熟悉的操作系统路径表达方式（注意与windows不同，分隔符不是“\”而是“/”）。 2、与文本的区别是，路径不用引号引起来。
举例：`{a:'sdfdsfa',c:{c:../},d:./a}`

cc.hanzs.json.JSONObject.d副本();//建立JSONObject。此处未采用new方式建立，而是使用副本技术，优点是加快了运行速度，缺点是占用了内存。对于使用频率非常高的API，优点远远大于缺点。

cc.hanzs.json.JSONObject.d副本("{a:'sdfdsfa',c:{c:../},d:./a}");//建立并反序列化文本

#####以上两个是静态过程，以下是获取对象後，对象所能执行的过程，此处仅说明以下特殊情况。

JSONObject.getPath(String);//获取路径。原json处理是无路径处理的功能，此为新增功能。

JSONObject.clear();//移除下级对象以及移除下级的下级对象，如此递归称之为清理。

JSONObject.removeall();//移除所有下级对象，不递归下级对象。

JSONObject.toString();//除增加JSONPath外，也考虑到了循环引用。循环引用在此被处理成JSONPath，既保障数据的完整，又解决循环引用造成的死循环。

<BR>

JSONArray.getPath(String);//获取路径。原json处理是无路径处理的功能，此为新增功能。

JSONArray.clear();//移除下级对象以及移除下级的下级对象，如此递归称之为清理。

JSONArray.removeall();//移除所有下级对象，不递归下级对象。

JSONArray.toString();//除增加JSONPath外，也考虑到了循环引用。循环引用在此被处理成JSONPath，既保障数据的完整，又解决循环引用造成的死循环。

<BR>

JSONPath.get();//获取path指向的内容或对象。