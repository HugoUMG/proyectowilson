-- Migración inicial: tablas para cuentas, asientos y movimientos

CREATE TABLE IF NOT EXISTS cuentas_contables (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  codigo VARCHAR(100) NOT NULL UNIQUE,
  nombre VARCHAR(255) NOT NULL,
  tipo VARCHAR(50),
  saldo_inicial DECIMAL(15,2)
);

CREATE TABLE IF NOT EXISTS asientos_contables (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  fecha DATE NOT NULL,
  descripcion VARCHAR(500),
  referencia VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS movimientos_contables (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  debe DECIMAL(15,2) DEFAULT 0,
  haber DECIMAL(15,2) DEFAULT 0,
  cuenta_id BIGINT,
  asiento_id BIGINT,
  CONSTRAINT fk_movimiento_cuenta FOREIGN KEY (cuenta_id) REFERENCES cuentas_contables(id) ON DELETE SET NULL,
  CONSTRAINT fk_movimiento_asiento FOREIGN KEY (asiento_id) REFERENCES asientos_contables(id) ON DELETE SET NULL
);
