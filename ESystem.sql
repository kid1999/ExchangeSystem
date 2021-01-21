/*
 Navicat Premium Data Transfer

 Source Server         : Tencent
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 159.75.6.26:3306
 Source Schema         : ESystem

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 21/01/2021 23:59:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_context` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `activity_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `activity_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address_id` bigint(20) NULL DEFAULT NULL,
  `detail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_user_id` bigint(20) NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `deleted` tinyint(4) NULL DEFAULT 0,
  `number_of_join` bigint(20) NULL DEFAULT 0,
  `status` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `activity_user`(`create_user_id`) USING BTREE,
  INDEX `address_id`(`address_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (1, 'hahaha', 'sssss', 'wwww', 1, '成都市成华大道', 1, '2021-01-12 15:32:03', '2021-02-27 15:32:06', 0, 0, 0);
INSERT INTO `activity` VALUES (2, 'dsadasd', 'aaaaa', 'ffffff', 14, '泸州老窖大厦', 5, '2021-01-17 14:59:45', '2021-01-15 05:45:40', 0, 0, 0);
INSERT INTO `activity` VALUES (3, '吃喝玩乐', '捡破烂小分队', NULL, 15, '泸州老窖大厦101室', 5, '2021-01-17 14:59:45', '2021-01-15 05:45:40', 0, 0, 0);

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (1, '北京市市辖区东城区', '北京市', '市辖区', '东城区');
INSERT INTO `address` VALUES (2, '天津市市辖区河西区', '天津市', '市辖区', '河西区');
INSERT INTO `address` VALUES (3, '河北省唐山市路南区', '河北省', '唐山市', '路南区');
INSERT INTO `address` VALUES (4, '北京市市辖区西城区', '北京市', '市辖区', '西城区');
INSERT INTO `address` VALUES (5, '北京市市辖区朝阳区', '北京市', '市辖区', '朝阳区');
INSERT INTO `address` VALUES (6, '河北省秦皇岛市山海关区', '河北省', '秦皇岛市', '山海关区');
INSERT INTO `address` VALUES (7, '山西省阳泉市矿区', '山西省', '阳泉市', '矿区');
INSERT INTO `address` VALUES (8, '山西省阳泉市城区', '山西省', '阳泉市', '城区');
INSERT INTO `address` VALUES (9, '山西省阳泉市市辖区', '山西省', '阳泉市', '市辖区');
INSERT INTO `address` VALUES (10, '山西省阳泉市郊区', '山西省', '阳泉市', '郊区');
INSERT INTO `address` VALUES (11, '山西省阳泉市平定县', '山西省', '阳泉市', '平定县');
INSERT INTO `address` VALUES (12, '山西省大同市新荣区', '山西省', '大同市', '新荣区');
INSERT INTO `address` VALUES (13, '天津市市辖区河北区', '天津市', '市辖区', '河北区');
INSERT INTO `address` VALUES (14, '山西省长治市屯留区', '山西省', '长治市', '屯留区');
INSERT INTO `address` VALUES (15, '四川省泸州市泸县', '四川省', '泸州市', '泸县');

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `goods_id` bigint(20) NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `deleted` tinyint(255) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collection
-- ----------------------------
INSERT INTO `collection` VALUES (1, 1, 1, '2021-01-28 19:51:17', 0);
INSERT INTO `collection` VALUES (2, 1, 0, '2021-01-03 07:07:20', 0);
INSERT INTO `collection` VALUES (18, 5, 1, '2020-12-27 13:40:05', 0);
INSERT INTO `collection` VALUES (26, 5, 5, '2020-12-14 21:39:07', 0);
INSERT INTO `collection` VALUES (41, 5, 3, '2021-01-10 04:12:21', 1);
INSERT INTO `collection` VALUES (42, 5, 4, '2021-01-07 06:12:34', 1);
INSERT INTO `collection` VALUES (44, 5, 2, '2021-01-09 15:56:03', 1);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user1_id` bigint(11) NOT NULL COMMENT '发信人',
  `goods_id` bigint(11) UNSIGNED NULL DEFAULT 1 COMMENT '商品id 可无',
  `user2_id` bigint(11) NULL DEFAULT NULL COMMENT '收信人',
  `date` datetime(0) NULL DEFAULT NULL,
  `context` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '状态',
  `deleted` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user1_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 1, 1, 1, '2021-01-04 11:15:00', '55555', 0, 0);
INSERT INTO `comment` VALUES (2, 5, 1, 5, '2021-01-13 23:58:32', 'thanks for your generous help.', 1, 0);
INSERT INTO `comment` VALUES (3, 2, 5, 5, '2021-01-14 00:03:20', 'aaaaa', 1, 0);
INSERT INTO `comment` VALUES (4, 5, 5, 5, '2021-01-17 01:44:08', 'ok get your reply.', 1, 0);
INSERT INTO `comment` VALUES (5, 5, 6, 3, '2021-01-17 02:17:58', 'ok ?', 0, 0);
INSERT INTO `comment` VALUES (6, 5, 1, 5, '2021-01-17 02:20:35', 'ok????', 1, 1);
INSERT INTO `comment` VALUES (7, 5, 0, 5, '2021-01-17 02:27:20', 'just do it.', 1, 0);
INSERT INTO `comment` VALUES (9, 5, 0, 5, '2021-01-17 02:50:14', 'test', 0, 0);
INSERT INTO `comment` VALUES (10, 5, 1, 5, '2021-01-17 03:19:44', '', 0, 0);

-- ----------------------------
-- Table structure for contact_way
-- ----------------------------
DROP TABLE IF EXISTS `contact_way`;
CREATE TABLE `contact_way`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `other` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `qq` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `wechat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of contact_way
-- ----------------------------
INSERT INTO `contact_way` VALUES (1, '', NULL, '13211112222', '', NULL);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `goods_condition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '成色',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `want_goods_id` bigint(20) NULL DEFAULT NULL COMMENT '期望交换物品id',
  `address_id` bigint(20) NULL DEFAULT NULL COMMENT '所在地区',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `goods_status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `number_of_clicked` int(11) NULL DEFAULT NULL COMMENT '被点击次数',
  `create_date` datetime(0) NULL DEFAULT NULL,
  `deleted` tinyint(4) NULL DEFAULT 0,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, 1, '无', 'http://kid1999.top:9000/default/avatar.png', '九成新', '描述信息', 2, 1, '11111', 0, 0, '2021-01-20 16:59:58', 0, 0.00);
INSERT INTO `goods` VALUES (2, 2, '电脑', 'http://kid1999.top:9000/default/avatar.png', '九成新', 'string', 2, 1, '不要放辣椒', 0, 0, '2021-01-03 03:15:28', 0, 0.00);
INSERT INTO `goods` VALUES (3, 2, '手表', 'http://kid1999.top:9000/default/avatar.png', '九成新', '描述信息', 3, 1, 'ss', 0, 0, '2021-01-12 16:55:47', 0, 0.00);
INSERT INTO `goods` VALUES (4, 4, '羽绒服', 'http://kid1999.top:9000/default/avatar.png', '全新', 'asda', 4, 1, 'asdasd', 0, 0, '2021-01-20 16:58:05', 0, 0.00);
INSERT INTO `goods` VALUES (5, 5, '羽绒服', 'http://kid1999.top:9000/default/avatar.png', '七成新', 'dasdasd', 6, 1, 'dasdasd', 0, 0, '2021-01-26 16:59:22', 0, 0.00);
INSERT INTO `goods` VALUES (6, 1, '手机', 'http://kid1999.top:9000/default/avatar.png', '全新', 'sad', 2, 1, 'sd', 0, 22, '2021-01-29 16:40:12', 0, 0.00);

-- ----------------------------
-- Table structure for join_activity
-- ----------------------------
DROP TABLE IF EXISTS `join_activity`;
CREATE TABLE `join_activity`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NULL DEFAULT NULL,
  `activity_id` bigint(11) NULL DEFAULT NULL,
  `join_time` datetime(0) NULL DEFAULT NULL,
  `deleted` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of join_activity
-- ----------------------------
INSERT INTO `join_activity` VALUES (1, 5, 1, '2021-01-28 17:11:10', 1);
INSERT INTO `join_activity` VALUES (3, 5, 1, '2021-01-18 05:09:02', 0);

-- ----------------------------
-- Table structure for leaving_comment
-- ----------------------------
DROP TABLE IF EXISTS `leaving_comment`;
CREATE TABLE `leaving_comment`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user1_id` bigint(11) NULL DEFAULT NULL COMMENT '留言者',
  `user2_id` bigint(11) UNSIGNED NULL DEFAULT NULL COMMENT '被留言者',
  `date` datetime(0) NULL DEFAULT NULL,
  `context` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '状态',
  `deleted` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user1_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of leaving_comment
-- ----------------------------
INSERT INTO `leaving_comment` VALUES (1, 1, 2, '2021-01-13 11:58:50', '1111122222', 0, 0);

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_used` timestamp(0) NOT NULL,
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------
INSERT INTO `persistent_logins` VALUES ('kid', 'bQmvyAjfVLA3nkPqOK0GgA==', '5zhaIuEqkzFf3DxT/Hn26w==', '2021-01-19 07:19:12');

-- ----------------------------
-- Table structure for trans_record
-- ----------------------------
DROP TABLE IF EXISTS `trans_record`;
CREATE TABLE `trans_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '交易状态\r\n0 申请\r\n1 接受申请\r\n2 完成交易\r\n\r\n11 拒绝申请\r\n12 交易失败\r\n\r\n20 客服介入',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `user1_id` bigint(20) NULL DEFAULT NULL COMMENT '卖家',
  `user2_id` bigint(20) NULL DEFAULT NULL COMMENT '买家',
  `goods_id` bigint(20) NOT NULL COMMENT '货物',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `detailed_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `detailed_datetime` datetime(0) NULL DEFAULT NULL,
  `deleted` tinyint(4) NULL DEFAULT 0,
  `exchange_goods_Id` bigint(20) NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `goods_id`) USING BTREE,
  INDEX `goods_id`(`goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of trans_record
-- ----------------------------
INSERT INTO `trans_record` VALUES (1, 0, '666', 1, 2, 1, '2021-01-18 02:41:16', '2021-02-16 01:32:06', NULL, NULL, 0, 2, NULL, '17377433406');
INSERT INTO `trans_record` VALUES (5, 4, 'dasd', 2, 5, 1, '2021-01-15 22:10:33', NULL, 'sadadas', '2021-01-06 15:26:39', 0, 3, NULL, '17377433406');
INSERT INTO `trans_record` VALUES (6, 1, '666', 1, 5, 2, '2021-01-15 21:59:41', NULL, '天安门', '2021-01-09 15:17:44', 0, 5, NULL, '17377433406');
INSERT INTO `trans_record` VALUES (7, 4, '666', 5, 5, 1, '2021-01-15 21:14:46', NULL, '44444', '2021-01-10 01:35:43', 0, 1, NULL, '17377433406');
INSERT INTO `trans_record` VALUES (8, 4, '留言如下', 2, 2, 1, '2021-01-15 07:19:56', NULL, '七星公园303户', '2021-01-28 02:00:00', 0, 5, 100.00, '17377433406');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address_id` bigint(255) NULL DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `contact_way_id` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `last_login_time` datetime(6) NULL DEFAULT NULL,
  `login_times` bigint(20) NULL DEFAULT NULL,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `deleted` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKllg3f9tpayhjurrtjkm8th7y2`(`contact_way_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 1, 'http://kid1999.top:9000/default/avatar.png', 1, '2020-12-30 09:15:53.000000', '2021-01-05 22:09:53.000000', 1, 'kid', 'asdasda', 'hahhahhhh', 0);
INSERT INTO `user` VALUES (2, 1, 'http://kid1999.top:9000/default/avatar.png', 1, '2021-01-16 11:57:26.000000', '2021-01-29 11:57:22.000000', 1, '123', 'aaaa', 'aaaaaa', 0);
INSERT INTO `user` VALUES (5, 1, 'http://kid1999.top:9000/default/avatar.png', 1, '2021-01-04 23:35:26.845446', '2021-01-04 23:35:26.845446', 0, '1111', '1111', '', 0);

SET FOREIGN_KEY_CHECKS = 1;
