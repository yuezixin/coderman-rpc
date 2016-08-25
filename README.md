# coderman-rpc 分布式服务框架
coderman-rpc是一个分布式服务框架，用于远程服务调用。coderman-rpc主要是学习rpc原理和一些技术实践，目前已经开始在写另一个RPC框架[Pandora](https://github.com/yuezixin/pandora)。
###环境要求
* Maven3+
* JDK1.8

###Package 说明
* core        工具类、bean、接口提供、工厂。
* registry    注册中心 服务注册发现，目前提供Zookeeper实现。可根据接口ServiceDiscovery、ServiceRegistry自行扩展。
* serialize   序列化支持 已实现Json、Hessain、Protostuff。
* transport   通讯层 已经实现Netty。

###使用
* 默认使用netty传输、Hessain序列化、Zookeeper为注册中心。
* Server 配置
![](https://raw.githubusercontent.com/yuezixin/coderman-rpc/master/doc/sping-server-config.png)
* Client 配置
* ![](https://raw.githubusercontent.com/yuezixin/coderman-rpc/master/doc/spring-client-config.png)

* demo可以直接跑的，有问题先去demo里面溜一圈~~~

###技术栈
* Zookeeper  注册中心，用的是zkClient
* Netty      tcp底层传输，过几天加个http的还是这东西
* protostuff、hessian、 jackson   用于序列化
* objenesis   被hessian、protostuff依赖  不使用构造方法直接调用newInstance创建对象实例

###后期完成
1. 新增路由模块，完成负载均衡；
2. 新增thrift序列化实现；
3. 新增http传输功能;
4. 服务治理coderman-rpc就不加了
##由于时间关系，后面大部分时间会写[Pandora](https://github.com/yuezixin/pandora)，但是这个也会更新但是时间不确定。QQ群：578127548##