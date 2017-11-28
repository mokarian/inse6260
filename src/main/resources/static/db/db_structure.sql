
DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `active` int(11) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `address` varchar(255),
    `phone` varchar(255),
    `dtype` varchar(255),
  `tuition` decimal ,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT,
  `courseName` varchar(255) DEFAULT NULL,
  `preRequisites` varchar(255) DEFAULT NULL,
  `dtype`         varchar(255) DEFAULT NULL,
  `schedule_id` int(11)  DEFAULT NULL,
  `semester` varchar(255) DEFAULT NULL,
  `schedule` varchar(255) DEFAULT NULL,
  `grade` FLOAT(5) DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


-- DROP TABLE IF EXISTS `coursehistory`;

-- CREATE TABLE `coursehistory` (
--   `coursehistory_id` int(11) NOT NULL AUTO_INCREMENT,
--   `courseName` varchar(255) DEFAULT NULL,
--   `preRequisites` varchar(255) DEFAULT NULL,
--   `schedule_id` int(11) DEFAULT NULL,
--   `semester` varchar(255) DEFAULT NULL,
--   `grade` FLOAT(3,2) DEFAULT NULL,
--   PRIMARY KEY (`coursehistory_id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- DROP TABLE IF EXISTS `schedule`;

-- CREATE TABLE `schedule` (
--   `schedule_id` int(11) NOT NULL AUTO_INCREMENT,
--   `weekDay` varchar(255) DEFAULT NULL,
--   `timeLine` varchar(255) DEFAULT NULL,
--   `classType` varchar(255) DEFAULT NULL,
--   `courseID` varchar(255) DEFAULT NULL,
--   PRIMARY KEY (`schedule_id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;



-- DROP TABLE IF EXISTS `student`;

-- CREATE TABLE `student` (
--   `user_id` int(11) NOT NULL AUTO_INCREMENT,
--   `active` int(11) DEFAULT NULL,
--   `email` varchar(255) NOT NULL,
--   `last_name` varchar(255) NOT NULL,
--   `name` varchar(255) NOT NULL,
--   `password` varchar(255) ,
--   `address` varchar(255),
--   `phone` varchar(255),
--   `tuition` decimal,
--   PRIMARY KEY (`user_id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


-- DROP TABLE IF EXISTS `user_coursehistory`;

-- CREATE TABLE `user_coursehistory` (
--   `user_id` int(11) NOT NULL,
--   `coursehistory_id` int(11) NOT NULL,
--   PRIMARY KEY (`user_id`,`coursehistory_id`),
--   FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
--   FOREIGN KEY (`coursehistory_id`) REFERENCES `coursehistory` (`coursehistory_id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- DROP TABLE IF EXISTS `user_course`;

-- CREATE TABLE `user_course` (
--   `user_id` int(11) NOT NULL,
--   `course_id` int(11) NOT NULL,
--   PRIMARY KEY (`user_id`,`course_id`),
--   FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
--   FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- DROP TABLE IF EXISTS `teacher`;

-- CREATE TABLE `teacher` (
--   `user_id` int(11) NOT NULL AUTO_INCREMENT,
--   `active` int(11) DEFAULT NULL,
--   `email` varchar(255) NOT NULL,
--   `last_name` varchar(255) NOT NULL,
--   `name` varchar(255) NOT NULL,
--   `password` varchar(255) NOT NULL,
--   PRIMARY KEY (`user_id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

