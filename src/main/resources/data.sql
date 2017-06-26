----------------INSERTING FOOD DATA ----------------------------
INSERT INTO dbmigraine.alimento (nombre)
SELECT * FROM (SELECT 'Pollo') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.alimento WHERE nombre = 'Pollo'
) LIMIT 1;

INSERT INTO dbmigraine.alimento (nombre)
SELECT * FROM (SELECT 'Carne') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.alimento WHERE nombre = 'Carne'
) LIMIT 1;

INSERT INTO dbmigraine.alimento (nombre)
SELECT * FROM (SELECT 'Cerdo') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.alimento WHERE nombre = 'Cerdo'
) LIMIT 1;

INSERT INTO dbmigraine.alimento (nombre)
SELECT * FROM (SELECT 'Empanada') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.alimento WHERE nombre = 'Empanada'
) LIMIT 1;

INSERT INTO dbmigraine.alimento (nombre)
SELECT * FROM (SELECT 'Hamburguesa') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.alimento WHERE nombre = 'Hamburguesa'
) LIMIT 1;

---------------- Location -------------------------------------
INSERT INTO dbmigraine.localizacion (nombre)
SELECT * FROM (SELECT 'Frontal') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.localizacion WHERE nombre = 'Frontal'
) LIMIT 1;

INSERT INTO dbmigraine.localizacion (nombre)
SELECT * FROM (SELECT 'Lateral Izquierdo') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.localizacion WHERE nombre = 'Lateral Izquierdo'
) LIMIT 1;

INSERT INTO dbmigraine.localizacion (nombre)
SELECT * FROM (SELECT 'Lateral Derecho') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.localizacion WHERE nombre = 'Lateral Derecho'
) LIMIT 1;

INSERT INTO dbmigraine.localizacion (nombre)
SELECT * FROM (SELECT 'Superior') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.localizacion WHERE nombre = 'Superior'
) LIMIT 1;

INSERT INTO dbmigraine.localizacion (nombre)
SELECT * FROM (SELECT 'Posterior') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.localizacion WHERE nombre = 'Posterior'
) LIMIT 1;

------------------- Medicine -------------------------------------
INSERT INTO dbmigraine.medicamento (nombre)
SELECT * FROM (SELECT 'Acetaminofen') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.medicamento WHERE nombre = 'Acetaminofen'
) LIMIT 1;

INSERT INTO dbmigraine.medicamento (nombre)
SELECT * FROM (SELECT 'Dolex') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.medicamento WHERE nombre = 'Dolex'
) LIMIT 1;

------------------- Physical activity ------------------------------
INSERT INTO dbmigraine.actividad_fisica (nombre)
SELECT * FROM (SELECT 'Caminata') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.actividad_fisica WHERE nombre = 'Caminata'
) LIMIT 1;

INSERT INTO dbmigraine.actividad_fisica (nombre)
SELECT * FROM (SELECT 'Natacion') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.actividad_fisica WHERE nombre = 'Natacion'
) LIMIT 1;

INSERT INTO dbmigraine.actividad_fisica (nombre)
SELECT * FROM (SELECT 'Ciclismo') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.actividad_fisica WHERE nombre = 'Ciclismo'
) LIMIT 1;

------------------- User type ------------------------------
INSERT INTO dbmigraine.tipo_usuario (nombre)
SELECT * FROM (SELECT 'Doctor') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.tipo_usuario WHERE nombre = 'Doctor'
) LIMIT 1;

INSERT INTO dbmigraine.tipo_usuario (nombre)
SELECT * FROM (SELECT 'Paciente') AS tmp
WHERE NOT EXISTS (
    SELECT nombre FROM dbmigraine.tipo_usuario WHERE nombre = 'Paciente'
) LIMIT 1;

---------------- Usuarios ----------------------------------
--------Doctor------------
INSERT INTO dbmigraine.usuario (numero_documento, nombre, id_tipo_usuario)
SELECT * FROM (SELECT '1014207335', 'Doctor Patch Adams', 1) AS tmp
WHERE NOT EXISTS (
    SELECT numero_documento FROM dbmigraine.usuario WHERE numero_documento = '1014207335'
) LIMIT 1;

INSERT INTO dbmigraine.usuario (numero_documento, nombre, id_tipo_usuario)
SELECT * FROM (SELECT '1014207334', 'Doctor House', 1) AS tmp
WHERE NOT EXISTS (
    SELECT numero_documento FROM dbmigraine.usuario WHERE numero_documento = '1014207334'
) LIMIT 1;

--------Paciente----------
INSERT INTO dbmigraine.usuario (numero_documento, nombre, id_tipo_usuario)
SELECT * FROM (SELECT '1014207336', 'Rebbeca Adler', 2) AS tmp
WHERE NOT EXISTS (
    SELECT numero_documento FROM dbmigraine.usuario WHERE numero_documento = '1014207336'
) LIMIT 1;