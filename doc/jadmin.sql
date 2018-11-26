/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50558
Source Host           : localhost:3306
Source Database       : jadmin

Target Server Type    : MYSQL
Target Server Version : 50558
File Encoding         : 65001

Date: 2018-11-26 15:58:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `configId` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(128) NOT NULL COMMENT '名字',
  `code` varchar(128) NOT NULL COMMENT '编号',
  `coValue` text NOT NULL COMMENT '值',
  `isOpen` char(1) NOT NULL COMMENT '是否允许在界面中编辑 1-是，0-否',
  `operateTime` varchar(19) NOT NULL COMMENT '操作时间',
  `operatorId` varchar(32) NOT NULL COMMENT '操作人',
  `billStatus` char(1) NOT NULL COMMENT '状态 1正常 0禁用',
  `memo` varchar(512) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`configId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统属性表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('4028802a53fa3a750153fa42a81f0001', '默认分页数量', 'DEF_PAGESIZE', '10', '1', '2018-10-30 10:48:14', 'admin', '1', '');
INSERT INTO `sys_config` VALUES ('402880df66e69f0f0166e6ac51e5000b', '密码多久强制修改一次', 'PSCHANGE_MONTH', '-1', '1', '2018-11-06 09:39:04', 'admin', '1', '单位为月，如果为-1，不强制修改，如果为正数，{PSCHANGE_MONTH}月份修改一次，并且第一次登陆的时候，需要强制修改密码。');
INSERT INTO `sys_config` VALUES ('4028818451b28de40151b290f7bd0002', '项目名称', 'DEF_PROJECT_NAME', 'JAdmin后台快速开发框架', '1', '2017-11-26 08:50:23', 'admin', '1', '');
INSERT INTO `sys_config` VALUES ('402881ef4f4f615d014f4f6ab3530001', '用户默认密码', 'DEF_PASSWORD', '111111', '1', '2017-11-26 10:10:10', 'admin', '1', '用户默认密码');

-- ----------------------------
-- Table structure for `sys_dictinfo`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictinfo`;
CREATE TABLE `sys_dictinfo` (
  `dictinfoId` varchar(32) NOT NULL COMMENT '字典ID',
  `dictkindId` varchar(32) DEFAULT NULL COMMENT '字典父ID',
  `dictinfoFid` varchar(32) DEFAULT NULL COMMENT '如果是2级以上的，需要fid',
  `name` varchar(1024) NOT NULL COMMENT '字典名字',
  `code` varchar(128) DEFAULT NULL COMMENT '字典编号',
  `sort` int(10) DEFAULT '0' COMMENT '排序，小的在前面',
  `level` char(1) NOT NULL DEFAULT '1' COMMENT '级别，默认1级，二级菜单的话是二级',
  `billStatus` char(1) NOT NULL COMMENT '状态 1正常 0禁用',
  `memo` varchar(512) DEFAULT NULL COMMENT '字典描述',
  PRIMARY KEY (`dictinfoId`),
  KEY `dictinfoId` (`dictinfoId`),
  KEY `FK_424ee705f050418c9e21d86f307` (`dictkindId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典属性表';

-- ----------------------------
-- Records of sys_dictinfo
-- ----------------------------
INSERT INTO `sys_dictinfo` VALUES ('1000', '100', null, '男', '1', '0', '1', '1', '');
INSERT INTO `sys_dictinfo` VALUES ('1001', '100', null, '女', '2', '1', '1', '1', '');
INSERT INTO `sys_dictinfo` VALUES ('1002', '101', null, '启用', '1', '0', '1', '1', null);
INSERT INTO `sys_dictinfo` VALUES ('1003', '101', null, '停用', '0', '1', '1', '1', null);
INSERT INTO `sys_dictinfo` VALUES ('1004', '103', null, '人工添加', '0', '0', '1', '1', null);
INSERT INTO `sys_dictinfo` VALUES ('1005', '103', null, '动态获取', '1', '0', '1', '1', null);
INSERT INTO `sys_dictinfo` VALUES ('1006', '104', null, '单选', '1', '0', '1', '1', '');
INSERT INTO `sys_dictinfo` VALUES ('1007', '104', null, '多选', '2', '0', '1', '1', '');
INSERT INTO `sys_dictinfo` VALUES ('4028818b53e5f7930153e5f9ffb00006', '4028818b53e5f7930153e5f9c5450004', null, '是', '1', '1', '1', '1', '');
INSERT INTO `sys_dictinfo` VALUES ('4028818b53e5f7930153e5fa1d620008', '4028818b53e5f7930153e5f9c5450004', null, '否', '0', '2', '1', '1', '');
INSERT INTO `sys_dictinfo` VALUES ('4028f681658892890165889d66ce0012', '4028f681658892890165889701830001', null, '执行中', '5', '6', '1', '1', '');
INSERT INTO `sys_dictinfo` VALUES ('4028f681658892890165889df5e40015', '4028f681658892890165889701830001', null, '错误', '4', '5', '1', '1', '');
INSERT INTO `sys_dictinfo` VALUES ('4028f681658892890165889e41150017', '4028f681658892890165889701830001', null, '完成', '3', '4', '1', '1', '');
INSERT INTO `sys_dictinfo` VALUES ('4028f681658892890165889ef1190019', '4028f681658892890165889701830001', null, '暂停', '2', '3', '1', '1', '');
INSERT INTO `sys_dictinfo` VALUES ('4028f681658892890165889f246d001b', '4028f681658892890165889701830001', null, '正常', '1', '2', '1', '1', '');
INSERT INTO `sys_dictinfo` VALUES ('4028f681658892890165889f6251001d', '4028f681658892890165889701830001', null, '不存在', '0', '1', '1', '1', '');
INSERT INTO `sys_dictinfo` VALUES ('4028f68166519330016651a0fbdb0002', '4028f68166519330016651a0a4cc0000', null, '启用', '1', '1', '1', '1', '');
INSERT INTO `sys_dictinfo` VALUES ('4028f68166519330016651a142ff0004', '4028f68166519330016651a0a4cc0000', null, '禁用', '0', '2', '1', '1', '');

-- ----------------------------
-- Table structure for `sys_dictkind`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictkind`;
CREATE TABLE `sys_dictkind` (
  `dictkindId` varchar(32) NOT NULL COMMENT '字典种类ID',
  `code` varchar(128) NOT NULL COMMENT '种类编号',
  `name` varchar(128) NOT NULL COMMENT '字典种类名称',
  `isDynamic` char(1) NOT NULL DEFAULT '0' COMMENT '是否是动态的，动态查询数据库的为1，否则0',
  `dynSql` varchar(1024) DEFAULT NULL COMMENT '如果字典的属性值不是固定的，就查询sql',
  `type` char(1) NOT NULL DEFAULT '1' COMMENT '字典类别类型，详见数据字典',
  `isOpen` char(1) NOT NULL COMMENT '是否允许在界面中编辑 1-是，0-否',
  `operateTime` char(19) NOT NULL COMMENT '创建时间',
  `billStatus` char(1) NOT NULL COMMENT '状态 1正常 0禁用',
  `memo` varchar(512) DEFAULT NULL COMMENT '字典描述',
  PRIMARY KEY (`dictkindId`),
  KEY `dictkindId` (`dictkindId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典种类表';

-- ----------------------------
-- Records of sys_dictkind
-- ----------------------------
INSERT INTO `sys_dictkind` VALUES ('100', 'sex', '性别', '0', '', '1', '1', '2015-08-25 14:06:16', '1', '性别分类');
INSERT INTO `sys_dictkind` VALUES ('101', 'billStatus', '是否启用', '0', '', '1', '1', '2015-08-25 14:06:16', '1', '是否启用');
INSERT INTO `sys_dictkind` VALUES ('102', 'userRole', '用户角色', '1', 'SELECT roleId id, roleName name FROM sys_role WHERE billStatus = 1', '1', '1', '2015-08-25 14:06:16', '1', '');
INSERT INTO `sys_dictkind` VALUES ('103', 'isDynamic', '字典属性类别', '0', null, '1', '1', '2015-08-25 14:06:16', '1', null);
INSERT INTO `sys_dictkind` VALUES ('104', 'dictkindType', '字典种类类别', '0', '', '1', '1', '2015-08-25 14:06:16', '1', '');
INSERT INTO `sys_dictkind` VALUES ('106', 'org', '机构列表（有权限控制）', '1', 'SELECT orgId rId, name, seq id, orgFSeq pId FROM sys_org WHERE billStatus = 1 and isDelete = 0', '1', '1', '2015-08-26 02:45:23', '1', '动态获取自己和子机构的orgId   and seq like \'${org.seq}%\'');
INSERT INTO `sys_dictkind` VALUES ('108', 'orgFSeq', '所属父机构', '1', 'SELECT seq id, name FROM sys_org WHERE billStatus = 1', '1', '1', '2015-09-02 16:02:22', '1', 'wu');
INSERT INTO `sys_dictkind` VALUES ('120', 'userDisRole', '可非配角色', '1', 'SELECT roleId id, roleName name FROM sys_role WHERE billStatus = 1', '2', '1', '2015-08-25 14:06:16', '1', '');
INSERT INTO `sys_dictkind` VALUES ('4028803a665678420166567dce8f0008', 'userName', '创建人', '1', 'SELECT userId id, name name FROM sys_user WHERE billStatus = 1', '1', '1', '2018-10-09 09:42:57', '1', '');
INSERT INTO `sys_dictkind` VALUES ('4028818b53e5f7930153e5f9c5450004', 'yesNo', '是否', '0', '', '1', '1', '2016-04-05 18:31:04', '1', '');
INSERT INTO `sys_dictkind` VALUES ('4028f681658892890165889701830001', 'SchedulerState', '调度状态', '0', '', '1', '1', '2018-08-30 10:08:42', '1', '');
INSERT INTO `sys_dictkind` VALUES ('4028f68166519330016651a0a4cc0000', 'quartzSwitch', '调度开关', '0', '', '1', '1', '2018-10-08 11:02:54', '1', '');

-- ----------------------------
-- Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `logId` varchar(32) NOT NULL COMMENT 'ID',
  `orgId` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `userId` char(128) DEFAULT NULL COMMENT '用户ID',
  `url` varchar(1024) DEFAULT NULL COMMENT '请求的url',
  `type` varchar(128) DEFAULT NULL COMMENT '类型',
  `content` longtext CHARACTER SET utf8mb4 NOT NULL COMMENT '日志内容',
  `clientIp` varchar(64) DEFAULT NULL COMMENT '用户操作ip',
  `level` char(1) DEFAULT NULL COMMENT '级别',
  `operateTime` varchar(19) DEFAULT NULL COMMENT '操作时间',
  `memo` varchar(512) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_memorandum`
-- ----------------------------
DROP TABLE IF EXISTS `sys_memorandum`;
CREATE TABLE `sys_memorandum` (
  `memorandumId` varchar(32) NOT NULL COMMENT '主键',
  `content` text COMMENT '备忘签内容',
  `isDelete` varchar(1) NOT NULL COMMENT '是否删除 1-是 0-否',
  `operateTime` varchar(19) NOT NULL COMMENT '操作时间',
  `operatorId` varchar(32) NOT NULL COMMENT '操作人',
  PRIMARY KEY (`memorandumId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='备忘笺';

-- ----------------------------
-- Records of sys_memorandum
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_org`
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `orgId` varchar(32) NOT NULL COMMENT '主键',
  `code` varchar(128) NOT NULL COMMENT '编号',
  `name` varchar(128) NOT NULL COMMENT '名字',
  `fullname` varchar(1024) DEFAULT NULL COMMENT '全称',
  `seq` varchar(1024) NOT NULL COMMENT '序列号',
  `orgFSeq` varchar(1024) NOT NULL COMMENT '所属父seq',
  `isDelete` char(1) NOT NULL COMMENT '是否删除',
  `operateTime` varchar(19) NOT NULL COMMENT '操作时间',
  `operatorId` varchar(32) NOT NULL COMMENT '操作人',
  `billStatus` char(1) NOT NULL COMMENT '是否可用',
  `memo` varchar(512) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`orgId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES ('1', '1', '管理部', '管理部', '1.', '', '', '2018-11-19 14:27:56', 'admin', '1', '1');
INSERT INTO `sys_org` VALUES ('4028803a65c7dffd0165c7e0c39c0001', '4', '二级部门', '二级部门', '1.4.', '1.', '', '2018-09-11 17:05:20', 'admin', '1', '');
INSERT INTO `sys_org` VALUES ('4028803a65e549160165e5492dfb0000', '5', '三级部门', '三级部门', '1.4.5.', '1.4.', '', '2018-09-17 10:08:23', 'admin', '1', '');
INSERT INTO `sys_org` VALUES ('4028803a65e549160165e549d6c00002', '6', '三级部门2', '三级部门2', '1.4.6.', '1.4.', '', '2018-09-17 10:09:06', 'admin', '1', '');
INSERT INTO `sys_org` VALUES ('4028803a65e549160165e581178f0004', '7', '四级部门', '四级部门', '1.4.5.7.', '1.4.5.', '', '2018-09-17 11:09:27', 'admin', '1', '');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `roleId` varchar(32) NOT NULL COMMENT '角色ID',
  `roleName` varchar(128) NOT NULL COMMENT '角色名称',
  `roleType` char(1) NOT NULL COMMENT '1全局型角色、2应用级角色',
  `operateTime` varchar(19) DEFAULT NULL COMMENT '操作时间',
  `operatorId` varchar(32) DEFAULT NULL COMMENT '操作人',
  `billStatus` char(1) NOT NULL COMMENT '状态 1正常 0禁用',
  `memo` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`roleId`),
  KEY `roleId` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('0', '超级管理员', '1', '2018-09-11 16:38:25', 'admin', '1', null);

-- ----------------------------
-- Table structure for `sys_role_module`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_module`;
CREATE TABLE `sys_role_module` (
  `roleModuleId` varchar(32) NOT NULL COMMENT '角色ID',
  `roleId` varchar(32) NOT NULL COMMENT '角色ID',
  `moduleMenu1` varchar(128) NOT NULL COMMENT '一级菜单名字',
  `moduleMenu2` varchar(128) DEFAULT NULL COMMENT '二级菜单名字',
  `modulePage` varchar(128) NOT NULL COMMENT '页面名字',
  `moduleButton` varchar(128) NOT NULL COMMENT '标题名字',
  `billStatus` char(1) NOT NULL COMMENT '状态 1正常 0禁用',
  PRIMARY KEY (`roleModuleId`),
  KEY `sys_role_module_ibfk_1` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of sys_role_module
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_task`
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task` (
  `taskId` varchar(32) NOT NULL COMMENT '主键id',
  `className` varchar(256) NOT NULL COMMENT '包名+类名',
  `cron` varchar(256) DEFAULT NULL COMMENT 'cron表达式',
  `initialDelay` int(32) DEFAULT NULL COMMENT '延迟执行 单位秒',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `serverIp` varchar(32) NOT NULL COMMENT '服务器ip',
  `billStatus` char(1) DEFAULT NULL COMMENT '调度开关，详见数据词典',
  `isDynamic` char(1) DEFAULT NULL COMMENT '是否可修改 1-是 0-否',
  `isEdited` char(1) DEFAULT NULL COMMENT '是否已经在界面中编辑过',
  PRIMARY KEY (`taskId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统调度任务表';

-- ----------------------------
-- Records of sys_task
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_task_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_task_log`;
CREATE TABLE `sys_task_log` (
  `logId` varchar(32) NOT NULL COMMENT '主键',
  `className` varchar(256) NOT NULL COMMENT '包名+类名',
  `serverIp` varchar(32) NOT NULL COMMENT '服务器ip',
  `content` varchar(2048) NOT NULL COMMENT '内容',
  `startTime` varchar(19) NOT NULL COMMENT '开始时间',
  `endTime` varchar(19) NOT NULL COMMENT '结束时间',
  `executeTime` int(11) NOT NULL COMMENT '耗时 单位为秒',
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调度任务执行记录表';

-- ----------------------------
-- Records of sys_task_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `userId` varchar(32) NOT NULL COMMENT '用户ID',
  `orgId` varchar(32) DEFAULT NULL COMMENT '所属机构',
  `roleId` varchar(32) NOT NULL COMMENT '所属角色',
  `account` varchar(128) NOT NULL COMMENT '账号',
  `name` varchar(128) NOT NULL COMMENT '名字',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `lastLoginIp` varchar(32) DEFAULT NULL COMMENT '上次登录ip',
  `lastLoginTime` varchar(19) DEFAULT NULL COMMENT '上次登录时间',
  `loginCount` int(10) NOT NULL DEFAULT '0' COMMENT '登录次数',
  `psChangeTime` varchar(19) DEFAULT NULL COMMENT '密码修改时间',
  `isDelete` char(1) NOT NULL,
  `operateTime` varchar(19) NOT NULL COMMENT '操作时间',
  `operatorId` varchar(32) NOT NULL COMMENT '操作人',
  `billStatus` char(1) NOT NULL COMMENT '是否可用',
  `memo` varchar(512) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`userId`),
  KEY `FKii60dd8hajdmbm0uvkkporcbl` (`orgId`),
  KEY `FK9s2sqg6p1req126agyn1sfeiy` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('402881ee4ff9acb3014ff11161111004', '1', '0', 'admin', '系统管理员', 'pnliiidgkneicjmb', '1', '127.0.0.1', '2018-11-26 08:35:41', '1', '2018-11-21 09:27:52', '0', '2018-11-21 08:21:34', 'admin', '1', '');
