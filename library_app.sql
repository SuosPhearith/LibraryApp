-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 29, 2023 at 10:47 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library_app`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `bookId` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `author` varchar(50) DEFAULT NULL,
  `year` varchar(10) DEFAULT NULL,
  `pages` varchar(20) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `isActive` varchar(20) NOT NULL DEFAULT 'Active'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`bookId`, `title`, `author`, `year`, `pages`, `category`, `isActive`) VALUES
(28, '7Day', 'Dara', '2012', '123', 'Horror', 'Active'),
(29, 'Apple', 'Sur', '2020', '123', 'Horror', 'Active'),
(30, 'Hello', 'Dara', '2018', '123', 'Language', 'Active'),
(31, 'Java', 'Phearith', '2022', '132', 'Language', 'Active'),
(33, 'Dream_Walk', 'Phearith', '2023', '123', 'Horror', 'Active');

-- --------------------------------------------------------

--
-- Table structure for table `borrow`
--

CREATE TABLE `borrow` (
  `borrowId` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `schoolId` varchar(20) NOT NULL,
  `tel` varchar(20) NOT NULL,
  `borrowDate` date NOT NULL,
  `returnDate` date NOT NULL,
  `isReturn` varchar(10) NOT NULL DEFAULT 'NoReturn'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `borrow`
--

INSERT INTO `borrow` (`borrowId`, `name`, `schoolId`, `tel`, `borrowDate`, `returnDate`, `isReturn`) VALUES
(21, 'Khouchlin', 'e20200662', '069265958', '2023-06-18', '2023-06-30', 'NoReturn'),
(25, 'Phearith', 'e20200663', '069265958', '2023-06-18', '2023-06-20', 'Return'),
(26, 'Heng', 'e20200663', '069265958', '2023-06-19', '2023-06-22', 'Return'),
(27, 'Dara', 'e20200659', '096844942', '2023-06-20', '2023-06-21', 'NoReturn'),
(28, 'Sethy', 'e20200559', '099545353', '2023-06-19', '2023-06-20', 'NoReturn'),
(36, 'Veasna', 'e20200777', '029302032', '2023-06-29', '2023-06-30', 'NoReturn'),
(37, 'Heng', 'e20201216', '092839232', '2023-06-29', '2023-06-30', 'NoReturn'),
(38, 'Veachea', 'e20200728', '093943432', '2023-06-29', '2023-07-01', 'NoReturn'),
(40, 'Saitama', 'e2020083', '097394733', '2023-06-29', '2023-06-30', 'NoReturn');

-- --------------------------------------------------------

--
-- Table structure for table `borrowbook`
--

CREATE TABLE `borrowbook` (
  `borrowBookId` int(11) NOT NULL,
  `borrowId` int(11) DEFAULT NULL,
  `bookId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `borrowbook`
--

INSERT INTO `borrowbook` (`borrowBookId`, `borrowId`, `bookId`) VALUES
(15, 21, 28),
(16, 21, 29),
(17, 21, 30),
(26, 25, 29),
(27, 25, 30),
(28, 25, 29),
(29, 26, 28),
(30, 26, 30),
(31, 26, 31),
(32, 27, 28),
(33, 27, 31),
(34, 27, 30),
(35, 27, NULL),
(36, 27, 29),
(37, 28, 28),
(38, 28, 29),
(39, 28, 30),
(40, 28, 31),
(41, 28, NULL),
(42, NULL, 28),
(43, NULL, 28),
(44, NULL, 28),
(45, NULL, 31),
(46, NULL, 31),
(47, NULL, 28),
(48, NULL, 29),
(49, NULL, 29),
(50, NULL, 31),
(51, NULL, 33),
(52, NULL, 29),
(53, NULL, 30),
(54, NULL, 31),
(55, NULL, 33),
(56, NULL, 31),
(57, NULL, 30),
(58, NULL, 30),
(59, NULL, 31),
(60, 36, 29),
(61, 36, 33),
(62, 37, 31),
(63, 37, 33),
(64, 38, 31),
(65, 38, 30),
(66, 40, 30),
(67, 40, 31),
(68, NULL, 30),
(69, NULL, 31),
(70, NULL, 29),
(71, NULL, 31),
(72, NULL, 30),
(73, NULL, 31),
(74, NULL, 30),
(75, NULL, 29);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `categoryId` int(11) NOT NULL,
  `catName` varchar(50) NOT NULL,
  `catInfo` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`categoryId`, `catName`, `catInfo`) VALUES
(1, 'History', 'About Khmer history'),
(2, 'Language', 'About language'),
(3, 'Horror', 'about horror'),
(8, 'Batman', 'about batman'),
(11, 'Magic', 'about magics');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `isActive` varchar(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `isActive`) VALUES
(1, 'Phearith', '123', '1'),
(2, 'John Doe', '30', '1'),
(3, 'SreyRoth', '123', '1'),
(4, 'Votey', '123', '1'),
(5, 'Votey', '123', '1'),
(6, 'Phearith', '123', '1'),
(7, 'Phearith', '123', '1'),
(8, 'SuosPhearith', '123', '1'),
(9, 'Phearith1', '123', '1'),
(10, 'Thida', '123', '1'),
(11, 'hello', '123', '1'),
(12, 'Dara', '123', '1'),
(13, 'HENG', '123', '1'),
(14, 'Pokhim', '123', '1'),
(15, 'Sethy', '123', '1'),
(16, 'John', '123', '1'),
(17, 'Vuthy', '123', '1'),
(18, 'John2', 'aa', '1'),
(19, 'Heng1', '123', '1'),
(20, 'SethyA', '123', '1'),
(21, 'bossheng', '123', '1'),
(22, 'Banana', '123', '1'),
(23, 'Sur', '123', '1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`bookId`),
  ADD KEY `category` (`category`);

--
-- Indexes for table `borrow`
--
ALTER TABLE `borrow`
  ADD PRIMARY KEY (`borrowId`);

--
-- Indexes for table `borrowbook`
--
ALTER TABLE `borrowbook`
  ADD PRIMARY KEY (`borrowBookId`),
  ADD KEY `borrowId` (`borrowId`),
  ADD KEY `bookId` (`bookId`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`categoryId`),
  ADD UNIQUE KEY `catName` (`catName`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `bookId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `borrow`
--
ALTER TABLE `borrow`
  MODIFY `borrowId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `borrowbook`
--
ALTER TABLE `borrowbook`
  MODIFY `borrowBookId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `categoryId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `borrowbook`
--
ALTER TABLE `borrowbook`
  ADD CONSTRAINT `borrowbook_ibfk_1` FOREIGN KEY (`borrowId`) REFERENCES `borrow` (`borrowId`) ON DELETE SET NULL,
  ADD CONSTRAINT `borrowbook_ibfk_2` FOREIGN KEY (`bookId`) REFERENCES `books` (`bookId`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
