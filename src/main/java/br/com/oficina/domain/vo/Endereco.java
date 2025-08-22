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
        this.logradouro = norm(logradouro);
        this.numero = norm(numero);
        this.complemento = norm(complemento);
        this.bairro = norm(bairro);
        this.cidade = norm(cidade);
this.estado = ufOrNull(estado);
this.cep = cepOrNull(cep);

    }

private static String norm(String s) {
    if (s == null) return null;
    String t = s.trim().replaceAll("\\s+", " ");
    return t.isEmpty() ? null : t;
}    

private static String ufOrNull(String s) {
    if (s == null) return null;
    String t = s.trim().toUpperCase();
    if (!t.isEmpty() && t.length() != 2) throw new IllegalArgumentException("estado (UF) deve ter 2 letras");
    return t;
}

private static String cepOrNull(String s) {
    if (s == null) return null;
    String digits = s.replaceAll("\\D", "");
    if (!digits.isEmpty() && digits.length() != 8) throw new IllegalArgumentException("cep deve ter 8 dígitos");
    return digits;
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
