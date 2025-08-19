package br.com.oficina.domain.vo;

import java.util.Objects;

public final class Endereco {
    private final String logradouro;
    private final String numero;
    private final String complemento;
    private final String bairro;
    private final String cidade;
    private final String estado;
    private final String cep;

    public Endereco(String logradouro, String numero, String complemento,
                    String bairro, String cidade, String estado, String cep) {
        this.logradouro = trimOrNull(logradouro);
        this.numero = trimOrNull(numero);
        this.complemento = trimOrNull(complemento);
        this.bairro = trimOrNull(bairro);
        this.cidade = trimOrNull(cidade);
        this.estado = trimOrNull(estado);
        this.cep = trimOrNull(cep);
    }

    private static String trimOrNull(String s) {
        return s == null ? null : s.trim();
    }

    public String getLogradouro() { return logradouro; }
    public String getNumero() { return numero; }
    public String getComplemento() { return complemento; }
    public String getBairro() { return bairro; }
    public String getCidade() { return cidade; }
    public String getEstado() { return estado; }
    public String getCep() { return cep; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco)) return false;
        Endereco that = (Endereco) o;
        return Objects.equals(logradouro, that.logradouro)
            && Objects.equals(numero, that.numero)
            && Objects.equals(complemento, that.complemento)
            && Objects.equals(bairro, that.bairro)
            && Objects.equals(cidade, that.cidade)
            && Objects.equals(estado, that.estado)
            && Objects.equals(cep, that.cep);
    }
    @Override public int hashCode() {
        return Objects.hash(logradouro, numero, complemento, bairro, cidade, estado, cep);
    }
}
