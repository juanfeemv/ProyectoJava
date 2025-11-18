CREATE DATABASE restaurante;
USE restaurante;

CREATE TABLE proveedores (
    id VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(100)
);

CREATE TABLE productos (
    id VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    cantidad INT NOT NULL,
    disponible BOOLEAN NOT NULL,
    categoria VARCHAR(50),
    calorias INT,
    proveedor_id VARCHAR(20),
    FOREIGN KEY (proveedor_id) REFERENCES proveedores(id)
);

SELECT * FROM productos;
SELECT * FROM proveedores;

