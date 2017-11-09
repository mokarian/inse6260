INSERT INTO course (course_id, courseName, dtype)
VALUES (1, "COMP 691", "comp"),
(2, "COMP 791", "comp"),
(3, "COMP 6231", "comp"),
(4, "COMP 6281", "comp"),
(5, "COMP 7241", "comp"),
(6, "COMP 7251", "comp"),
(7, "COMP 6311", "comp"),
(8, "COMP 6321", "comp"),
(9, "COMP 6711", "comp"),
(10, "COMP 6731", "comp"),
(11, "COMP 6761", "comp"),
(12, "COMP 6771", "comp"),
(13, "COMP 7661", "comp"),
(14, "COMP 7751", "comp"),
(15, "COMP 7781", "comp"),
(16, "COMP 6411", "comp"),
(17, "COMP 6421", "comp"),
(18, "COMP 6461", "comp"),
(19, "COMP 7451", "comp"),
(20, "COMP 6521", "comp"),
(21, "COMP 6591", "comp"),
(22, "COMP 6811", "comp"),
(23, "COMP 6821", "comp"),
(24, "COMP 7531", "comp"),
(25, "COMP 6351", "comp"),
(26, "COMP 6361", "comp"),
(27, "COMP 6621", "comp"),
(28, "COMP 6641", "comp"),
(29, "COMP 6651", "comp"),
(30, "COMP 6661", "comp"),
(31, "COMP 7521", "comp"),
(32, "COMP 7651", "comp"),
(33, "COMP 6531", "comp"),
(34, "COMP 6721", "comp"),
(35, "COMP 6741", "comp"),
(36, "COMP 6751", "comp"),
(37, "COMP 6781", "comp"),
(38, "COMP 6791", "comp"),
(39, "SOEN 691", "soen"),
(40, "SOEN 791", "soen"),
(41, "SOEN 6441", "soen"),
(42, "SOEN 6751", "soen"),
(43, "SOEN 7761", "soen"),
(44, "SOEN 6311", "soen"),
(45, "SOEN 6461", "soen"),
(46, "SOEN 6471", "soen"),
(47, "SOEN 6481", "soen"),
(48, "SOEN 6861", "soen"),
(49, "SOEN 6431", "soen"),
(50, "SOEN 6491", "soen"),
(51, "SOEN 6611", "soen"),
(52, "SOEN 7481", "soen"),
(53, "SOEN 6011", "soen"),
(54, "SOEN 6841", "soen"),
(55, "SOEN 6761", "soen"),
(56, "SOEN 6951", "soen"),
(57, "SOEN 6211", "soen"),
(58, "SOEN 6941", "soen"),
(59, "COMP 6961", "comp"),
(60, "COMP 6971", "comp"),
(61, "SOEN 6971", "soen"),
(62, "COMP 7941", "comp"),
(63, "ENCS 6931", "encs"),
(64, "SOEN 7941", "soen"),
(65, "SOEN 6501", "soen"),,
(66, "COEN 7311", "coen"),
(67, "ENCS 6021", "encs"),
(68, "ENCS 6161", "encs"),
(69, "ENCS 6181", "encs");

UPDATE `inse`.`course` SET `preRequisites`='COMP 6761' WHERE `course_id`='7';
UPDATE `inse`.`course` SET `preRequisites`='COMP 6281' WHERE `course_id`='5';
UPDATE `inse`.`course` SET `preRequisites`='COMP 6461' WHERE `course_id`='6';
UPDATE `inse`.`course` SET `preRequisites`='COMP 6411' WHERE `course_id`='19';
UPDATE `inse`.`course` SET `preRequisites`='COMP 6651' WHERE `course_id`='31';
UPDATE `inse`.`course` SET `preRequisites`='COMP 6521' WHERE `course_id`='24';
UPDATE `inse`.`course` SET `preRequisites`='COMP 6651' WHERE `course_id`='32';
UPDATE `inse`.`course` SET `preRequisites`='COMP 6761' WHERE `course_id`='13';
UPDATE `inse`.`course` SET `preRequisites`='COMP 6731' WHERE `course_id`='14';
UPDATE `inse`.`course` SET `preRequisites`='COMP 6771' WHERE `course_id`='15';
UPDATE `inse`.`course` SET `preRequisites`='SOEN 6461' WHERE `course_id`='50';
UPDATE `inse`.`course` SET `preRequisites`='SOEN 6461' WHERE `course_id`='52';
UPDATE `inse`.`course` SET `preRequisites`='SOEN 6481' WHERE `course_id`='52';
UPDATE `inse`.`course` SET `preRequisites`='SOEN 6751' WHERE `course_id`='43';
