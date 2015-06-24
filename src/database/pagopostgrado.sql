-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-06-2015 a las 03:38:27
-- Versión del servidor: 5.6.17
-- Versión de PHP: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `pagopostgrado`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciclo`
--

CREATE TABLE IF NOT EXISTS `ciclo` (
  `idCiclo` int(11) NOT NULL AUTO_INCREMENT,
  `NombreCiclo` varchar(45) DEFAULT NULL,
  `CicloAnterior` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCiclo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `ciclo`
--

INSERT INTO `ciclo` (`idCiclo`, `NombreCiclo`, `CicloAnterior`) VALUES
(1, '2014A', NULL),
(2, '2014B', 1),
(3, '2015A', 2),
(4, '2015B', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `curso`
--

CREATE TABLE IF NOT EXISTS `curso` (
  `idCurso` int(11) NOT NULL AUTO_INCREMENT,
  `NombreCurso` varchar(100) DEFAULT NULL,
  `Maestria_idMaestria` int(11) NOT NULL,
  PRIMARY KEY (`idCurso`),
  KEY `fk_Curso_Maestria1_idx` (`Maestria_idMaestria`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `curso`
--

INSERT INTO `curso` (`idCurso`, `NombreCurso`, `Maestria_idMaestria`) VALUES
(1, 'Matemática Aplicada', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `curso_has_profesor`
--

CREATE TABLE IF NOT EXISTS `curso_has_profesor` (
  `Curso_idCurso` int(11) NOT NULL,
  `Curso_Maestria_idMaestria` int(11) NOT NULL,
  `Profesor_idProfesor` int(11) NOT NULL,
  PRIMARY KEY (`Curso_idCurso`,`Curso_Maestria_idMaestria`,`Profesor_idProfesor`),
  KEY `fk_Curso_has_Profesor_Profesor1_idx` (`Profesor_idProfesor`),
  KEY `fk_Curso_has_Profesor_Curso1_idx` (`Curso_idCurso`,`Curso_Maestria_idMaestria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estudiante`
--

CREATE TABLE IF NOT EXISTS `estudiante` (
  `idEstudiante` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `DNI` varchar(11) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `Telefono` varchar(45) DEFAULT NULL,
  `UsuarioRegistro` int(11) NOT NULL,
  `Maestria` int(11) NOT NULL,
  PRIMARY KEY (`idEstudiante`),
  KEY `fk_Estudiante_user1_idx` (`UsuarioRegistro`),
  KEY `fk_estudiante_maestria` (`Maestria`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Volcado de datos para la tabla `estudiante`
--

INSERT INTO `estudiante` (`idEstudiante`, `nombre`, `apellido`, `DNI`, `direccion`, `Telefono`, `UsuarioRegistro`, `Maestria`) VALUES
(1, 'Vaner', 'Anampa Martines', '12345678', '12345678', '12345', 1, 1),
(2, 'Anell', 'Roldan Quinallata', '87654321', 'jr por ahi ', '987654', 1, 2),
(3, 'Ricardo', 'Arana Reyes Guerrero', '12345679', 'Jr Cahuide 1542', '3843758939', 1, 3),
(4, 'Carlos', 'Perez Cuella', '12346579', '12346579', NULL, 1, 4),
(5, 'Renzo', 'Flores Espinoza', '23294949', '23294949', '1234', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo_usuario`
--

