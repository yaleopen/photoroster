CREATE TABLE `roster_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `browser` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `context_title` varchar(255) DEFAULT NULL,
  `course_id` varchar(255) DEFAULT NULL,
  `lms` varchar(255) DEFAULT NULL,
  `lti_version` varchar(255) DEFAULT NULL,
  `platform` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `session_id` varchar(255) NOT NULL,
  `session_end` datetime DEFAULT NULL,
  `session_start` datetime NOT NULL,
  `is_success` tinyint(1) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `session_id` (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `is_admin` tinyint(1) NOT NULL,
  `created_on` datetime NOT NULL,
  `department_info` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `SURNAME` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lti_consumer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `consumer_guid` varchar(255) DEFAULT NULL,
  `consumer_version` varchar(255) DEFAULT NULL,
  `created` datetime NOT NULL,
  `css_path` varchar(255) DEFAULT NULL,
  `enable_from` datetime DEFAULT NULL,
  `enable_until` datetime DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL,
  `lti_key` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `protected` tinyint(1) NOT NULL,
  `secret` varchar(255) NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lti_key` (`lti_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- TODO: Update with relevant values
INSERT INTO `lti_consumer` (`id`,`active`,`consumer_guid`,`consumer_version`,`created`,`css_path`,`enable_from`,`enable_until`,`enabled`,`lti_key`,`name`,`protected`,`secret`,`updated`) VALUES ();
