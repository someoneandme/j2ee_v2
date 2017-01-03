CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(24) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `address` varchar(120) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11019 DEFAULT CHARSET=gbk