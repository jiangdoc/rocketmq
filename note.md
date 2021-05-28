# 源码学习
## 部署源码
### 设置启动参数
1. 首先启动namesvr 和 broker都需要指定配置
  - 在idea中添加启动环境变量：Environment variables：ROCKETMQ_HOME=源码路径/rocketmq/distribution
  - broker需要添加参数：arguments:-c /Users/jiangwenjie/IdeaProjects/rocketmq/distribution/conf/2m-noslave/broker-a.properties
2. namesvr 和 broker 的启动配置文件
    /rocketmq/distribution/conf/2m-noslave/broker-a.properties
    
    
# NameService
## 架构
1. NamesrvStartup 负责启动NameSrv服务
    - 创建NamesrvController实例
    - 初始化并启动NamesrvController
2. NamesrvController NameSrv服务的核心控制类，负责控制服务的启动和关闭，有三个核心方法：initialize、start、shutdown
    - initialize:初始化方法
        1. 加载配置信息，并将配置信息存储在KVConfigManager的configTable属性中
        2. 创建NettyRemotingServer(是NameSrv对外提供连接服务)，并创建用于执行NettyRemotingServer的线程池。
        3. 注册NameServer服务接受请求的处理类，默认采用DefaultRequestProcessor，所有的请求均由该处理类的processRequest方法来处理
        4. 每隔10秒，通过RouteInfoManager扫描brokerLiveTable。判断每一个Broker最近两分钟是否更新过。如果没有更新则把该Broker的Channel关闭，并清除相关数据。
    - start
        1. 启动NettyServer
        2. 启动FileWatchService
    - shutdown
        1. 关闭Netty server
        2. 关闭处理Netty Server的线程池
        3. 关闭定时任务线程池
3. kvconfig
    - KVConfigManager：主要负责读取或变更NameServer的配置属性，加载NamesrvConfig中配置的配置文件到内存
    - KVConfigSerializeWrapper

4. processor
    - DefaultRequestProcessor:NameServer服务接受请求的处理类，默认采用DefaultRequestProcessor，所有的请求均由该处理类的processRequest方法来处理
    
5. routeinfo
- RouteInfoManager作为NameServer数据的载体，记录Broker、Topic、QueueData等信息
    - Broker在启动时会将Broker信息、Topic信息、QueueData信息注册到所有的NameServer上，并和所有NameServer节点保持长连接，之后也会定时注册信息
    - Producer、Consumer也会和其中一个NameServer节点保持长连接，定时从NameServer中获取Topic路由信息