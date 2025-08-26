package br.com.oficina.adapters.repositories.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios",
        uniqueConstraints = {
            @UniqueConstraint(name="uk_usuarios_username", columnNames = "username"),
            @UniqueConstraint(name="uk_usuarios_email", columnNames = "email")
        },
        indexes = {
            @Index(name="idx_usuarios_role", columnList = "role_id"),
            @Index(name="idx_usuarios_nome", columnList = "nome")
        })
public class UsuarioEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=60)
    private String username;

    @Column(nullable=false, length=120)
    private String nome;

    @Column(nullable=false, length=160)
    private String email;

    @Column(nullable=false, length=255)
    private String password;

    @Column(nullable=false)
    private boolean ativo;

    @Column(name="role_id", nullable=false)
    private Long roleId;

    // getters/setters
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getUsername(){return username;} public void setUsername(String v){this.username=v;}
    public String getNome(){return nome;} public void setNome(String v){this.nome=v;}
    public String getEmail(){return email;} public void setEmail(String v){this.email=v;}
    public String getPassword(){return password;} public void setPassword(String v){this.password=v;}
    public boolean isAtivo(){return ativo;} public void setAtivo(boolean v){this.ativo=v;}
    public Long getRoleId(){return roleId;} public void setRoleId(Long v){this.roleId=v;}
}
