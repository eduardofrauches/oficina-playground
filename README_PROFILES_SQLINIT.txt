# Habilitando o `data.sql` no estilo do projeto antigo (Spring Boot + perfis)

Este pacote adiciona **profiles** (`local` e `docker`) e configura o Spring Boot para:
- Criar o **DataSource**
- Inicializar o **schema** via Hibernate (`ddl-auto: update` por padrão)
- Executar o **data.sql** automaticamente ao subir (`spring.sql.init.mode=always`)
- Garantir a ordem correta com `spring.jpa.defer-datasource-initialization=true`

## Arquivos incluídos
- `src/main/resources/application.yaml` (default: `local`)
- `src/main/resources/application-local.yaml` (DB local)
- `src/main/resources/application-docker.yaml` (DB no serviço `db` do Docker Compose)
- `docker-compose.app-env.patch.yml` (opcional: ativa o profile `docker` no serviço `app`)

> **Importante:** o seu `data.sql` deve estar em `src/main/resources/data.sql`.

## Como usar (LOCAL)
1) Ajuste usuário/senha/URL em `application-local.yaml` se necessário.
2) Rode:
   ```bash
   mvn -q -DskipTests clean package
   mvn spring-boot:run
   ```
   O profile `local` é o padrão (vem de `application.yaml`).  
   O `data.sql` será executado automaticamente.

## Como usar (DOCKER COMPOSE)
### Opção A — passando perfil por override YAML
```bash
docker compose -f docker-compose.yml -f docker-compose.app-env.patch.yml up --build
```
### Opção B — definindo env direto no seu compose
Adicione em `services.app.environment`:
```yaml
SPRING_PROFILES_ACTIVE: docker
```
> Certifique-se de que seu Postgres do compose use host `db` e credenciais `oficina/oficina` para coincidir com `application-docker.yaml` (ou ajuste o YAML).

## Observações de Clean Architecture
- Domínio e casos de uso permanecem **puros** (sem dependências de framework).
- A configuração de DataSource/JPA/SQL init fica **na camada de frameworks & drivers** (adapters/config).
- Você pode continuar usando o seu `ClienteRepository` (adapter) normalmente — nada muda no domínio.

## Problemas comuns
- **`data.sql` não executa**: verifique se o **profile ativo** é `local` ou `docker` e se o arquivo está em `src/main/resources/data.sql`.
- **Esquema não existe**: mude `hibernate.ddl-auto` para `create` ou inclua o DDL no seu `data.sql`.
- **Conflito com perfis**: se ainda estiver usando o repositório em memória no profile `dev`, ele **não toca no banco** (e portanto `data.sql` não influi nesse perfil).
