
DROP TABLE IF EXISTS `currency`;

CREATE TABLE `currency` (
  `currency_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL ,
  `symbol` varchar(15) NOT NULL ,
  PRIMARY KEY (`currency_id`)
);

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `description` varchar(1000) NOT NULL,
  PRIMARY KEY (`category_id`)
);

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `category_id` int(11) NOT NULL,
  `currency_id` int(11) NOT NULL,
  `price` DECIMAL(11,2) NOT NULL,
  PRIMARY KEY (`product_id`),
  CONSTRAINT `prod_cate_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `currency_id_fk` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`currency_id`)
);


INSERT INTO `currency` VALUES (1,'Euro', 'EUR');
INSERT INTO `currency` VALUES (2,'US Dollar', 'USD');

INSERT INTO `category` VALUES (1,'Fashion', 'Fashion clothing category');
INSERT INTO `category` VALUES (2,'Kitchen', 'Kitchen Appliances category');

INSERT INTO `product` VALUES (1,'Jeans', 'Jeans From Denim', 1,  1, 200.00);

