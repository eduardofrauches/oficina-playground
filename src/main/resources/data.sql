-- =====================================================================
-- data.sql — seed inicial coerente com o schema atual (PostgreSQL)
-- Padrão: INSERT ... ON CONFLICT (id) DO NOTHING
-- Observação: usar apenas a tabela "ordens_servico" (plural)
-- =====================================================================

-- ================================
-- USUÁRIOS
-- ================================
/*INSERT INTO usuarios (id, username, nome, password, role, ativo) VALUES
                                                                     (1, 'admin',   'admin',      '$2a$10$U48YT31YHCC298YLckXz6.gJNvbRqjgeqsco1XnOhteSSDjt6/mXW', 'ADMIN',    true),
                                                                     (2, 'cliente', '',           '$2a$10$MDwScT7g1tbN2h/mOdThu.ETTkkZJRTVATQDEy4fchuAzr7R6M4oy', 'USER',     true),
                                                                     (3, 'mecanico','Mecanico 1', '$2a$10$xpW8M9QsFbnPV1Bo/Mif..6QRwvixw8fPGG751VH3XGuBDIzUhZnG', 'MECANICO', true)
    ON CONFLICT (id) DO NOTHING;
ALTER TABLE usuarios ALTER COLUMN id RESTART WITH 4;

-- ================================
-- SERVIÇOS DISPONÍVEIS
-- ================================
INSERT INTO servicos (id, nome, descricao, preco, tempo_estimado, categoria, data_cadastro, ativo) VALUES
                                                                                                       (1, 'Troca de Óleo',     'Troca completa de óleo do motor',                150.00,  60, 'MECANICO',  CURRENT_TIMESTAMP, true),
                                                                                                       (2, 'Alinhamento',       'Alinhamento e balanceamento de direção',         100.00,  90, 'SUSPENSAO', CURRENT_TIMESTAMP, true),
                                                                                                       (3, 'Revisão Completa',  'Revisão geral do veículo com troca de filtros',  300.00, 120, 'MECANICO',  CURRENT_TIMESTAMP, true),
                                                                                                       (4, 'Troca de Pastilhas','Substituição das pastilhas de freio dianteiras', 200.00,  45, 'FREIOS',    CURRENT_TIMESTAMP, true)
    ON CONFLICT (id) DO NOTHING;
*/
-- ================================
-- CLIENTES
-- ================================
INSERT INTO clientes (
    id, nome, cpf, cnpj, email, telefone, data_cadastro, data_nascimento, observacao,
    logradouro, numero, complemento, bairro, cidade, estado, cep
) VALUES
      (1,'João da Silva','123.456.789-01',NULL,'joao.silva@email.com','(11) 98765-4321',
       CURRENT_TIMESTAMP,'1985-03-15','Cliente antigo.','Rua das Flores','123','Apto 4B','Jardim das Rosas','São Paulo','SP','01234-567'),
      (2,'Maria Oliveira','987.654.321-02',NULL,'maria.o@email.com','(21) 91234-5678',
       CURRENT_TIMESTAMP,'1992-11-20',NULL,'Avenida Copacabana','456',NULL,'Copacabana','Rio de Janeiro','RJ','22020-001'),
      (3,'Transportadora Veloz Ltda',NULL,'12.345.678/0001-99','contato@veloztransp.com.br','(31) 3344-5566',
       CURRENT_TIMESTAMP,NULL,'Empresa parceira.','Rua dos Aimorés','789','Galpão 2','Centro','Belo Horizonte','MG','30140-070'),
      (4,'Pedro Santos','111.222.333-04',NULL,'pedro.santos@email.com','(51) 99988-7766',
       CURRENT_TIMESTAMP,'1978-07-01',NULL,'Travessa dos Ventos','10',NULL,'Moinhos de Vento','Porto Alegre','RS','90570-080')
    ON CONFLICT (id) DO NOTHING;
ALTER TABLE clientes ALTER COLUMN id RESTART WITH 5;

-- ================================
-- VEÍCULOS
-- ================================
INSERT INTO veiculos (id, placa, modelo, marca, ano, cor, cliente_id) VALUES
                                                                          (1, 'ABC1A23', 'Fusca', 'Volkswagen', 1975, 'azul',   1),
                                                                          (2, 'XYZ9B87', 'Civic', 'Honda',      2020, 'preto',  2),
                                                                          (3, 'LMN3C45', 'F-250', 'Ford',       2018, 'branco', 3),
                                                                          (4, 'QWE4D56', 'Onix',  'Chevrolet',  2021, 'prata',  4)
    ON CONFLICT (id) DO NOTHING;
