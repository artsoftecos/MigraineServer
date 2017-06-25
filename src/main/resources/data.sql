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