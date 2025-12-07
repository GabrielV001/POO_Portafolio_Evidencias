-- Crear la base de datos
DROP DATABASE IF EXISTS pozo_tenis;
CREATE DATABASE pozo_padel;
USE pozo_padel;

-- Tabla de jugadores
CREATE TABLE jugadores (
    id VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nivel VARCHAR(50) NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado BOOLEAN DEFAULT TRUE,
    CONSTRAINT chk_nivel CHECK (nivel IN ('Principiante', 'Intermedio', 'Avanzado')),
    CONSTRAINT chk_nombre_length CHECK (LENGTH(nombre) >= 3)
);

-- Tabla de parejas
CREATE TABLE parejas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    jugador1_id VARCHAR(10) NOT NULL,
    jugador2_id VARCHAR(10) NOT NULL,
    fecha_formacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (jugador1_id) REFERENCES jugadores(id) ON DELETE RESTRICT,
    FOREIGN KEY (jugador2_id) REFERENCES jugadores(id) ON DELETE RESTRICT,
    CONSTRAINT chk_diferentes_jugadores CHECK (jugador1_id != jugador2_id),
    CONSTRAINT uk_pareja UNIQUE (jugador1_id, jugador2_id)
);

-- Tabla de partidos
CREATE TABLE partidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pareja1_id INT NOT NULL,
    pareja2_id INT NOT NULL,
    resultado VARCHAR(50) DEFAULT 'Pendiente',
    fecha_partido DATETIME,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'Pendiente',
    FOREIGN KEY (pareja1_id) REFERENCES parejas(id) ON DELETE RESTRICT,
    FOREIGN KEY (pareja2_id) REFERENCES parejas(id) ON DELETE RESTRICT,
    CONSTRAINT chk_diferentes_parejas CHECK (pareja1_id != pareja2_id),
    CONSTRAINT chk_estado CHECK (estado IN ('Pendiente', 'En Curso', 'Finalizado', 'Cancelado'))
);

-- Índices para mejorar el rendimiento
CREATE INDEX idx_jugadores_nivel ON jugadores(nivel);
CREATE INDEX idx_parejas_jugadores ON parejas(jugador1_id, jugador2_id);
CREATE INDEX idx_partidos_estado ON partidos(estado);
CREATE INDEX idx_partidos_fecha ON partidos(fecha_partido);

-- Vistas para consultas comunes
CREATE VIEW v_parejas_activas AS
SELECT p.id, 
       j1.nombre AS jugador1_nombre, 
       j1.nivel AS jugador1_nivel,
       j2.nombre AS jugador2_nombre, 
       j2.nivel AS jugador2_nivel,
       p.fecha_formacion
FROM parejas p
JOIN jugadores j1 ON p.jugador1_id = j1.id
JOIN jugadores j2 ON p.jugador2_id = j2.id
WHERE p.estado = TRUE;

CREATE VIEW v_partidos_pendientes AS
SELECT pt.id,
       j1.nombre AS pareja1_jugador1,
       j2.nombre AS pareja1_jugador2,
       j3.nombre AS pareja2_jugador1,
       j4.nombre AS pareja2_jugador2,
       pt.fecha_partido,
       pt.estado
FROM partidos pt
JOIN parejas p1 ON pt.pareja1_id = p1.id
JOIN parejas p2 ON pt.pareja2_id = p2.id
JOIN jugadores j1 ON p1.jugador1_id = j1.id
JOIN jugadores j2 ON p1.jugador2_id = j2.id
JOIN jugadores j3 ON p2.jugador1_id = j3.id
JOIN jugadores j4 ON p2.jugador2_id = j4.id
WHERE pt.estado = 'Pendiente';

-- Procedimientos almacenados útiles
DELIMITER //

CREATE PROCEDURE sp_crear_pareja(
    IN p_jugador1_id VARCHAR(10),
    IN p_jugador2_id VARCHAR(10)
)
BEGIN
    INSERT INTO parejas (jugador1_id, jugador2_id)
    VALUES (p_jugador1_id, p_jugador2_id);
