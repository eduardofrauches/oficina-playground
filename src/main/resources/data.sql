-- data.sql — SOMENTE CLIENTES (seguro para o schema atual)
-- Obs.: Mantém ON CONFLICT para ser idempotente.

INSERT INTO roles (tipo, descricao) VALUES
  ('ADMIN',     'Administradores do Sistema'),
  ('ATENDENTE', 'Atendente da Oficina'),
  ('MECANICO',  'Mecânico responsável pela manutenção dos veículos'),
  ('CLIENTE',   'Cliente que irá visualizar suas ordens de serviços e orçamentos.')
ON CONFLICT (tipo) DO NOTHING;

-- USUÁRIO ADMIN (senha = "12345", já hasheada com BCrypt cost 10)
INSERT INTO usuarios (id, username, nome, email, password, ativo, role_id)
VALUES (1, 'admin', 'Administrador', 'admin@oficina.local',
        '$2b$10$gy/opxKgad0pDVHYtvR/z.Zy8Qh286dmK2Akke.e7YyapVrdM83UO', true, 1),
        (2, 'José', 'jose', 'jose_mecanico@oficina.local',
        '$2b$10$gy/opxKgad0pDVHYtvR/z.Zy8Qh286dmK2Akke.e7YyapVrdM83UO', true, 3),
        (3, 'Joana', 'joana', 'joana_atendente@oficina.local',
        '$2b$10$gy/opxKgad0pDVHYtvR/z.Zy8Qh286dmK2Akke.e7YyapVrdM83UO', true, 2)        

ON CONFLICT DO NOTHING;


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

-- Ajuste da sequência (só se houver sequence)
SELECT setval(pg_get_serial_sequence('clientes','id'),
              COALESCE((SELECT MAX(id) FROM clientes), 0) + 1, false)
WHERE pg_get_serial_sequence('clientes','id') IS NOT NULL;


-- Exemplo: placas únicas e clientes 1..3 já existentes
INSERT INTO veiculos (placa, marca, modelo, ano, cor, observacoes, cliente_id, ativo) VALUES
('ABC1D23', 'Fiat', 'Argo', 2020, 'Prata', 'Revisão recente', 1, true)
ON CONFLICT (placa) DO NOTHING;

INSERT INTO veiculos (placa, marca, modelo, ano, cor, observacoes, cliente_id, ativo) VALUES
('DEF4G56', 'VW', 'Polo', 2019, 'Branco', null, 2, true)
ON CONFLICT (placa) DO NOTHING;

INSERT INTO veiculos (placa, marca, modelo, ano, cor, observacoes, cliente_id, ativo) VALUES
('HIJ7K89', 'Chevrolet', 'Onix', 2021, 'Preto', 'Troca de pneus recente', 3, true)
ON CONFLICT (placa) DO NOTHING;


INSERT INTO servicos (id, nome, descricao, preco, tempo_estimado, categoria, ativo, data_cadastro, data_atualizacao) VALUES
  (1, 'Troca de Óleo',     'Troca completa de óleo do motor',                   150.00, 60, 'MECANICO', TRUE, NOW(), NULL),
  (2, 'Alinhamento',       'Alinhamento e balanceamento de direção',           100.00, 90,  'SUSPENSAO', TRUE, NOW(), NULL),
  (3, 'Revisão Completa',  'Revisão geral do veículo com troca de filtros',    300.00, 120, 'MECANICO', TRUE, NOW(), NULL),
  (4, 'Troca de Pastilhas','Substituição das pastilhas de freio dianteiras',   200.00, 45, 'FREIOS',  TRUE, NOW(), NULL)
ON CONFLICT (id) DO NOTHING;
