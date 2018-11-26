# JAdmin
JAdmin 是一个基于Java语言的快速开发平台，数据库表创建后，只需5分钟就能开发一套后台管理系统。无需JSP，一个Controller，一个JavaBean，就可以实现数据的增删改查。 文本框、单选、多选、图片组件、日期组件、富文本编辑器、坐标选取等等……一个注解轻松搞定。友好的代码结构及注释，便于阅读及开发。  
  
JAdmin环境搭建：  
第一步：JAdmin 基于Spring Boot，建议大家使用STS（官方开发工具）或者IDEA（均可开发Spring Boot项目），或者你也可以在Eclipse上安装Spring Boot插件；  
第二步：JAdmin 使用了Lombook插件（Lombook可以减少很多重复代码的书写，比如说getter/setter的编写），建议用户安装Lombook插件。如果无法安装Lombook，可以下载 JAdmin无Lombook版；  
第三步：JAdmin 必须要有数据库支撑，JAdmin标准版使用的是Mysql数据库。  
  
体验JAdmin：  
第一步：打开IDE，导入JAdmin项目，JAdmin源码不包含jar包，项目初始化时，maven需要时间下载所需jar包，请耐心等待；
第二步：导入mysql基础表，数据库文件是doc/jAdmin.sql；  
第三步：修改application.yml的数据库连接；  
第四步：打开WebApplication类，右键Run As Java Application；  
第五步：打开浏览器，访问：http://127.0.0.1:8080，用户名：admin，密码：111111。  

JAdmin 基于以下开源框架，再次感谢：  
核心框架：Spring Boot 2.0.3  
视图框架：Spring MVC 5.0.7  
持久层框架：Hibernate 6.0.10  
定时器：Quartz 2.3  
日志管理：Logback 1.2.3、Log4j  
前端框架：Hui.admin V3.1 （感谢jQuery、layer、laypage、Validform、UEditor、My97DatePicker、iconfont、Datatables、WebUploaded、icheck、highcharts、bootstrap-Switch）