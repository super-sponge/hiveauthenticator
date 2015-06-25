hiveserver2 定制权限
=================================================

##目的
    通过实现PasswdAuthenticationProvider接口，可以根据项目需要定制hiveserver2的认证。
##使用
### 编译打包
    mvn package 
    把打包的jar放入hive的lib目录
### 配置hive文件
    我们一配置用户为 user1，密码为passwd为例子
    <property>
        <name>hive.server2.authentication</name>
        <value>CUSTOM</value>
      </property>
    
    <property>
        <name>hive.server2.custom.authentication.class</name>
        <value>com.asiainfo.ctc.bigdata.AIPasswdAuthenticator</value>
    </property>
    <property>
        <name>hive.jdbc_passwd.auth.user1</name>
        <value>76A2173BE6393254E72FFA4D6DF1030A</value>
    </property>

### 验证
    beenline
    !connect jdbc:hive2://localhost:10000 user1 passwd org.apache.hive.jdbc.HiveDriver
    show tables;
    
    如果输入错误密码连接会报错
    !connect jdbc:hive2://localhost:10000 user1 errorpasswd org.apache.hive.jdbc.HiveDriver
### 工具使用
    生成MD5可以使用CodesUtil 程序，调用方式
    mvn exec:java -Dexec.mainClass="com.asiainfo.ctc.bigdata.utils.CodesUtil" -Dexec.args="passwd"
    或打包后执行
     java -jar hiveauthenticator-1.0-SNAPSHOT.jar pwd

##备注：
    在md5加密中可以使用
    maven 中添加
    <dependency>
    	<groupId>commons-codec</groupId>
    	<artifactId>commons-codec</artifactId>
    	<version>1.10</version>
    </dependency>

    import org.apache.commons.codec.digest.DigestUtils;
    String md5 = DigestUtils.md5Hex(passwd);
## 参考
获取详细信息，请参考 
* [Hive authentication] [1]
* [Setting HiveServer2] [2]

[1]: http://www.cloudera.com/content/cloudera/en/documentation/cdh4/v4-3-0/CDH4-Security-Guide/cdh4sg_topic_9_1.html "Hive Security"
[2]: https://cwiki.apache.org/confluence/display/Hive/Setting+up+HiveServer2 "Setting HiveServer2"