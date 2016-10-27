DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` (`name`, `email`, `password`) VALUES ('Tinh', 'tinh_pt@septeni-technology.jp', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');