CREATE TABLE IF NOT EXISTS `grupo_usuario` (
  `idGrupo` int(11) NOT NULL AUTO_INCREMENT,
  `Grupo_Usuariocol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idGrupo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Volcado de datos para la tabla `grupo_usuario`
--

INSERT INTO `grupo_usuario` (`idGrupo`, `Grupo_Usuariocol`) VALUES
(1, 'admin'),
(2, 'Tesorera');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `maestria`
--

CREATE TABLE IF NOT EXISTS `maestria` (
  `idMaestria` int(11) NOT NULL AUTO_INCREMENT,
  `nombreMaestria` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idMaestria`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `maestria`
--

INSERT INTO `maestria` (`idMaestria`, `nombreMaestria`) VALUES
(1, 'Maestría en Productividad y Relaciones Industriales'),
(2, 'Maestría en Ingeniería con Mención: Gerencia en Logistica'),
(3, 'Maestría en Ingeniería Industrial Mensión: Gerencia de Calidad y La Productividad'),
(4, 'Maestría en Ingeniería de Sistemas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE IF NOT EXISTS `pago` (
  `nroVoucher` varchar(50) NOT NULL,
  `Cuota` varchar(1) DEFAULT NULL,
  `Estudiante_idEstudiante` int(11) NOT NULL,
  `Maestria_idMaestria` int(11) NOT NULL,
  `Ciclo_idCiclo` int(11) NOT NULL,
  `monto` float(15,2) DEFAULT NULL,
  `montoSaldo` float(15,2) NOT NULL,
  `fechaPago` date DEFAULT NULL,
  `fechaRegPago` date DEFAULT NULL,
  PRIMARY KEY (`nroVoucher`),
  KEY `fk_Pago_Estudiante1_idx` (`Estudiante_idEstudiante`),
  KEY `fk_Pago_Maestria1_idx` (`Maestria_idMaestria`),
  KEY `fk_Pago_Ciclo1_idx` (`Ciclo_idCiclo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `pago`
--

INSERT INTO `pago` (`nroVoucher`, `Cuota`, `Estudiante_idEstudiante`, `Maestria_idMaestria`, `Ciclo_idCiclo`, `monto`, `montoSaldo`, `fechaPago`, `fechaRegPago`) VALUES
('123', '0', 1, 1, 1, 200.00, 0.00, '2015-06-09', '2015-06-10'),
('123456', '0', 2, 2, 1, 100.00, 100.00, '2015-06-17', '2015-06-17'),
('12345609', '2', 2, 2, 1, 100.00, 200.00, '2015-06-17', '2015-06-17'),
('1234567', '0', 2, 2, 1, 100.00, 0.00, '2015-06-17', '2015-06-17'),
('12345678', '1', 2, 2, 1, 100.00, 200.00, '2015-06-17', '2015-06-17'),
('123456789', '1', 2, 2, 1, 100.00, 100.00, '2015-06-17', '2015-06-17'),
('12345678910', '1', 2, 2, 1, 100.00, 0.00, '2015-06-17', '2015-06-17');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesor`
--

CREATE TABLE IF NOT EXISTS `profesor` (
  `idProfesor` int(11) NOT NULL AUTO_INCREMENT,
  `nomProfesor` varchar(100) DEFAULT NULL,
  `apellidoProfesor` varchar(100) DEFAULT NULL,
  `DNIprofesor` varchar(12) DEFAULT NULL,
  `telefonoProfesor` varchar(15) DEFAULT NULL,
  `UsuarioReg` varchar(45) DEFAULT NULL,
  `FechaReg` date DEFAULT NULL,
  PRIMARY KEY (`idProfesor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sueldo`
--

CREATE TABLE IF NOT EXISTS `sueldo` (
  `idSueldo` int(11) NOT NULL AUTO_INCREMENT,
  `Maestria_idMaestria` int(11) NOT NULL,
  `Ciclo_idCiclo` int(11) NOT NULL,
  `Profesor_idProfesor` int(11) NOT NULL,
  `monto` float(15,2) DEFAULT NULL,
  `fechaSueldo` date DEFAULT NULL,
  PRIMARY KEY (`idSueldo`,`Maestria_idMaestria`,`Ciclo_idCiclo`,`Profesor_idProfesor`),
  KEY `fk_Sueldo_Maestria1_idx` (`Maestria_idMaestria`),
  KEY `fk_Sueldo_Ciclo1_idx` (`Ciclo_idCiclo`),
  KEY `fk_Sueldo_Profesor1_idx` (`Profesor_idProfesor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sueldototal`
--

CREATE TABLE IF NOT EXISTS `sueldototal` (
  `idSueldoTotal` int(11) NOT NULL,
  `MontoTotal` float(15,2) DEFAULT NULL,
  `Curso_idCurso` int(11) NOT NULL,
  `Ciclo_idCiclo` int(11) NOT NULL,
  PRIMARY KEY (`idSueldoTotal`),
  KEY `fk_SueldoTotal_Curso1_idx` (`Curso_idCurso`),
  KEY `fk_SueldoTotal_Ciclo1_idx` (`Ciclo_idCiclo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `suldototal`
--

CREATE TABLE IF NOT EXISTS `suldototal` (
  `idSuldoTotal` int(11) NOT NULL,
  `Monto` float(15,2) DEFAULT NULL,
  PRIMARY KEY (`idSuldoTotal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(32) NOT NULL,
  `create_time` date DEFAULT NULL,
  `Grupo_Usuario_idGrupo` int(11) NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `username` (`username`),
  KEY `fk_user_Grupo_Usuario1_idx` (`Grupo_Usuario_idGrupo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`idUser`, `username`, `email`, `password`, `create_time`, `Grupo_Usuario_idGrupo`) VALUES
(1, 'administrador', 'ricardo.aranareyes@gmail.com', '123456', '2015-06-01', 1),
(3, 'prueba', NULL, '123456', NULL, 2),
(7, 'prueba3', NULL, '123456', NULL, 2),
(8, 'admin', 'chio@rociocorporationsac.net', '123', NULL, 1);

--
-- Disparadores `user`
--
DROP TRIGGER IF EXISTS `user_BEFORE_INSERT`;
DELIMITER //
CREATE TRIGGER `user_BEFORE_INSERT` BEFORE INSERT ON `user`
 FOR EACH ROW SET @create_time = NOW()
//
DELIMITER ;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `curso`
--
ALTER TABLE `curso`
  ADD CONSTRAINT `fk_Curso_Maestria1` FOREIGN KEY (`Maestria_idMaestria`) REFERENCES `maestria` (`idMaestria`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `curso_has_profesor`
--
ALTER TABLE `curso_has_profesor`
  ADD CONSTRAINT `fk_Curso_has_Profesor_Curso1` FOREIGN KEY (`Curso_idCurso`) REFERENCES `curso` (`idCurso`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Curso_has_Profesor_Profesor1` FOREIGN KEY (`Profesor_idProfesor`) REFERENCES `profesor` (`idProfesor`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `estudiante`
--
ALTER TABLE `estudiante`
  ADD CONSTRAINT `fk_Estudiante_Maestria1` FOREIGN KEY (`Maestria`) REFERENCES `maestria` (`idMaestria`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Estudiante_user1` FOREIGN KEY (`UsuarioRegistro`) REFERENCES `user` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pago`
--
ALTER TABLE `pago`
  ADD CONSTRAINT `fk_Pago_Ciclo1` FOREIGN KEY (`Ciclo_idCiclo`) REFERENCES `ciclo` (`idCiclo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Pago_Estudiante1` FOREIGN KEY (`Estudiante_idEstudiante`) REFERENCES `estudiante` (`idEstudiante`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Pago_Maestria1` FOREIGN KEY (`Maestria_idMaestria`) REFERENCES `maestria` (`idMaestria`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `sueldo`
--
ALTER TABLE `sueldo`
  ADD CONSTRAINT `fk_Sueldo_Ciclo1` FOREIGN KEY (`Ciclo_idCiclo`) REFERENCES `ciclo` (`idCiclo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Sueldo_Maestria1` FOREIGN KEY (`Maestria_idMaestria`) REFERENCES `maestria` (`idMaestria`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Sueldo_Profesor1` FOREIGN KEY (`Profesor_idProfesor`) REFERENCES `profesor` (`idProfesor`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `sueldototal`
--
ALTER TABLE `sueldototal`
  ADD CONSTRAINT `fk_SueldoTotal_Ciclo1` FOREIGN KEY (`Ciclo_idCiclo`) REFERENCES `ciclo` (`idCiclo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_SueldoTotal_Curso1` FOREIGN KEY (`Curso_idCurso`) REFERENCES `curso` (`idCurso`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_Grupo_Usuario1` FOREIGN KEY (`Grupo_Usuario_idGrupo`) REFERENCES `grupo_usuario` (`idGrupo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
