/*
 Navicat Premium Dump SQL

 Source Server         : SecKill
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40)
 Source Host           : localhost:3306
 Source Schema         : seckilltest

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40)
 File Encoding         : 65001

 Date: 08/01/2025 02:22:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '类别id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类别名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '类别表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_category
-- ----------------------------
INSERT INTO `t_category` VALUES (1, '手机');
INSERT INTO `t_category` VALUES (2, '电脑');

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `category_id` bigint UNSIGNED NOT NULL COMMENT '商品类别',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品标题',
  `img` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片URL',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品详情描述',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `stock` int NULL DEFAULT 0 COMMENT '库存数量，-1表示无限制',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_goods_category`(`category_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES (1, 1, '华为note12', '华为note12', NULL, '好', 29999.00, 733);
INSERT INTO `t_goods` VALUES (2, 1, '小米mate7', '小米mate7', NULL, '好好', 3999.00, 2999);
INSERT INTO `t_goods` VALUES (3, 2, '联想mini2024', '联想mini2024', NULL, '好好好', 12700.00, 3989);

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户ID',
  `goods_id` bigint UNSIGNED NOT NULL COMMENT '商品ID',
  `delivery_addr_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '收货地址ID',
  `goods_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `goods_count` int NULL DEFAULT 1 COMMENT '商品数量',
  `goods_price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `order_channel` tinyint NULL DEFAULT 1 COMMENT '订单渠道，1-PC，2-Android，3-iOS',
  `status` tinyint NULL DEFAULT 0 COMMENT '订单状态',
  `create_date` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `pay_date` datetime NULL DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_goods_id`(`goods_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 338 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for t_seckill_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_goods`;
CREATE TABLE `t_seckill_goods`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '秒杀商品ID',
  `goods_id` bigint UNSIGNED NOT NULL COMMENT '商品ID',
  `seckill_price` decimal(10, 2) NOT NULL COMMENT '秒杀价格',
  `stock_count` int NULL DEFAULT 0 COMMENT '秒杀库存数量',
  `start_date` datetime NULL DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime NULL DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_goods_id`(`goods_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '秒杀商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_seckill_goods
-- ----------------------------
INSERT INTO `t_seckill_goods` VALUES (1, 3, 11.00, 1061, '2025-01-06 14:07:57', '2025-01-31 14:08:01');
INSERT INTO `t_seckill_goods` VALUES (2, 1, 1.10, 9661, '2025-01-07 01:20:41', '2025-01-18 01:20:44');
INSERT INTO `t_seckill_goods` VALUES (3, 12345, 99.99, 100, '2025-01-10 10:00:00', '2025-01-15 22:00:00');

-- ----------------------------
-- Table structure for t_seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_order`;
CREATE TABLE `t_seckill_order`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '秒杀订单ID',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户ID',
  `order_id` bigint UNSIGNED NOT NULL COMMENT '订单ID',
  `goods_id` bigint UNSIGNED NOT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_goods`(`user_id` ASC, `goods_id` ASC) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE,
  INDEX `fk_seckill_order_goods`(`goods_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 338 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '秒杀订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '加密后的密码',
  `salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码加密的盐值',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `register_date` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册日期',
  `last_login_date` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录日期',
  `login_count` int NULL DEFAULT 0 COMMENT '登录次数',
  `role` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_mobile`(`mobile` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, '1234', 'Abcd1234!', '27e202b10190aebe', '1146802625@qq.com', '18577885423', '2024-11-29 13:31:12', '2024-11-29 14:55:46', 13, 0);
INSERT INTO `t_user` VALUES (2, 'xuxu', '123', 'manman', '1231231@312312', '123123123123', '2025-01-08 02:01:50', '2025-01-30 02:01:53', 0, 1);

SET FOREIGN_KEY_CHECKS = 1;
