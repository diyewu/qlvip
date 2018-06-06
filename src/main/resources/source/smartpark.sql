/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50629
Source Host           : localhost:3306
Source Database       : smartpark

Target Server Type    : MYSQL
Target Server Version : 50629
File Encoding         : 65001

Date: 2018-06-02 11:00:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for smart_car
-- ----------------------------
DROP TABLE IF EXISTS `smart_car`;
CREATE TABLE `smart_car` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `member_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `car_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `car_owner_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '车辆拥有者',
  `car_type` int(2) DEFAULT NULL,
  `create_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of smart_car
-- ----------------------------
INSERT INTO `smart_car` VALUES ('00152576327970100002', '00152574989796300000', '111111', '00152576327969400001', '1', '2018-05-08 15:07:59', '2018-05-08 15:07:59');
INSERT INTO `smart_car` VALUES ('00152576338153100006', '00152576337863000004', '11222', '00152576338149800005', '1', '2018-05-08 15:09:41', '2018-05-08 15:09:41');
INSERT INTO `smart_car` VALUES ('00152576348906100009', '00152576348903900007', '111223', '00152576348905800008', '1', '2018-05-08 15:11:29', '2018-05-08 15:11:29');
INSERT INTO `smart_car` VALUES ('00152618016291300002', '00152618016278500000', '111233', '00152618016289500001', '1', '2018-05-13 10:56:02', '2018-05-13 10:56:02');

-- ----------------------------
-- Table structure for smart_car_owner
-- ----------------------------
DROP TABLE IF EXISTS `smart_car_owner`;
CREATE TABLE `smart_car_owner` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `owner_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `owner_address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `owner_phone` int(20) DEFAULT NULL,
  `create_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `member_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of smart_car_owner
-- ----------------------------
INSERT INTO `smart_car_owner` VALUES ('00152576327969400001', null, null, null, '2018-05-08 15:07:59', '2018-05-08 15:07:59', '00152574989796300000');
INSERT INTO `smart_car_owner` VALUES ('00152576338149800005', '阿斯顿发', null, null, '2018-05-08 15:09:41', '2018-05-08 15:09:41', null);
INSERT INTO `smart_car_owner` VALUES ('00152576348905800008', '阿斯顿', null, null, '2018-05-08 15:11:29', '2018-05-08 15:11:29', null);
INSERT INTO `smart_car_owner` VALUES ('00152618016289500001', '12121212', null, null, '2018-05-13 10:56:02', '2018-05-13 10:56:02', null);

-- ----------------------------
-- Table structure for smart_car_park_recoder
-- ----------------------------
DROP TABLE IF EXISTS `smart_car_park_recoder`;
CREATE TABLE `smart_car_park_recoder` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `car_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `park_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `space_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `entrance_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `parking_type` int(1) DEFAULT NULL COMMENT '停车类型，0：驶入 1 驶出',
  `create_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of smart_car_park_recoder
-- ----------------------------

-- ----------------------------
-- Table structure for smart_car_park_record
-- ----------------------------
DROP TABLE IF EXISTS `smart_car_park_record`;
CREATE TABLE `smart_car_park_record` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `car_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `park_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `begin_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `end_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `receivable_amount` double DEFAULT NULL COMMENT '应收费用',
  `actual_amount` double DEFAULT NULL COMMENT '实收费用',
  `create_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pay_way_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付渠道',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of smart_car_park_record
-- ----------------------------

