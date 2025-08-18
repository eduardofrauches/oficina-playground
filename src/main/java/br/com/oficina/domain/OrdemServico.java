package br.com.oficina.domain;

import java.time.LocalDate;

public class OrdemServico {
    private final String id;
    private final Orcamento orcamento;
    private final LocalDate dataAbertura;
    private LocalDate dataConclusao;

    public OrdemServico(String id, Orcamento orcamento, LocalDate dataAbertura) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id é obrigatório");
        if (orcamento == null) throw new IllegalArgumentException("orçamento é obrigatório");
        if (dataAbertura == null) throw new IllegalArgumentException("data de abertura é obrigatória");

        this.id = id;
        this.orcamento = orcamento;
        this.dataAbertura = dataAbertura;
    }

    public void concluir(LocalDate dataConclusao) {
        if (dataConclusao.isBefore(dataAbertura)) {
            throw new IllegalArgumentException("Data de conclusão não pode ser antes da abertura");
        }
        this.dataConclusao = dataConclusao;
    }

    public String getId() { return id; }
    public Orcamento getOrcamento() { return orcamento; }
    public LocalDate getDataAbertura() { return dataAbertura; }
    public LocalDate getDataConclusao() { return dataConclusao; }
}
