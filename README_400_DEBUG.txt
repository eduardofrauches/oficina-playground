# 400 no POST /clientes — como diagnosticar

Se aparecer apenas:
{
  "timestamp": "...",
  "status": 400,
  "error": "Bad Request",
  "path": "/clientes"
}
é porque o Spring gerou a resposta padrão. Este handler melhora a mensagem e aponta o campo com problema.

## O que muda
- Trata: validação (@Valid), binding, JSON inválido e tipo inválido em path params.
- Resposta padronizada, exemplo:
  {
    "timestamp": "...",
    "status": 400,
    "error": "Bad Request",
    "message": "Erro de validação",
    "fields": {
      "nome": "nome é obrigatório",
      "email": "email é obrigatório"
    }
  }

## Exemplos de payloads válidos

### Mínimo (só obrigatórios)
{
  "nome": "Fulano",
  "email": "fulano@ex.com",
  "telefone": "11999999999"
}

### Completo (com endereço e data)
{
  "nome": "Fulano",
  "email": "fulano@ex.com",
  "telefone": "11999999999",
  "cpf": "12345678900",
  "logradouro": "Rua A",
  "numero": "123",
  "bairro": "Centro",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01000-000",
  "dataNascimento": "1990-05-10",
  "observacao": "VIP"
}

## Armadilhas comuns
- Content-Type: sempre **application/json**.
- Campos numéricos em string (telefone/cep/cpf): use **entre aspas**.
- `estado` deve ter **2 letras** (ex.: "SP").
- `dataNascimento` é **YYYY-MM-DD** (ex.: "1990-05-10").
- Nomes de campos: use exatamente como acima (ex.: `telefone`, não `phone`).

## Como aplicar
- Salve este arquivo em: `src/main/java/br/com/oficina/adapters/controllers/GlobalExceptionHandler.java`
- Reinicie a aplicação.
