/*
Navicat MySQL Data Transfer

Source Server         : 101.200.61.19
Source Server Version : 50718
Source Host           : 101.200.61.19:3306
Source Database       : db

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-07-16 23:08:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_api_access_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_api_access_info`;
CREATE TABLE `tb_api_access_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apiName` varchar(255) NOT NULL,
  `parameter` text,
  `result` text,
  `resultContent` text,
  `errorReason` text,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `addedTime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_api_access_info
-- ----------------------------

-- ----------------------------
-- Table structure for tb_auth_function
-- ----------------------------
DROP TABLE IF EXISTS `tb_auth_function`;
CREATE TABLE `tb_auth_function` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `pid` int(9) NOT NULL COMMENT '父节点',
  `funName` varchar(30) NOT NULL COMMENT '功能名称',
  `funUrl` varchar(100) DEFAULT NULL COMMENT '功能地址',
  `isGroup` int(1) NOT NULL DEFAULT '0' COMMENT '是否分组',
  `iconCls` varchar(10) DEFAULT NULL COMMENT '图标名称',
  `funOrder` int(11) DEFAULT '0' COMMENT '排序字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_auth_function
-- ----------------------------
INSERT INTO `tb_auth_function` VALUES ('2', '0', '用户管理', '', '0', null, '1');
INSERT INTO `tb_auth_function` VALUES ('3', '0', '系统管理', '', '0', null, '2');
INSERT INTO `tb_auth_function` VALUES ('4', '100', '用户列表', 'user/userList.do', '1', null, '2');
INSERT INTO `tb_auth_function` VALUES ('5', '63', '功能管理', 'void();', '1', null, '1');
INSERT INTO `tb_auth_function` VALUES ('6', '5', '功能列表初始化', 'fun/getFunTableInit.do', '1', null, '1');
INSERT INTO `tb_auth_function` VALUES ('7', '5', '查询功能表格json', 'fun/getFunTable.do', '1', null, '2');
INSERT INTO `tb_auth_function` VALUES ('8', '5', '添加或者编辑功能初始化', 'fun/addOrEditFunInit.do', '1', null, '3');
INSERT INTO `tb_auth_function` VALUES ('9', '5', '添加或者编辑功能', 'fun/addOrEditFun.do', '1', null, '4');
INSERT INTO `tb_auth_function` VALUES ('10', '5', '查询所有功能的树形json', 'fun/getFunTreeJson.do', '1', null, '5');
INSERT INTO `tb_auth_function` VALUES ('11', '5', '查询当前节点和下面子节点的combobox树', 'fun/getCurAndNextOneNode.do', '1', null, '6');
INSERT INTO `tb_auth_function` VALUES ('39', '5', '删除当前节点和下面所有的子节点', 'fun/deleteAllChildrenNodebyId.do', '1', '', '7');
INSERT INTO `tb_auth_function` VALUES ('61', '2', '测试', 'test', '1', '1', '2');
INSERT INTO `tb_auth_function` VALUES ('63', '3', '权限管理', 'void();', '0', '', '1');
INSERT INTO `tb_auth_function` VALUES ('64', '63', '菜单管理', 'void();', '0', '', '2');
INSERT INTO `tb_auth_function` VALUES ('66', '64', '查询菜单树html', 'menu/getMenuTree.do', '1', '', '1');
INSERT INTO `tb_auth_function` VALUES ('67', '64', '查询菜单树形表格初始化', 'menu/getMenuTableInit.do', '1', '', '2');
INSERT INTO `tb_auth_function` VALUES ('68', '64', '查询菜单树形表格json数据', 'menu/getMenuTable.do', '1', '', '3');
INSERT INTO `tb_auth_function` VALUES ('69', '64', '添加或者编辑菜单初始化', 'menu/addOrEditMenuInit.do', '1', '', '4');
INSERT INTO `tb_auth_function` VALUES ('70', '64', '添加或者编辑菜单', 'menu/addOrEditMenu.do', '1', '', '5');
INSERT INTO `tb_auth_function` VALUES ('71', '64', '查询菜单树comboboxjson', 'menu/getMenuTreeJson.do', '1', '', '7');
INSERT INTO `tb_auth_function` VALUES ('72', '64', '查询当前菜单节点的下一级子节点', 'menu/getCurAndNextOneNode.do', '1', '', '8');
INSERT INTO `tb_auth_function` VALUES ('73', '64', '删除当前菜单节点及下面的所有子节点', 'menu/deleteAllChildrenNodebyId.do', '1', '', '6');
INSERT INTO `tb_auth_function` VALUES ('75', '63', '角色权限管理', 'void();', '0', '', '3');
INSERT INTO `tb_auth_function` VALUES ('76', '75', '角色权限管理初始化', 'roleAuth/roleAuthManageInit.do', '1', '', '1');
INSERT INTO `tb_auth_function` VALUES ('77', '3', '资源管理', 'void();', '0', '', '2');
INSERT INTO `tb_auth_function` VALUES ('78', '77', '图标管理', 'void();', '0', '', '1');
INSERT INTO `tb_auth_function` VALUES ('79', '78', '图标列表初始化', 'resource/iconListInit.do', '1', '', '1');
INSERT INTO `tb_auth_function` VALUES ('80', '78', '图标列表数据', 'resource/iconList.do', '1', '', '2');
INSERT INTO `tb_auth_function` VALUES ('81', '75', '角色列表', 'roleAuth/roleList.do', '1', '', '2');
INSERT INTO `tb_auth_function` VALUES ('82', '75', '根据角色查询权限树', 'roleAuth/getAllAuthByRole.do', '1', '', '3');
INSERT INTO `tb_auth_function` VALUES ('83', '75', '添加或者编辑角色初始化', 'roleAuth/addOrEditRoleInit.do', '1', '', '4');
INSERT INTO `tb_auth_function` VALUES ('84', '75', '添加或者编辑角色', 'roleAuth/addOrEditRole.do', '1', '', '5');
INSERT INTO `tb_auth_function` VALUES ('85', '75', '删除角色', 'roleAuth/deleteRole.do', '1', '', '6');
INSERT INTO `tb_auth_function` VALUES ('86', '75', '更新角色权限', 'roleAuth/updateRoleAuth.do', '1', '', '7');
INSERT INTO `tb_auth_function` VALUES ('87', '78', '添加或者编辑图标初始化', 'resource/addOrEditIconInit.do', '1', '', '3');
INSERT INTO `tb_auth_function` VALUES ('88', '78', '添加或者编辑图标', 'resource/addOrEditIcon.do', '1', '', '4');
INSERT INTO `tb_auth_function` VALUES ('89', '78', '删除图标', 'resource/deleteIcon.do', '1', '', '5');
INSERT INTO `tb_auth_function` VALUES ('90', '3', '字典管理', 'void();', '0', '', '3');
INSERT INTO `tb_auth_function` VALUES ('91', '90', '根据key查询字典', 'dict/getDictByKey.do', '1', '', '1');
INSERT INTO `tb_auth_function` VALUES ('92', '3', '文件管理', 'void();', '0', '', '4');
INSERT INTO `tb_auth_function` VALUES ('93', '92', '文件上传', 'resource/fileUpload.do', '1', '', '1');
INSERT INTO `tb_auth_function` VALUES ('95', '0', '测试', '/', '0', '', '4');
INSERT INTO `tb_auth_function` VALUES ('96', '95', 'api测试', 'test/apiTestInit.do', '1', '', '1');
INSERT INTO `tb_auth_function` VALUES ('97', '0', '商品管理', '/', '0', '', '3');
INSERT INTO `tb_auth_function` VALUES ('98', '97', '商品列表初始化', 'goods/goodsListInit.do', '1', '', '1');
INSERT INTO `tb_auth_function` VALUES ('99', '97', '商品列表', 'goods/goodsList.do', '1', '', '2');
INSERT INTO `tb_auth_function` VALUES ('100', '2', '用户管理', '/', '0', '', '1');
INSERT INTO `tb_auth_function` VALUES ('101', '100', '用户列表初始化', 'user/selectListInit.do', '1', '', '1');

-- ----------------------------
-- Table structure for tb_auth_menu_function
-- ----------------------------
DROP TABLE IF EXISTS `tb_auth_menu_function`;
CREATE TABLE `tb_auth_menu_function` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `pid` int(9) DEFAULT NULL,
  `menuName` varchar(30) NOT NULL,
  `funId` int(9) NOT NULL,
  `isGroup` int(1) NOT NULL DEFAULT '0',
  `menuOrder` int(9) NOT NULL,
  `iconCls` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_auth_menu_function
-- ----------------------------
INSERT INTO `tb_auth_menu_function` VALUES ('1', '0', '用户管理', '0', '0', '1', 'icon-user');
INSERT INTO `tb_auth_menu_function` VALUES ('2', '0', '系统管理', '0', '0', '2', 'icon-system');
INSERT INTO `tb_auth_menu_function` VALUES ('3', '1', '用户列表', '101', '1', '1', 'icon-userList');
INSERT INTO `tb_auth_menu_function` VALUES ('4', '11', '功能管理', '6', '1', '1', 'icon-fun');
INSERT INTO `tb_auth_menu_function` VALUES ('5', '1', '用户角色管理', '4', '1', '2', 'icon-print');
INSERT INTO `tb_auth_menu_function` VALUES ('6', '11', '菜单管理', '67', '1', '2', 'icon-menu');
INSERT INTO `tb_auth_menu_function` VALUES ('7', '9', '测试菜单', '61', '1', '1', '');
INSERT INTO `tb_auth_menu_function` VALUES ('8', '7', '菜单一', '4', '1', '1', '');
INSERT INTO `tb_auth_menu_function` VALUES ('10', '11', '角色权限管理', '76', '1', '3', 'icon-role_auth');
INSERT INTO `tb_auth_menu_function` VALUES ('11', '2', '权限管理', '61', '0', '1', 'icon-auth');
INSERT INTO `tb_auth_menu_function` VALUES ('12', '2', '资源管理', '61', '0', '2', 'icon-resource');
INSERT INTO `tb_auth_menu_function` VALUES ('13', '12', 'icon图标管理', '79', '1', '1', 'icon-icon');
INSERT INTO `tb_auth_menu_function` VALUES ('14', '2', '字典管理', '61', '1', '3', 'icon-dict');
INSERT INTO `tb_auth_menu_function` VALUES ('15', '0', '测试管理', '0', '0', '7', 'icon-test');
INSERT INTO `tb_auth_menu_function` VALUES ('16', '15', 'api测试', '96', '1', '1', 'icon-api');
INSERT INTO `tb_auth_menu_function` VALUES ('17', '0', '商品管理', '98', '0', '3', 'icon-goods');
INSERT INTO `tb_auth_menu_function` VALUES ('18', '0', '内容管理', '0', '0', '4', 'icon-content');
INSERT INTO `tb_auth_menu_function` VALUES ('19', '0', '统计管理', '0', '0', '5', 'icon-statis');
INSERT INTO `tb_auth_menu_function` VALUES ('20', '0', '运营管理', '0', '0', '6', 'icon-operate');

-- ----------------------------
-- Table structure for tb_auth_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_auth_role`;
CREATE TABLE `tb_auth_role` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(20) NOT NULL,
  `roleDes` varchar(100) DEFAULT NULL,
  `roleStatus` int(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_auth_role
-- ----------------------------
INSERT INTO `tb_auth_role` VALUES ('2', '测试角色一', '1', '1');
INSERT INTO `tb_auth_role` VALUES ('3', '测试角色二', '测试角色二', '1');

-- ----------------------------
-- Table structure for tb_auth_role_function
-- ----------------------------
DROP TABLE IF EXISTS `tb_auth_role_function`;
CREATE TABLE `tb_auth_role_function` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `roleId` int(9) NOT NULL,
  `funId` int(9) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_auth_role_function
-- ----------------------------
INSERT INTO `tb_auth_role_function` VALUES ('176', '2', '79');
INSERT INTO `tb_auth_role_function` VALUES ('177', '2', '80');
INSERT INTO `tb_auth_role_function` VALUES ('178', '2', '87');
INSERT INTO `tb_auth_role_function` VALUES ('179', '2', '88');
INSERT INTO `tb_auth_role_function` VALUES ('180', '2', '91');
INSERT INTO `tb_auth_role_function` VALUES ('181', '2', '93');

-- ----------------------------
-- Table structure for tb_carousel_figure
-- ----------------------------
DROP TABLE IF EXISTS `tb_carousel_figure`;
CREATE TABLE `tb_carousel_figure` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imageUrl` varchar(255) NOT NULL,
  `clickUrl` varchar(255) NOT NULL,
  `platform` varchar(10) NOT NULL DEFAULT 'all',
  `status` int(1) NOT NULL DEFAULT '1',
  `addedTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `order` int(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_carousel_figure
-- ----------------------------

-- ----------------------------
-- Table structure for tb_resource_icon
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource_icon`;
CREATE TABLE `tb_resource_icon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL DEFAULT '0',
  `resourceType` varchar(100) NOT NULL,
  `iconName` varchar(100) NOT NULL,
  `className` varchar(100) NOT NULL,
  `fileName` varchar(100) DEFAULT NULL,
  `width` varchar(30) DEFAULT NULL,
  `filePath` varchar(255) DEFAULT NULL,
  `fileOldName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_resource_icon
-- ----------------------------
INSERT INTO `tb_resource_icon` VALUES ('22', '0', '1', '测试', 'icon-test', 'test', null, 'icons/test.png', '测试.png');
INSERT INTO `tb_resource_icon` VALUES ('23', '0', '1', 'api图标', 'icon-api', 'api', null, 'icons/api.png', 'API.png');
INSERT INTO `tb_resource_icon` VALUES ('24', '0', '1', '文件夹', 'icon-folder', 'folder', null, 'icons/folder.png', '文件夹.png');
INSERT INTO `tb_resource_icon` VALUES ('25', '0', '1', '商品管理', 'icon-goods', 'goods', null, 'icons/goods.png', '商品.png');
INSERT INTO `tb_resource_icon` VALUES ('26', '0', '1', '统计', 'icon-statis', 'statis', null, 'icons/statis.png', '统计.png');
INSERT INTO `tb_resource_icon` VALUES ('27', '0', '1', '内容管理', 'icon-content', 'content', null, 'icons/content.png', '内容.png');
INSERT INTO `tb_resource_icon` VALUES ('28', '0', '1', '运营', 'icon-operate', 'operate', null, 'icons/operate.png', '运营.png');
INSERT INTO `tb_resource_icon` VALUES ('29', '0', '1', '登录', 'icon-login', 'login', null, 'icons/login.png', '登录.png');

-- ----------------------------
-- Table structure for tb_resource_path_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource_path_config`;
CREATE TABLE `tb_resource_path_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resourceType` varchar(30) NOT NULL,
  `resourcePath` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_resource_path_config
-- ----------------------------
INSERT INTO `tb_resource_path_config` VALUES ('1', '1', 'upload/');
INSERT INTO `tb_resource_path_config` VALUES ('2', '2', 'D://upload/icon/');

-- ----------------------------
-- Table structure for tb_secret
-- ----------------------------
DROP TABLE IF EXISTS `tb_secret`;
CREATE TABLE `tb_secret` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_key` varchar(255) NOT NULL,
  `app_secret` varchar(255) NOT NULL,
  `platform` varchar(10) NOT NULL,
  `status` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_secret
-- ----------------------------
INSERT INTO `tb_secret` VALUES ('1', '%^%^U8990SAafafaf', '131@@$efsf', 'android', '1');

-- ----------------------------
-- Table structure for tb_system_dict
-- ----------------------------
DROP TABLE IF EXISTS `tb_system_dict`;
CREATE TABLE `tb_system_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL DEFAULT '0',
  `dictKey` varchar(100) NOT NULL,
  `dictName` varchar(100) NOT NULL,
  `dictValue` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_system_dict
-- ----------------------------
INSERT INTO `tb_system_dict` VALUES ('1', '0', 'RESOURCE_TYPE', 'icon图标', '1');
INSERT INTO `tb_system_dict` VALUES ('2', '0', 'RESOURCE_TYPE', '轮播图', '2');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `userName` varchar(30) NOT NULL,
  `passWord` varchar(50) NOT NULL,
  `roleId` int(9) NOT NULL,
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '1.正常',
  `addTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1', '1', '2017-07-15 21:06:30');
INSERT INTO `tb_user` VALUES ('2', 'test01', 'e10adc3949ba59abbe56e057f20f883e', '2', '1', '2017-07-05 21:06:34');

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(11) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `sex` int(1) DEFAULT '0',
  `nickName` varchar(50) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------

-- ----------------------------
-- Function structure for getFunChildrensList
-- ----------------------------
DROP FUNCTION IF EXISTS `getFunChildrensList`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getFunChildrensList`(`rootId` int) RETURNS varchar(1000) CHARSET utf8
BEGIN
		DECLARE sTemp VARCHAR(1000);
		DECLARE sTempPar VARCHAR(1000); 
		SET sTemp = ''; 
		SET sTempPar = rootId; 
		
		#循环递归
		WHILE sTempPar is not null DO 
			#判断是否是第一个，不加的话第一个会为空
			IF sTemp != '' THEN
				SET sTemp = concat(sTemp,',',sTempPar);
			ELSE
				SET sTemp = sTempPar;
			END IF;

			SET sTemp = concat(sTemp,',',sTempPar);
			SELECT group_concat(id) INTO sTempPar FROM tb_auth_function where id <> rootId AND  FIND_IN_SET(pid,sTempPar)>0;
		END WHILE; 
		RETURN sTemp;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getMenuChildrensList
-- ----------------------------
DROP FUNCTION IF EXISTS `getMenuChildrensList`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getMenuChildrensList`(`rootId` int) RETURNS varchar(1000) CHARSET utf8
BEGIN
		DECLARE sTemp VARCHAR(1000);
		DECLARE sTempPar VARCHAR(1000); 
		SET sTemp = ''; 
		SET sTempPar = rootId; 
		
		#循环递归
		WHILE sTempPar is not null DO 
			#判断是否是第一个，不加的话第一个会为空
			IF sTemp != '' THEN
				SET sTemp = concat(sTemp,',',sTempPar);
			ELSE
				SET sTemp = sTempPar;
			END IF;

			SET sTemp = concat(sTemp,',',sTempPar);
			SELECT group_concat(id) INTO sTempPar FROM tb_auth_menu_function where id <> rootId AND  FIND_IN_SET(pid,sTempPar)>0;
		END WHILE; 
		RETURN sTemp;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getMenuParentList
-- ----------------------------
DROP FUNCTION IF EXISTS `getMenuParentList`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getMenuParentList`(`rootId` int) RETURNS varchar(1000) CHARSET utf8
BEGIN
		DECLARE sTemp VARCHAR(1000);
		DECLARE sTempPar VARCHAR(1000); 
		SET sTemp = ''; 
		SET sTempPar = rootId; 
		
		#循环递归
		WHILE sTempPar is not null DO 
			#判断是否是第一个，不加的话第一个会为空
			IF sTemp != '' THEN
				SET sTemp = concat(sTemp,',',sTempPar);
			ELSE
				SET sTemp = sTempPar;
			END IF;

			SET sTemp = concat(sTemp,',',sTempPar); 
			SELECT group_concat(pid) INTO sTempPar FROM tb_auth_menu_function where pid<>id and FIND_IN_SET(id,sTempPar)>0; 
		END WHILE; 
		RETURN sTemp;
END
;;
DELIMITER ;
