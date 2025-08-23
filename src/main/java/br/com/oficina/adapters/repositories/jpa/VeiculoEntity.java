package br.com.oficina.adapters.repositories.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "veiculos",
       uniqueConstraints = @UniqueConstraint(name = "uk_veiculos_placa", columnNames = "placa"),
       indexes = {
           @Index(name = "idx_veiculos_cliente_id", columnList = "cliente_id")
       }
)
public class VeiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // unique controlado no nível da tabela
    private String placa;

    private String marca;
    private String modelo;

    private Integer ano;

    private String cor;

    private String observacoes;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private Boolean ativo = true;

    // Getters/Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
