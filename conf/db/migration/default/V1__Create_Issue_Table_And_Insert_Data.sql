DROP TABLE IF EXISTS `issue`;
CREATE TABLE `issue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `issue` varchar(200) NOT NULL,
  `raised_date` datetime NOT NULL,
  `challenge` varchar(200) DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `issue` (`issue`, `raised_date`, `challenge`, `status`) VALUES ('write test code for models', '2016-10-15', 'Know nothing', 'DONE');
INSERT INTO `issue` (`issue`, `raised_date`, `challenge`, `status`) VALUES ('write test code for controller', '2016-10-16', 'Khow nothing', 'DONE');
