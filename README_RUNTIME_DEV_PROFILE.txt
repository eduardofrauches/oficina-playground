
# Dev profile para testar no Swagger sem banco

Este pacote adiciona dois configuradores de repositório usando **Spring Profiles**:

- **dev**: usa `InMemoryClienteRepository` (não precisa de banco).
- **default (!dev)**: usa `JpaClienteRepository` (seu JPA normal).

## Como usar

### 1) Testar no Swagger sem banco (dev)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
# Abra http://localhost:8080/swagger-ui/index.html
```

### 2) Rodar com JPA (sem o profile dev)
```bash
mvn spring-boot:run
```

> Dica: se usar JPA e der erro 500, é porque sua unidade de persistência precisa alcançar o banco (Postgres/H2). 
> No dev, prefira o profile `dev` acima para testar endpoints rapidamente.
