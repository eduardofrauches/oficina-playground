-- === SCHEMA EXPLÍCITO (PostgreSQL) ===


CREATE TABLE IF NOT EXISTS roles (
  id        BIGSERIAL PRIMARY KEY,
  tipo      VARCHAR(40)  NOT NULL UNIQUE,
  descricao VARCHAR(200) NOT NULL
);


-- ===========================
-- USUÁRIOS
-- ===========================
CREATE TABLE IF NOT EXISTS usuarios (
  id          BIGSERIAL PRIMARY KEY,
  username    VARCHAR(100)  NOT NULL UNIQUE,
  nome        VARCHAR(255),
  email       VARCHAR(255),
  password    VARCHAR(255)  NOT NULL,
  role        VARCHAR(50),        -- legado (texto)
  role_id     BIGINT,             -- FK para roles
  ativo       BOOLEAN       NOT NULL DEFAULT TRUE,
  criado_em   TIMESTAMP     NOT NULL DEFAULT NOW()
);

-- ===========================
-- CLIENTES
-- ===========================
CREATE TABLE IF NOT EXISTS clientes (
  id                    BIGSERIAL PRIMARY KEY,
  nome                  VARCHAR(255)   NOT NULL,
  cpf                   VARCHAR(14),   -- aceita com ou sem máscara
  cnpj                  VARCHAR(18),   -- aceita com ou sem máscara
  email                 VARCHAR(255),
  telefone              VARCHAR(20),

  -- Endereco (objeto embutido)
  endereco_logradouro   VARCHAR(255),
  endereco_numero       VARCHAR(50),
  endereco_complemento  VARCHAR(255),
  endereco_bairro       VARCHAR(255),
  endereco_cidade       VARCHAR(255),
  endereco_estado       VARCHAR(2),
  endereco_cep          VARCHAR(20),

  data_nascimento       DATE,
  observacao            TEXT,

  ativo                 BOOLEAN        NOT NULL DEFAULT TRUE,
  criado_em             TIMESTAMP      NOT NULL DEFAULT NOW(),
  atualizado_em         TIMESTAMP      NOT NULL DEFAULT NOW()
);

-- Índices
CREATE INDEX IF NOT EXISTS idx_clientes_nome_lower ON clientes (LOWER(nome));
CREATE INDEX IF NOT EXISTS ix_clientes_cpf ON clientes (cpf);
CREATE INDEX IF NOT EXISTS ix_clientes_cnpj ON clientes (cnpj);

-- ===========================
-- VEÍCULOS
-- ===========================
CREATE TABLE IF NOT EXISTS veiculos (
  id                BIGSERIAL PRIMARY KEY,
  placa             VARCHAR(10)        NOT NULL,
  marca             VARCHAR(100),
  modelo            VARCHAR(100),
  ano               INTEGER,
  cor               VARCHAR(50),
  observacoes       TEXT,
  cliente_id        BIGINT,
  ativo             BOOLEAN            NOT NULL DEFAULT TRUE,
  criado_em         TIMESTAMP          NOT NULL DEFAULT NOW(),
  atualizado_em     TIMESTAMP          NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX IF NOT EXISTS ux_veiculos_placa ON veiculos (UPPER(placa));
CREATE INDEX IF NOT EXISTS idx_veiculos_cliente ON veiculos (cliente_id);
CREATE INDEX IF NOT EXISTS idx_veiculos_modelo_lower ON veiculos (LOWER(modelo));

-- ===========================
-- PRODUTOS
-- ===========================
-- categoria: 'PECA' | 'INSUMO'
CREATE TABLE IF NOT EXISTS produtos (
  id                  BIGSERIAL PRIMARY KEY,
  nome_produto        VARCHAR(255)     NOT NULL,
  descricao_produto   TEXT,
  categoria           VARCHAR(20)      NOT NULL,
  ativo               BOOLEAN          NOT NULL DEFAULT TRUE,
  preco_final_venda   NUMERIC(15,2)    NOT NULL,
  criado_em           TIMESTAMP        NOT NULL DEFAULT NOW(),
  atualizado_em       TIMESTAMP        NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_produtos_nome_lower ON produtos (LOWER(nome_produto));
CREATE INDEX IF NOT EXISTS idx_produtos_categoria_ativo ON produtos (categoria, ativo);

-- ===========================
-- SERVIÇOS
-- ===========================
CREATE TABLE IF NOT EXISTS servicos (
  id                BIGSERIAL PRIMARY KEY,
  nome              VARCHAR(255)    NOT NULL,
  descricao         TEXT,
  preco             NUMERIC(15,2)   NOT NULL,
  tempo_estimado    NUMERIC        NOT NULL,   
  categoria         VARCHAR(30)     NOT NULL,   -- ex.: 'MECANICO' | 'SUSPENSAO' | 'FREIOS'
  ativo             BOOLEAN         NOT NULL DEFAULT TRUE,
  data_cadastro     TIMESTAMP       NOT NULL DEFAULT NOW(),
  data_atualizacao  TIMESTAMP
);

-- Índices úteis
CREATE INDEX IF NOT EXISTS idx_servicos_nome_lower   ON servicos (LOWER(nome));
CREATE INDEX IF NOT EXISTS idx_servicos_categoria_at ON servicos (categoria, ativo);
