# APNs_Push_Server
依赖javapns库
利用swing开发的一款图形化的iOS推送服务器

IDEA工程

需要在src/main/resources下放置推送证书,并更名为：
development证书：com.qihu.malljd_push_dev.p12
distribution证书：com.qihu.malljd_push_dis.p12

选中src/main/java/Application  ▶️Run即可运行

程序会自动记录上次发送的消息内容及DeviceToken、推送环境，不需要重新输入。

如果打jar包使用，需要在jar包所在目录下放置上述两个环境的证书

适合用于测试iOS推送。
