
spring-boot + quartz + freemaker +bootstrap

支持在web页面中添加任务，暂停任务，继续执行任务，修改任务执行周期，测试任务，删除任务，以及quartz集群模式。
需要添加的任务需要实现Job接口，并有spring管理bean，例如DemoService,DemoService2
这个项目也集成了spring-data,可以用来写业务。

说明：

 1，数据库脚本：resources/tables_mysql.sql

 2,配置application.properties和quartz.properties中的数据源信息

 3，启动应用 StartWebApplication

 4,访问http://127.0.0.1:8098/

 5,登录，username:admin   password:1

 6,添加任务，
    serviceName:demoService
    description:test
    CronExpression:0/30 * * * * ?

（quartz的集群模式需要服务器进行时间同步）
 1,Never run clustering on separate machines,
 unless their clocks are synchronized using some form of time-sync service (daemon)
 that runs very regularly (the clocks must be within a second of each other).