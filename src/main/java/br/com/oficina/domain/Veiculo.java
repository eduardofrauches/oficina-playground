package br.com.oficina.domain;

public class Veiculo {
    private final String id;
    private final String placa;
    private final String modelo;
    private final String marca;

    public Veiculo(String id, String placa, String modelo, String marca) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id é obrigatório");
        if (placa == null || placa.isBlank()) throw new IllegalArgumentException("placa é obrigatória");
        if (modelo == null || modelo.isBlank()) throw new IllegalArgumentException("modelo é obrigatório");
        if (marca == null || marca.isBlank()) throw new IllegalArgumentException("marca é obrigatória");

        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
    }

    public String getId() { return id; }
    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public String getMarca() { return marca; }

    @Override
    public String toString() {
        return "Veiculo{id='%s', placa='%s', modelo='%s', marca='%s'}"
                .formatted(id, placa, modelo, marca);
    }
}
