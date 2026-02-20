-- Script para crear la tabla VIEWED en Oracle
-- Esta tabla almacena el registro de elementos visualizados por los usuarios

-- Crear secuencia para el ID de la tabla viewed
CREATE SEQUENCE SEQ_VIEWED_ID
  START WITH 1
  INCREMENT BY 1
  NOCYCLE;

-- Crear la tabla viewed
CREATE TABLE viewed (
    id_viewed NUMBER PRIMARY KEY DEFAULT SEQ_VIEWED_ID.NEXTVAL,
    id_material NUMBER NOT NULL,
    id_element NUMBER NOT NULL,
    id_user NUMBER NOT NULL,
    viewed_date TIMESTAMP DEFAULT SYSTIMESTAMP,
    CONSTRAINT uk_viewed_element UNIQUE (id_material, id_element, id_user),
    CONSTRAINT fk_viewed_user FOREIGN KEY (id_user) REFERENCES users(id_user)
);

-- Comentarios de la tabla
COMMENT ON TABLE viewed IS 'Tabla que almacena el registro de elementos visualizados por los usuarios';
COMMENT ON COLUMN viewed.id_viewed IS 'Identificador único del registro de visualización';
COMMENT ON COLUMN viewed.id_material IS 'Tipo de material (1=Película, 2=Serie, 3=Capítulo, 4=Libro, 5=Revista)';
COMMENT ON COLUMN viewed.id_element IS 'ID del elemento específico (película, serie, etc.)';
COMMENT ON COLUMN viewed.id_user IS 'ID del usuario que visualizó el elemento';
COMMENT ON COLUMN viewed.viewed_date IS 'Fecha y hora de visualización';

-- Crear índices para optimizar búsquedas
CREATE INDEX idx_viewed_user ON viewed(id_user);
CREATE INDEX idx_viewed_material_element ON viewed(id_material, id_element);
CREATE INDEX idx_viewed_material_element_user ON viewed(id_material, id_element, id_user);

-- Commit para guardar los cambios
COMMIT;

-- Consultas de prueba (ejecutar después de crear datos)
-- SELECT * FROM viewed WHERE id_user = 1;
-- SELECT * FROM viewed WHERE id_material = 1 AND id_user = 1; -- Películas vistas por usuario 1
