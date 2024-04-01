# xfg-frame-archetype - DDD 脚手架
# https://bugstack.cn/md/road-map/ddd-archetype.html 脚手架介绍
123.57.229.124
app：写启动配置
domain：写具体业务逻辑，抽奖领域，用户的积分领域，兑换消费领域；定义接口但不做具体实现，通过注入的方式实现
infrastructure：技术层，提供数据服务，mysql，redis，通过依赖倒置的方式让domain去查询
trigger：触发器：接收触发动作，如http请求，mq请求
types：存常量，枚举，异常

#domain是被引用的，定义一个标准，被infrastructure和trigger引用
app层引用infrastructure和trigger

领域建模--数据结构--数据库设计
1.分析抽奖的行为逻辑，设计抽奖策略领域模型和对应的库表结构