-- ----------------------------
-- Table structure for smart_member
-- ----------------------------
DROP TABLE IF EXISTS `smart_member`;
CREATE TABLE `smart_member` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `member_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '会员名称',
  `member_sex` int(1) DEFAULT NULL COMMENT '性别,0 女性，1 男性,2 未知',
  `create_time` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `open_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mobile` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_open_id` (`open_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of smart_member
-- ----------------------------
INSERT INTO `smart_member` VALUES ('00152775510077000003', '啊实打实', '1', '2018-05-31 16:25:00', '2018-05-31 16:25:44', 'asddasdsadasasdd', '2222222');

-- ----------------------------
-- Table structure for smart_mobile_code_send
-- ----------------------------
DROP TABLE IF EXISTS `smart_mobile_code_send`;
CREATE TABLE `smart_mobile_code_send` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_send_time` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` int(6) DEFAULT NULL,
  `remain_time` int(2) DEFAULT '10' COMMENT '当天手机验证码剩余次数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile_code_number` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of smart_mobile_code_send
-- ----------------------------
INSERT INTO `smart_mobile_code_send` VALUES ('1', '18936483081', '2018-05-08 15:09:38', '111111', '9');
INSERT INTO `smart_mobile_code_send` VALUES ('2', '1111111111', '2018-05-08 15:09:38', '122121', '7');

-- ----------------------------
-- Table structure for smart_mobile_code_send_history
-- ----------------------------
DROP TABLE IF EXISTS `smart_mobile_code_send_history`;
CREATE TABLE `smart_mobile_code_send_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `send_time` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `send_code` int(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `mobilesend_his_mobile` (`mobile`) USING BTREE,
  KEY `mobilesend_his_time` (`send_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of smart_mobile_code_send_history
-- ----------------------------

-- ----------------------------
-- Table structure for smart_order
-- ----------------------------
DROP TABLE IF EXISTS `smart_order`;
CREATE TABLE `smart_order` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `car_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `park_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `space_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `order_type` int(1) DEFAULT NULL COMMENT '订单类型：0:实时停车  1:预约停车',
  `order_state_id` int(2) DEFAULT NULL,
  `begin_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `end_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `receivable_amount` double DEFAULT NULL COMMENT '应收费用',
  `actual_amount` double DEFAULT NULL COMMENT '实收费用',
  `create_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pay_way_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付渠道',
  `record_in_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '驶入记录id',
  `record_out_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '驶出记录id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of smart_order
-- ----------------------------

-- ----------------------------
-- Table structure for smart_order_state_dictionory
-- ----------------------------
DROP TABLE IF EXISTS `smart_order_state_dictionory`;
CREATE TABLE `smart_order_state_dictionory` (
  `id` int(2) DEFAULT NULL,
  `state_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of smart_order_state_dictionory
-- ----------------------------
INSERT INTO `smart_order_state_dictionory` VALUES ('0', '完结，正常驶出停车场');
INSERT INTO `smart_order_state_dictionory` VALUES ('1', '申请驶进停车场');
INSERT INTO `smart_order_state_dictionory` VALUES ('2', '停车中');
INSERT INTO `smart_order_state_dictionory` VALUES ('3', '申请驶出停车场，待支付');
INSERT INTO `smart_order_state_dictionory` VALUES ('4', '支付完成，待出场');
INSERT INTO `smart_order_state_dictionory` VALUES ('5', '强制完结，无法正常完结');

-- ----------------------------
-- Table structure for smart_park
-- ----------------------------
DROP TABLE IF EXISTS `smart_park`;
CREATE TABLE `smart_park` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `park_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '场地名称',
  `park_description` text COLLATE utf8mb4_unicode_ci COMMENT '描述',
  `park_longitude` double DEFAULT NULL COMMENT '停车经度',
  `park_latitude` double DEFAULT NULL COMMENT '停车场维度',
  `create_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `manager_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '场地管理员编号，场地管理员或后台操作管理员不能同时为空',
  `operate_user_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '后台操作管理员编号,场地管理员或后台操作管理员不能同时为空',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of smart_park
-- ----------------------------
INSERT INTO `smart_park` VALUES ('00152618440631400018', '啊实打实的', '啊四大四大', '121.411439', '30.803186', '2018-05-13 12:06:46', '2018-05-13 12:06:46', '00152618016278500000', null);

-- ----------------------------
-- Table structure for smart_park_entrance
-- ----------------------------
DROP TABLE IF EXISTS `smart_park_entrance`;
CREATE TABLE `smart_park_entrance` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `park_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `entrance_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '入口名称',
  `entrance_longitude` double DEFAULT NULL COMMENT '经度',
  `entrance_latitude` double DEFAULT NULL COMMENT '纬度',
  `entrance_description` text COLLATE utf8mb4_unicode_ci,
  `create_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of smart_park_entrance
-- ----------------------------
INSERT INTO `smart_park_entrance` VALUES ('00152618393378800006', '00152618393373100004', '正门-阿萨大声道了', '121.239131', '31.015213', '啊实打实大师', '2018-05-13 11:58:53', '2018-05-13 11:58:53');
INSERT INTO `smart_park_entrance` VALUES ('00152618401477300008', '00152618401473500007', '正门-阿萨大声道了', '121.239131', '31.015213', '啊实打实大师', '2018-05-13 12:00:14', '2018-05-13 12:00:14');
INSERT INTO `smart_park_entrance` VALUES ('00152618412974200012', '00152618412961800010', '正门', '121.409973', '30.805171', '啊四大四大撒多', '2018-05-13 12:02:09', '2018-05-13 12:02:09');
INSERT INTO `smart_park_entrance` VALUES ('00152618430185500014', '00152618430181300013', '1111', '121.411439', '30.803186', '啊实打实的', '2018-05-13 12:05:01', '2018-05-13 12:05:01');
INSERT INTO `smart_park_entrance` VALUES ('00152618430185500015', '00152618430181300013', '2222', '2222', '31.015213', '啊四大四大撒多', '2018-05-13 12:05:01', '2018-05-13 12:05:01');
INSERT INTO `smart_park_entrance` VALUES ('00152618440635600021', '00152618440631400018', '1111', '121.411439', '30.803186', '啊实打实的', '2018-05-13 12:06:46', '2018-05-13 12:06:46');
INSERT INTO `smart_park_entrance` VALUES ('00152618440635700022', '00152618440631400018', '2222', '2222', '31.015213', '啊四大四大撒多', '2018-05-13 12:06:46', '2018-05-13 12:06:46');

-- ----------------------------
-- Table structure for smart_park_space
-- ----------------------------
DROP TABLE IF EXISTS `smart_park_space`;
CREATE TABLE `smart_park_space` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `park_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `space_type` int(2) DEFAULT NULL COMMENT '车位类型，无分类统一为0',
  `space_total` int(11) DEFAULT NULL COMMENT '类型车位总数',
  `space_used` int(11) DEFAULT NULL COMMENT '已占用停车位',
  `space_price_perhour` double DEFAULT NULL COMMENT '类型车位每小时停车费价格',
  `space_description` text COLLATE utf8mb4_unicode_ci,
  `create_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of smart_park_space
-- ----------------------------
INSERT INTO `smart_park_space` VALUES ('00152618440634800019', '00152618440631400018', '2', '3345', '222', '33', '撒打算', '2018-05-13 12:06:46', '2018-05-13 12:06:46');
INSERT INTO `smart_park_space` VALUES ('00152618440635000020', '00152618440631400018', '1', '1112', '11', '14', '啊实打实', '2018-05-13 12:06:46', '2018-05-13 12:06:46');

-- ----------------------------
-- Table structure for smart_park_space_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `smart_park_space_dictionary`;
CREATE TABLE `smart_park_space_dictionary` (
  `id` int(11) NOT NULL,
  `space_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `space_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of smart_park_space_dictionary
-- ----------------------------
INSERT INTO `smart_park_space_dictionary` VALUES ('1', '地下停车位', '1');
INSERT INTO `smart_park_space_dictionary` VALUES ('2', '地上停车位', '2');

-- ----------------------------
-- Table structure for smart_pay_way
-- ----------------------------
DROP TABLE IF EXISTS `smart_pay_way`;
CREATE TABLE `smart_pay_way` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pay_way` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付方式',
  `create_time` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of smart_pay_way
-- ----------------------------
INSERT INTO `smart_pay_way` VALUES ('1', '微信', null);
INSERT INTO `smart_pay_way` VALUES ('2', '支付宝', null);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '1', '1', '1', '1', null);
INSERT INTO `t_user` VALUES ('2', 'zhao', null, null, null, null);
INSERT INTO `t_user` VALUES ('3', 'test', null, null, null, null);
