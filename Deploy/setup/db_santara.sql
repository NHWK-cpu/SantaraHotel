-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 10, 2025 at 08:20 PM
-- Server version: 11.7.2-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_santarahotel`
--
CREATE DATABASE IF NOT EXISTS `db_santarahotel` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `db_santarahotel`;

-- --------------------------------------------------------

--
-- Table structure for table `bulan_ref`
--

CREATE TABLE `bulan_ref` (
  `bulan` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bulan_ref`
--

INSERT INTO `bulan_ref` (`bulan`) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10),
(11),
(12);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `nama` varchar(1000) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nomor_telepon` varchar(16) NOT NULL,
  `tanggal_check_in` datetime NOT NULL,
  `tanggal_check_out` datetime NOT NULL,
  `tipe_kamar` text NOT NULL,
  `nomor_kamar` int(11) NOT NULL,
  `status` text NOT NULL,
  `id_pesanan` varchar(8) NOT NULL,
  `varian_kamar` varchar(1000) DEFAULT NULL,
  `harga` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`nama`, `email`, `nomor_telepon`, `tanggal_check_in`, `tanggal_check_out`, `tipe_kamar`, `nomor_kamar`, `status`, `id_pesanan`, `varian_kamar`, `harga`) VALUES
('nobo', 'hafizhammarmuflih2@gmail.com', '+628885461046', '2025-05-30 00:00:00', '2025-05-31 00:00:00', 'Standar', 1, 'Terbayar', '&iWW2!Dt', 'Standar (Tidak termasuk sarapan)', 275),
('Boni', 'humannobo@gmail.com', '12364789', '2025-06-09 00:00:00', '2025-06-12 00:00:00', 'Standar', 1, 'Belum Dibayar', '+Oormii+', 'Termasuk sarapan', 975),
('Hafizh Ammar Muflih', 'humannobo@gmail.com', '628885461046', '2025-05-31 00:00:00', '2025-06-01 00:00:00', 'Standar', 1, 'Belum Dibayar', '2D+76WmC', 'Standar (Termasuk sarapan)', 275),
('Hafizh', '24050974055@mhs.unesa.ac.id', '6281234567', '2025-05-28 00:00:00', '2025-05-29 00:00:00', 'Deluxe', 1, 'Belum Dibayar', 'a67-LbH4', 'Deluxe (Termasuk sarapan)', 275),
('Septian', '24050974055@mhs.unesa.ac.id', '628885461046', '2025-06-06 00:00:00', '2025-06-08 00:00:00', 'Premium', 1, 'Belum Dibayar', 'GUr898du', 'Termasuk sarapan', 800),
('hafizh ammar muflih', 'humannobo@gmail.com', '62888541064', '2025-06-09 00:00:00', '2025-06-11 00:00:00', 'Deluxe', 1, 'Terbayar', 'mmXwT76Y', 'Termasuk sarapan', 1150),
('Muflih', '24050974055@mhs.unesa.ac.id', '628885461046', '2025-07-01 00:00:00', '2025-07-03 00:00:00', 'Standar', 1, 'Terbayar', 'T#M4sT$5', 'Standar (Tidak termasuk sarapan)', 550),
('Muflih', 'hafizhammarmuflih2@gmail.com', '628885461046', '2025-06-02 00:00:00', '2025-06-04 00:00:00', 'Deluxe', 1, 'Belum Dibayar', 'V6qLPO-u', 'Deluxe (Tidak termasuk sarapan)', 550),
('Ammar', 'hafizhammarmuflih2@gmail.com', '628885461046', '2026-06-01 00:00:00', '2026-06-03 00:00:00', 'Deluxe', 1, 'Check In', 'VW5B+Gwx', 'Deluxe (Tidak termasuk sarapan)', 550);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id_pesanan`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
