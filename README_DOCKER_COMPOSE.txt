# App + Postgres via Docker Compose

Este pacote prepara sua aplicação para rodar em containers com Postgres.

## O que foi incluído
- `Dockerfile` — cria a imagem da aplicação (usa o JAR gerado pelo Maven).
- `docker-compose.yml` — sobe **db (Postgres 16)** + **app**.
- `src/main/java/br/com/oficina/adapters/repositories/jpa/JpaUtil.java` —
  atualizado para aceitar overrides de JDBC via variáveis de ambiente
  (sem precisar editar `persistence.xml`).

## Passo a passo

1) **Build do JAR**
```bash
mvn -q -DskipTests clean package
```

2) **Extrair este ZIP na raiz do projeto** (vai sobrescrever apenas o `JpaUtil.java` e adicionar os arquivos do Docker).

3) **Subir com Docker Compose**
```bash
docker compose up --build
```

4) **Acessar o Swagger**
```
http://localhost:8080/swagger-ui/index.html
```

## Como funciona a configuração de banco
O `docker-compose.yml` exporta variáveis de ambiente que o `JpaUtil` lê para sobrepor
as propriedades JPA:

- `JPA_JDBC_URL=jdbc:postgresql://db:5432/oficina`
- `JPA_JDBC_USER=oficina`
- `JPA_JDBC_PASSWORD=oficina`
- `JPA_JDBC_DRIVER=org.postgresql.Driver`
- `HIBERNATE_HBM2DDL=update`

Assim você **não precisa alterar** o seu `persistence.xml`. Localmente, fora do Docker,
você pode continuar rodando com `mvn spring-boot:run` (default) ou `-Dspring-boot.run.profiles=dev`
para usar o repositório em memória.

> Observação: se sua PU no `persistence.xml` **não** se chama `oficinaPU`, mude o valor padrão no `JpaUtil`
ou rode com `-DPU_NAME=nomeDaSuaPU`.

## Comandos úteis

- Ver logs:
```bash
docker compose logs -f app
docker compose logs -f db
```

- Resetar o banco (apaga volume):
```bash
docker compose down -v
docker compose up --build
```

- Rodar somente o app (se o db já estiver rodando):
```bash
docker compose up --build app
```

## Problemas comuns

- **A app sobe antes do Postgres**: o compose tem healthcheck; a app espera pelo `service_healthy`.
- **Erro 500 nos endpoints**: verifique os logs do `app` e se o Hibernate criou as tabelas
  (`hibernate.hbm2ddl.auto=update`).
- **Porta 5432 ocupada**: pare outro Postgres local ou altere a porta no `docker-compose.yml`.
