/*
Navicat MySQL Data Transfer

Source Server         : 神马
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-04-02 11:33:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `ages` int(40) DEFAULT NULL COMMENT '年龄',
  `password` varchar(64) NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('0', '1', '');
INSERT INTO `user` VALUES ('2', '1', '');
INSERT INTO `user` VALUES ('12', '12', '');
INSERT INTO `user` VALUES ('13', '12', '');
INSERT INTO `user` VALUES ('14', '1', '1');
INSERT INTO `user` VALUES ('15', '11', '不知道');
INSERT INTO `user` VALUES ('16', '11', '不知道1');
INSERT INTO `user` VALUES ('17', '11', '不知道1');