ALTER TABLE veiculos ALTER COLUMN id RESTART WITH 5;
/*
-- ================================
-- CATÁLOGO DE PRODUTOS (produto_catalogo)
-- ================================
INSERT INTO produto_catalogo (id, nome_produto, descricao_produto, categoria, ativo, preco_final_venda) VALUES
                                                                                                            (1, 'Filtro de Óleo Wega', 'Filtro para motor 1.0/1.6',               'PECA',   true,  89.90),
                                                                                                            (2, 'Óleo 5W30 Sintético', 'Lubrificante sintético para motores flex','INSUMO', true,  59.90),
                                                                                                            (3, 'Pastilha de Freio',   'Pastilha dianteira padrão Bosch',         'PECA',   true, 129.00)
    ON CONFLICT (id) DO NOTHING;
ALTER TABLE produto_catalogo ALTER COLUMN id RESTART WITH 4;

-- ================================
-- ENTRADAS DE ESTOQUE (produto_entrada_estoque)
-- ================================
-- Campos: id, produto_catalogo_id, usuario_id, quantidade, sku,
--         preco_compra_unidade, hash_lote, observacao, data_entrada
INSERT INTO produto_entrada_estoque (
    id, produto_catalogo_id, usuario_id, quantidade, sku,
    preco_compra_unidade, hash_lote, observacao, data_entrada
) VALUES
      (1, 1, 1, 10, 'FO-WEGA-001',     25.00, 'LOTE-001', NULL, CURRENT_TIMESTAMP),
      (2, 2, 1, 20, 'OLEO-SYN-002',    35.00, 'LOTE-002', NULL, CURRENT_TIMESTAMP),
      (3, 3, 1, 15, 'PAST-BOSCH-003',  40.00, 'LOTE-003', NULL, CURRENT_TIMESTAMP)
    ON CONFLICT (id) DO NOTHING;
ALTER TABLE produto_entrada_estoque ALTER COLUMN id RESTART WITH 4;

-- ================================
-- SALDO AGREGADO (produto_estoque)
-- ================================
-- Campos: id, produto_catalogo_id, quantidade_total, quantidade_reservada,
--         quantidade_disponivel, preco_custo_medio, preco_medio_sugerido, updated_at
INSERT INTO produto_estoque (
    id, produto_catalogo_id, quantidade_total, quantidade_reservada, quantidade_disponivel,
    preco_custo_medio, preco_medio_sugerido, updated_at
) VALUES
      (1, 1, 10, 0, 10, 25.00, 32.50, CURRENT_TIMESTAMP),
      (2, 2, 20, 0, 20, 35.00, 45.50, CURRENT_TIMESTAMP),
      (3, 3, 15, 0, 15, 40.00, 52.00, CURRENT_TIMESTAMP)
    ON CONFLICT (id) DO NOTHING;
ALTER TABLE produto_estoque ALTER COLUMN id RESTART WITH 4;

-- ================================
-- ORÇAMENTOS (usar APENAS "orcamento")
-- ================================
-- Colunas: id, ordem_servico_id, data_cadastro, valor_total, status
INSERT INTO orcamento (
    id, data_criacao, valor_total, status_orcamento
) VALUES
    (1, CURRENT_TIMESTAMP, 500.00, 'PENDENTE'),
    (2, CURRENT_TIMESTAMP, 300.00, 'APROVADO'),
    (3, CURRENT_TIMESTAMP, 150.00, 'PENDENTE'),
    (4, CURRENT_TIMESTAMP, 700.00, 'APROVADO'),
    (5, CURRENT_TIMESTAMP, 150.00, 'PENDENTE'),
    (6, CURRENT_TIMESTAMP, 700.00, 'APROVADO'),
    (7, CURRENT_TIMESTAMP, 150.00, 'PENDENTE'),
    (8, CURRENT_TIMESTAMP, 700.00, 'APROVADO')

    ON CONFLICT (id) DO NOTHING;
ALTER TABLE orcamento ALTER COLUMN id RESTART WITH 3;

-- ================================
-- ORDENS DE SERVIÇO (usar APENAS "ordem_servico")
-- ================================
-- Colunas: id, data_cadastro, data_fechamento, status, cliente_id, veiculo_id, mecanico_id
/* INSERT INTO ordem_servico (
    id, data_criacao, data_termino_execucao, status, cliente_id, veiculo_id, mecanico_id, orcamento_id
) VALUES
      (1001, CURRENT_TIMESTAMP, NULL, 'AGUARDANDO_APROVACAO', 1, 1, 3, 2),
      (1002, CURRENT_TIMESTAMP, NULL, 'RECEBIDA', 2, 2, 3, null),
      (1003, CURRENT_TIMESTAMP, NULL, 'AGUARDANDO_APROVACAO', 3, 3, 3, 1)
    ON CONFLICT (id) DO NOTHING;
ALTER TABLE ordem_servico ALTER COLUMN id RESTART WITH 1004;*/

