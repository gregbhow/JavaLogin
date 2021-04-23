-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 22, 2021 at 08:06 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 7.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `login`
--
CREATE DATABASE IF NOT EXISTS `login` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `login`;

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `id_login` int(11) NOT NULL,
  `name` text NOT NULL,
  `surname` text NOT NULL,
  `login` text NOT NULL,
  `password` text NOT NULL,
  `salary` text NOT NULL,
  `type` text NOT NULL DEFAULT 'user'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id_login`, `name`, `surname`, `login`, `password`, `salary`, `type`) VALUES
(1, 'Gregory', 'Bhowany', 'gregb@gmail.com', '3cb2ba5675cebf4b11c1c15e7e3e76ac9ebb8ebc', 'KeJkdoYs2FaqUxBEOd0EBA==', 'admin'),
(2, 'Tony', 'Stark', 'tony@stark.com', '3cb2ba5675cebf4b11c1c15e7e3e76ac9ebb8ebc', '/s4s2f/ErZJMm5apl/xRLw==', 'user'),
(3, 'Scooby', 'Doo', 'scooby@doo.com', '3cb2ba5675cebf4b11c1c15e7e3e76ac9ebb8ebc', 'RWYqeFSMeAefDREum4UIIA==', 'user'),
(5, 'Didier', 'Samfat', 'didier@samfat.com', '3cb2ba5675cebf4b11c1c15e7e3e76ac9ebb8ebc', '71PWTPFdwVdLUp8xktgsgA==', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id_login`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `id_login` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
