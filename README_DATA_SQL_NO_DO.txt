# data.sql sem DO $$ … $$ (compatível com Spring Boot SQL initializer)

Este arquivo substitui o DO/BEGIN/END por instruções simples:
- `INSERT ... ON CONFLICT DO NOTHING` (idempotente)
- `INSERT ... SELECT ... WHERE EXISTS(...)` para condicionar inserts à existência da tabela
- `SELECT setval(...) WHERE pg_get_serial_sequence(...) IS NOT NULL` para ajustar sequences sem erros

## Como aplicar
1) Substitua o conteúdo de `src/main/resources/data.sql` por este.
2) Rebuild e suba a aplicação:
   ```bash
   mvn -q -DskipTests clean package
   docker compose up --build
   ```
3) Acesse: http://localhost:8080/swagger-ui/index.html

Se ainda falhar, rode:
```bash
docker compose logs -f app
```
e me envie as últimas linhas.