INSERT INTO ordem_servico (
    id, data_criacao, data_inicio_execucao, data_termino_execucao, data_entrega, status, cliente_id, veiculo_id, mecanico_id, orcamento_id, observacoes
) VALUES
      -- RECEBIDA: apenas data_criacao preenchida
      (1, CURRENT_TIMESTAMP, NULL, NULL, NULL, 'RECEBIDA', 1, 1, 3, NULL, 'Ordem recebida'),
      -- EM_DIAGNOSTICO: data_inicio_execucao preenchida
      (2, CURRENT_TIMESTAMP, NULL, NULL, NULL, 'EM_DIAGNOSTICO', 2, 2, 3, NULL, 'Em diagnóstico'),
      -- EM_EXECUCAO: data_inicio_execucao preenchida
      (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '4' DAY, NULL, NULL, 'EM_EXECUCAO', 3, 3, 3, 1, 'Em execução'),

      (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '1' DAY, NULL, NULL, 'EM_EXECUCAO', 4, 4, 3, 4, 'Em execução'),
      -- FINALIZADA: data_termino_execucao preenchida
      (5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '3' DAY, CURRENT_TIMESTAMP + INTERVAL '5' DAY, NULL, 'FINALIZADA', 1, 2, 3, 2, 'Finalizada'),
      -- ENTREGUE: todas as datas preenchidas
      (6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '4' DAY, CURRENT_TIMESTAMP + INTERVAL '15' DAY, CURRENT_TIMESTAMP + INTERVAL '16' DAY, 'ENTREGUE', 2, 3, 3, 5, 'Entregue ao cliente'),
      (7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '3' DAY, CURRENT_TIMESTAMP + INTERVAL '8' DAY, CURRENT_TIMESTAMP + INTERVAL '10' DAY, 'ENTREGUE', 2, 3, 3, 6, 'Entregue ao cliente'),
      (8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '2' DAY, CURRENT_TIMESTAMP + INTERVAL '8' DAY, CURRENT_TIMESTAMP + INTERVAL '14' DAY, 'ENTREGUE', 2, 3, 3, 7, 'Entregue ao cliente'),
      (9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '6' DAY, CURRENT_TIMESTAMP + INTERVAL '20' DAY, CURRENT_TIMESTAMP + INTERVAL '21' DAY, 'ENTREGUE', 2, 3, 3, 8, 'Entregue ao cliente'),

      -- CANCELADA: apenas data_criacao preenchida
      (10, CURRENT_TIMESTAMP, NULL, NULL, NULL, 'CANCELADA', 3, 1, 3, NULL, 'Cancelada pelo cliente'),
      -- AGUARDANDO_APROVACAO: apenas data_criacao preenchida
      (11, CURRENT_TIMESTAMP, NULL, NULL, NULL, 'AGUARDANDO_APROVACAO', 1, 3, 3, 3, 'Aguardando aprovação');

-- Reiniciar sequência do ID (ajuste o nome da sequence conforme seu banco)
ALTER SEQUENCE ordem_servico_id_seq RESTART WITH 12;

-- ================================
-- RESERVAS DE ESTOQUE
-- ================================
-- Colunas: id, produto_estoque_id, ordem_servico_id, quantidade, data_reserva, ativa
INSERT INTO reserva_estoque (
    id, produto_estoque_id, ordem_servico_id, quantidade, data_reserva, ativa
) VALUES
      (1, 1, 1, 2, CURRENT_TIMESTAMP, true),
      (2, 3, 2, 5, CURRENT_TIMESTAMP, true)
    ON CONFLICT (id) DO NOTHING;
ALTER TABLE reserva_estoque ALTER COLUMN id RESTART WITH 3;
*/
-- =====================================================================
-- FIM
-- =====================================================================
