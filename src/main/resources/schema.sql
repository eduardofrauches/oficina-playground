-- Tabela de roles (somente banco, sem endpoints)
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    tipo VARCHAR(40) NOT NULL UNIQUE,
    descricao VARCHAR(200) NOT NULL
);


ALTER TABLE usuarios ADD CONSTRAINT fk_usuarios_role FOREIGN KEY (role_id) REFERENCES roles(id);