END //

CREATE PROCEDURE sp_crear_partido(
    IN p_pareja1_id INT,
    IN p_pareja2_id INT,
    IN p_fecha_partido DATETIME
)
BEGIN
    INSERT INTO partidos (pareja1_id, pareja2_id, fecha_partido)
    VALUES (p_pareja1_id, p_pareja2_id, p_fecha_partido);
END //

CREATE PROCEDURE sp_actualizar_resultado(
    IN p_partido_id INT,
    IN p_resultado VARCHAR(50)
)
BEGIN
    UPDATE partidos 
    SET resultado = p_resultado,
        estado = 'Finalizado'
    WHERE id = p_partido_id;
END //

DELIMITER ;

-- Datos de prueba
-- Insertar jugadores
INSERT INTO jugadores (id, nombre, nivel) VALUES
('J001', 'Juan Pérez', 'Intermedio'),
('J002', 'María García', 'Avanzado'),
('J003', 'Pedro López', 'Principiante'),
('J004', 'Ana Martínez', 'Intermedio'),
('J005', 'Carlos Rodríguez', 'Avanzado'),
('J006', 'Laura Sánchez', 'Principiante'),
('J007', 'Miguel Torres', 'Intermedio'),
('J008', 'Sofia Ramírez', 'Avanzado');

-- Insertar parejas
INSERT INTO parejas (jugador1_id, jugador2_id) VALUES
('J001', 'J002'),
('J003', 'J004'),
('J005', 'J006'),
('J007', 'J008');

-- Insertar partidos
INSERT INTO partidos (pareja1_id, pareja2_id, fecha_partido, resultado, estado) VALUES
(1, 2, NOW() + INTERVAL 1 DAY, 'Pendiente', 'Pendiente'),
(3, 4, NOW() + INTERVAL 2 DAY, 'Pendiente', 'Pendiente'),
(1, 3, NOW() - INTERVAL 1 DAY, '6-4, 7-5', 'Finalizado'),
(2, 4, NOW() - INTERVAL 2 DAY, '6-3, 6-2', 'Finalizado');

-- Triggers para validaciones adicionales
DELIMITER //

CREATE TRIGGER before_insert_pareja
BEFORE INSERT ON parejas
FOR EACH ROW
BEGIN
    DECLARE count_jugador1 INT;
    DECLARE count_jugador2 INT;
    
    -- Verificar que los jugadores no estén en más de 2 parejas activas
    SELECT COUNT(*) INTO count_jugador1
    FROM parejas
    WHERE (jugador1_id = NEW.jugador1_id OR jugador2_id = NEW.jugador1_id)
    AND estado = TRUE;
    
    SELECT COUNT(*) INTO count_jugador2
    FROM parejas
    WHERE (jugador1_id = NEW.jugador2_id OR jugador2_id = NEW.jugador2_id)
    AND estado = TRUE;
    
    IF count_jugador1 >= 2 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El jugador 1 ya está en el máximo de parejas permitidas';
    END IF;
    
    IF count_jugador2 >= 2 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El jugador 2 ya está en el máximo de parejas permitidas';
    END IF;
END //

CREATE TRIGGER before_insert_partido
BEFORE INSERT ON partidos
FOR EACH ROW
BEGIN
    DECLARE count_partidos INT;
    
    -- Verificar que no haya más de 5 partidos programados para el mismo día
    SELECT COUNT(*) INTO count_partidos
    FROM partidos
    WHERE DATE(fecha_partido) = DATE(NEW.fecha_partido)
    AND estado != 'Cancelado';
    
    IF count_partidos >= 5 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Se ha alcanzado el límite de partidos para este día';
    END IF;
END //

DELIMITER ;

-- Permisos (ajustar según necesidades)
GRANT SELECT, INSERT, UPDATE, DELETE ON pozo_tenis.* TO 'usuario_app'@'localhost';
FLUSH PRIVILEGES;