package br.com.oficina.adapters.controllers.dto;

import br.com.oficina.domain.Veiculo;

public class VeiculoDtoMapper {

    public static Veiculo toDomain(VeiculoRequest req, Long idIfAny) {
        boolean ativo = req.ativo == null ? true : req.ativo;
        return new Veiculo(
                idIfAny,
                req.placa,
                req.marca,
                req.modelo,
                req.ano,
                req.cor,
                req.observacoes,
                req.clienteId,
                ativo
        );
    }

    public static VeiculoResponse fromDomain(Veiculo v) {
        VeiculoResponse r = new VeiculoResponse();
        r.id = v.getId();
        r.placa = v.getPlaca();
        r.marca = v.getMarca();
        r.modelo = v.getModelo();
        r.ano = v.getAno();
        r.cor = v.getCor();
        r.observacoes = v.getObservacoes();
        r.clienteId = v.getClienteId();
        r.ativo = v.isAtivo();
        return r;
    }

    /** Merge parcial para PATCH: só troca o que veio não-nulo no request. */
    public static Veiculo merge(Veiculo original, VeiculoRequest patch) {
        String placa = patch.placa != null ? patch.placa : original.getPlaca();
        String marca = patch.marca != null ? patch.marca : original.getMarca();
        String modelo = patch.modelo != null ? patch.modelo : original.getModelo();
        Integer ano = patch.ano != null ? patch.ano : original.getAno();
        String cor = patch.cor != null ? patch.cor : original.getCor();
        String observacoes = patch.observacoes != null ? patch.observacoes : original.getObservacoes();
        Long clienteId = patch.clienteId != null ? patch.clienteId : original.getClienteId();
        boolean ativo = patch.ativo != null ? patch.ativo : original.isAtivo();

        return new Veiculo(
                original.getId(),
                placa,
                marca,
                modelo,
                ano,
                cor,
                observacoes,
                clienteId,
                ativo
        );
    }
}
