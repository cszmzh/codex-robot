# codex-robot
## 一. 项目说明

这是[Codex](https://www.515code.com/codex/)的部分源码。

一个基于Mirai的QQ机器人，使用Java+Maven实现部分功能。

官方文档：https://github.com/mamoe/mirai/blob/dev/docs/CoreAPI.md

文档是用Kotlin描述的，可以参考着这个项目转换为Java代码。

## 二. 核心模块

本项目中，入口代码为`robot/Robot.class`文件，我已经配置好Maven，修改完代码后直接Install即可打成jar包（会出现两个jar包，大的那个是带完整依赖的）。

handler模块负责监听各种消息，并进行反馈，详见代码。

service模块则进行一些网络请求，也可以编写一些业务逻辑。

## 三. 其他功能

1. 使用Selenium对游览器界面进行截图，文章参考：https://www.515code.com/posts/d2c1m6j5/

2. 定时群发信息功能（TimeService）

3. 一些特殊消息（如戳一戳）的实现可以参考官方文档。

## 四. 启动

1. 在`RobotConfig`中配置QQ账号/密码。
2. 在`IDConfig`配置群号等信息。
3. 配置`Robot`中初始化机器人的相关信息。
4. 运行`Robot`的main函数（如果在服务器上运行要解除QQ号设备锁）。

