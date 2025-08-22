-- data.sql — SOMENTE CLIENTES (seguro para o schema atual)
-- Obs.: Mantém ON CONFLICT para ser idempotente.

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
