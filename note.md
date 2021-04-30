# 源码学习
## 部署源码
### 设置启动参数
1. 首先启动namesvr 和 broker都需要指定配置
  - 在idea中添加启动环境变量：Environment variables：ROCKETMQ_HOME=源码路径/rocketmq/distribution
  - broker需要添加参数：arguments:-c /Users/jiangwenjie/IdeaProjects/rocketmq/distribution/conf/2m-noslave/broker-a.properties
2. namesvr 和 broker 的启动配置文件
    /rocketmq/distribution/conf/2m-noslave/broker-a.properties