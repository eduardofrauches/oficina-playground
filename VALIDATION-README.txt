### Validação no controller (Jakarta Validation)

1) Dependência no pom.xml (se não quiser substituir o pom inteiro, adicione isto dentro de <dependencies>):
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

2) As anotações estão nos DTOs (ClienteRequest): @NotBlank, @Email, @Size.

3) O controller usa @Valid e há um GlobalExceptionHandler para retornar 400 com mapa de erros.

4) Teste rápido:
   - POST /clientes sem nome/email/telefone → deve retornar 400 com detalhes dos campos.

5) Build:
   mvn -q -DskipTests clean package
