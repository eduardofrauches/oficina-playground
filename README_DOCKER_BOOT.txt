# Docker Compose + POM (Spring Boot JPA) — pacote pronto

Este pacote complementa o anterior (profiles + SQL init) com:
- `docker-compose.yml` que já ativa o profile `docker` na app
- `Dockerfile` simples (JRE 17)
- `pom.xml` ajustado para `spring-boot-starter-data-jpa` (o Boot gerencia o JPA/DataSource)

## Como aplicar
1) Extraia este ZIP na **raiz** do projeto.
   - Ele vai **substituir** seu `pom.xml` (guarde uma cópia se quiser comparar).
   - Se já possuir `docker-compose.yml`/`Dockerfile`, você pode comparar e mesclar.
2) Garanta que existe `src/main/resources/application-docker.yaml` (veio no pacote anterior).
3) Tenha o seu `src/main/resources/data.sql` com os INSERTs iniciais.

## Rodar no Docker
```bash
mvn -q -DskipTests clean package
docker compose up --build
```
- A app sobe com `SPRING_PROFILES_ACTIVE=docker` e usa as configs do `application-docker.yaml`.
- O Postgres fica acessível em `localhost:5432` (usuário/senha/db: `oficina`).
- O `data.sql` é executado automaticamente ao iniciar, **depois** que o Hibernate cria o schema.

## Rodar localmente (sem Docker)
```bash
mvn spring-boot:run
```
- O perfil `local` é o default no `application.yaml` (do pacote anterior).
- Certifique-se que o seu Postgres local usa URL/credenciais de `application-local.yaml`.

## Observações de Clean Architecture
- O domínio e casos de uso continuam **puros**.
- `spring-boot-starter-data-jpa` é um detalhe de infra (camada frameworks/drivers).
- Seus adapters (`ports` + `adapters`) seguem iguais; apenas a **configuração** é centralizada nos `application-*.yaml`.

## Dicas
- Se você ainda tiver um `persistence.xml`, o Spring Boot pode usá-lo. Caso prefira só as configs do `application-*.yaml`, remova/renomeie o `persistence.xml`.
- Para H2 em memória durante dev, ative um profile específico e aponte `spring.datasource.url=jdbc:h2:mem:...`.
- Logs de SQL: `spring.jpa.show-sql=true` (já habilitado nos YAML).

Swagger: http://localhost:8080/swagger-ui/index.html
