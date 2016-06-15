-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 15, 2016 at 09:43 AM
-- Server version: 10.1.8-MariaDB
-- PHP Version: 5.6.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projectdemo`
--

-- --------------------------------------------------------

--
-- Table structure for table `eignecache`
--

CREATE TABLE `eignecache` (
  `ei_id` bigint(20) NOT NULL,
  `info_id` bigint(20) NOT NULL,
  `ei_vector_path` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `enhanced`
--

CREATE TABLE `enhanced` (
  `e_id` bigint(20) NOT NULL,
  `info_id` bigint(20) NOT NULL,
  `p_id` bigint(20) NOT NULL,
  `e_path` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `info`
--

CREATE TABLE `info` (
  `info_id` bigint(20) NOT NULL,
  `log_id` bigint(20) NOT NULL,
  `Admin_name` varchar(50) NOT NULL,
  `Email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `info`
--

INSERT INTO `info` (`info_id`, `log_id`, `Admin_name`, `Email`) VALUES
(1, 3, '123', 'star@gmail.com'),
(2, 4, '1234', 'hello@yahoo.com'),
(3, 5, '123', 'sdjfk@gmail.com'),
(4, 6, '12', 'taposdatta@gmail.com'),
(5, 7, '12', 'taposdatta@gmail.com'),
(6, 8, '1234', '1234@gmail.com'),
(7, 9, 'Tapos Roy', 'Taposdatta@yahoo.com'),
(8, 10, '12', 'a@gmail.com'),
(9, 11, '12', 'r@gmail.com'),
(10, 12, 'tom', 'tom@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `log_id` bigint(20) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`log_id`, `username`, `password`) VALUES
(1, 'hello', '123'),
(2, 'tapos', '123'),
(3, 'hello', '123'),
(4, 'iam', '1234'),
(5, 'sdfsadf', '123'),
(6, 'hi', '12'),
(7, 'hi', '12'),
(8, '1234', '1234'),
(9, 'sadfsadf', '1234'),
(10, '12', '12'),
(11, '12', '12'),
(12, 'tom', 'tom');

-- --------------------------------------------------------

--
-- Table structure for table `original`
--

CREATE TABLE `original` (
  `o_id` bigint(20) NOT NULL,
  `info_id` bigint(20) NOT NULL,
  `p_id` bigint(20) NOT NULL,
  `o_path` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `original`
--

INSERT INTO `original` (`o_id`, `info_id`, `p_id`, `o_path`) VALUES
(3, 7, 3, 'F:\\project300Backup\\9\\original\\Tapos datta2.png'),
(4, 7, 3, 'F:\\project300Backup\\9\\original\\Tapos datta3.png'),
(5, 7, 4, 'F:\\project300Backup\\9\\original\\Tapos datta0.png'),
(6, 7, 4, 'F:\\project300Backup\\9\\original\\Tapos datta1.png'),
(7, 7, 4, 'F:\\project300Backup\\9\\original\\Tapos datta2.png'),
(8, 7, 4, 'F:\\project300Backup\\9\\original\\Tapos datta3.png'),
(9, 7, 5, 'F:\\project300Backup\\9\\original\\Tapos datta0.png'),
(10, 7, 5, 'F:\\project300Backup\\9\\original\\Tapos datta1.png'),
(11, 7, 5, 'F:\\project300Backup\\9\\original\\Tapos datta2.png'),
(12, 7, 5, 'F:\\project300Backup\\9\\original\\Tapos datta3.png'),
(13, 7, 6, 'F:\\project300Backup\\9\\original\\Tapos datta0.png'),
(14, 7, 6, 'F:\\project300Backup\\9\\original\\Tapos datta1.png'),
(15, 7, 6, 'F:\\project300Backup\\9\\original\\Tapos datta2.png'),
(16, 7, 6, 'F:\\project300Backup\\9\\original\\Tapos datta3.png'),
(17, 7, 7, 'F:\\project300Backup\\9\\original\\7_0.png'),
(18, 7, 7, 'F:\\project300Backup\\9\\original\\7_1.png'),
(19, 7, 7, 'F:\\project300Backup\\9\\original\\7_2.png');

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE `person` (
  `p_id` bigint(20) NOT NULL,
  `info_id` bigint(20) NOT NULL,
  `person_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`p_id`, `info_id`, `person_name`) VALUES
(3, 7, 'Tapos datta'),
(4, 7, 'Tapos datta'),
(5, 7, 'Tapos datta'),
(6, 7, 'Tapos datta'),
(7, 7, 'robi talukdar');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `eignecache`
--
ALTER TABLE `eignecache`
  ADD PRIMARY KEY (`ei_id`),
  ADD KEY `info_id` (`info_id`);

--
-- Indexes for table `enhanced`
--
ALTER TABLE `enhanced`
  ADD PRIMARY KEY (`e_id`),
  ADD KEY `info_id` (`info_id`),
  ADD KEY `p_id` (`p_id`);

--
-- Indexes for table `info`
--
ALTER TABLE `info`
  ADD PRIMARY KEY (`info_id`),
  ADD KEY `log_id` (`log_id`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`log_id`);

--
-- Indexes for table `original`
--
ALTER TABLE `original`
  ADD PRIMARY KEY (`o_id`),
  ADD KEY `info_id` (`info_id`),
  ADD KEY `p_id` (`p_id`);

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`p_id`),
  ADD KEY `info_id` (`info_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `eignecache`
--
ALTER TABLE `eignecache`
  MODIFY `ei_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `enhanced`
--
ALTER TABLE `enhanced`
  MODIFY `e_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `info`
--
ALTER TABLE `info`
  MODIFY `info_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `log_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `original`
--
ALTER TABLE `original`
  MODIFY `o_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;
--
-- AUTO_INCREMENT for table `person`
--
ALTER TABLE `person`
  MODIFY `p_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `eignecache`
--
ALTER TABLE `eignecache`
  ADD CONSTRAINT `eignecache_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `info` (`info_id`);

--
-- Constraints for table `enhanced`
--
ALTER TABLE `enhanced`
  ADD CONSTRAINT `enhanced_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `info` (`info_id`),
  ADD CONSTRAINT `enhanced_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `person` (`p_id`);

--
-- Constraints for table `info`
--
ALTER TABLE `info`
  ADD CONSTRAINT `info_ibfk_1` FOREIGN KEY (`log_id`) REFERENCES `login` (`log_id`);

--
-- Constraints for table `original`
--
ALTER TABLE `original`
  ADD CONSTRAINT `original_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `info` (`info_id`),
  ADD CONSTRAINT `original_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `person` (`p_id`);

--
-- Constraints for table `person`
--
ALTER TABLE `person`
  ADD CONSTRAINT `person_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `info` (`info_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
