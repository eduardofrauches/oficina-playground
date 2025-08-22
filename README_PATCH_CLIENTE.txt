
# PATCH /clientes/{id} (merge)

Este pacote adiciona um endpoint PATCH que **só atualiza** os campos enviados no body.
Campos ausentes são preservados; campos enviados como string vazia ("") são limpos (viram null).

## Arquivos incluídos
- src/main/java/br/com/oficina/adapters/controllers/dto/ClientePatchRequest.java
- src/main/java/br/com/oficina/adapters/controllers/mappers/ClienteDtoMapper.java   (atualizado com merge)
- src/main/java/br/com/oficina/adapters/controllers/ClienteController.java          (adicionado @PatchMapping)

## Como usar
- Substitua/adicione os arquivos.
- Rode a aplicação e use no Swagger: `PATCH /clientes/{id}`.

### Exemplo (trocar só nome e email)
{
  "nome": "Fulano da Silva",
  "email": "fulano.silva@ex.com"
}

### Exemplo (limpar observação e alterar UF)
{
  "observacao": "",
  "estado": "SP"
}

### Observações
- Se enviar `""` (string vazia) para um campo de texto, ele será **limpo** (setado para null).
- `email` é validado **somente** se enviado.
- `estado` (UF) valida tamanho **somente** se enviado.
- `ativo` só muda se enviado (true/false).
- `dataCadastro` é sempre preservada.
