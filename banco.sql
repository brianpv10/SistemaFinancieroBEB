-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-05-2026 a las 03:47:38
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `banco`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `cedula` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `nombre`, `cedula`) VALUES
(1, 'luis miguel', '456123'),
(2, 'jose', '123456'),
(3, 'mario', '14725836');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuenta`
--

CREATE TABLE `cuenta` (
  `id_cuenta` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `tipo` enum('corriente','ahorro','cdt') NOT NULL,
  `saldo` double DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cuenta`
--

INSERT INTO `cuenta` (`id_cuenta`, `id_cliente`, `tipo`, `saldo`) VALUES
(1, 1, 'corriente', 9850),
(2, 1, 'ahorro', 6500),
(3, 1, 'cdt', 0),
(4, 2, 'corriente', 4000),
(5, 2, 'ahorro', 3100),
(6, 2, 'cdt', 4500),
(7, 3, 'corriente', 10500),
(8, 3, 'ahorro', 5500),
(9, 3, 'cdt', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `transaccion`
--

CREATE TABLE `transaccion` (
  `id_transaccion` int(11) NOT NULL,
  `id_cuenta` int(11) NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `monto` double NOT NULL,
  `fecha` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `transaccion`
--

INSERT INTO `transaccion` (`id_transaccion`, `id_cuenta`, `tipo`, `monto`, `fecha`) VALUES
(1, 3, 'Abrir CDT', 4500, '2026-04-28 23:57:45'),
(2, 1, 'Consignar corriente', 5600, '2026-04-28 23:58:51'),
(3, 2, 'Consignar ahorro', 7500, '2026-04-28 23:58:57'),
(4, 1, 'Retirar corriente', 250, '2026-04-29 00:00:59'),
(5, 2, 'Retirar ahorro', 500, '2026-04-29 00:01:33'),
(6, 3, 'Cerrar CDT', 4500, '2026-04-29 00:03:03'),
(7, 2, 'Retirar ahorro', 500, '2026-04-29 00:04:41'),
(8, 6, 'Abrir CDT', 4500, '2026-04-29 12:35:32'),
(9, 4, 'Consignar corriente', 5000, '2026-04-29 12:35:38'),
(10, 5, 'Consignar ahorro', 4000, '2026-04-29 12:35:43'),
(11, 4, 'Retirar corriente', 500, '2026-04-29 12:46:14'),
(12, 5, 'Retirar ahorro', 450, '2026-04-29 12:46:20'),
(13, 4, 'Retirar corriente', 500, '2026-04-29 12:49:02'),
(14, 5, 'Retirar ahorro', 450, '2026-04-29 12:49:06'),
(15, 9, 'Abrir CDT', 6000, '2026-05-11 20:04:43'),
(16, 7, 'Consignar corriente', 4500, '2026-05-11 20:05:10'),
(17, 8, 'Consignar ahorro', 5500, '2026-05-11 20:05:35'),
(18, 7, 'Retirar corriente', 500, '2026-05-12 01:29:11'),
(19, 8, 'Retirar ahorro', 600, '2026-05-12 01:43:14'),
(20, 9, 'Cerrar CDT', 6000, '2026-05-12 13:47:11'),
(21, 9, 'Cerrar CDT', 6000, '2026-05-14 14:28:43'),
(22, 9, 'Cerrar CDT', 6000, '2026-05-14 14:49:20');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`),
  ADD UNIQUE KEY `cedula` (`cedula`);

--
-- Indices de la tabla `cuenta`
--
ALTER TABLE `cuenta`
  ADD PRIMARY KEY (`id_cuenta`),
  ADD KEY `id_cliente` (`id_cliente`);

--
-- Indices de la tabla `transaccion`
--
ALTER TABLE `transaccion`
  ADD PRIMARY KEY (`id_transaccion`),
  ADD KEY `id_cuenta` (`id_cuenta`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `cuenta`
--
ALTER TABLE `cuenta`
  MODIFY `id_cuenta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `transaccion`
--
ALTER TABLE `transaccion`
  MODIFY `id_transaccion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cuenta`
--
ALTER TABLE `cuenta`
  ADD CONSTRAINT `cuenta_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`);

--
-- Filtros para la tabla `transaccion`
--
ALTER TABLE `transaccion`
  ADD CONSTRAINT `transaccion_ibfk_1` FOREIGN KEY (`id_cuenta`) REFERENCES `cuenta` (`id_cuenta`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
