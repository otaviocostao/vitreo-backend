package com.api.vitreo.components;

import com.api.vitreo.dto.produto.ArmacaoResponseDTO;
import com.api.vitreo.dto.produto.LenteResponseDTO;
import com.api.vitreo.dto.produto.ProdutoResponseDTO;
import com.api.vitreo.entity.Armacao;
import com.api.vitreo.entity.Lente;
import com.api.vitreo.entity.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public ProdutoResponseDTO toResponseDTO(Produto produto) {
        if (produto instanceof Armacao) {
            return toArmacaoResponseDTO((Armacao) produto);
        }
        if (produto instanceof Lente) {
            return toLenteResponseDTO((Lente) produto);
        }

        throw new IllegalArgumentException("Tipo de produto desconhecido: " + produto.getClass().getName());
    }

    private ArmacaoResponseDTO toArmacaoResponseDTO(Armacao armacao) {
        return new ArmacaoResponseDTO(
                armacao.getId(),
                armacao.getNome(),
                armacao.getCusto(),
                armacao.getMargemLucroPercentual(),
                armacao.getValorVenda(),
                armacao.getQuantidadeEstoque(),
                armacao.getMarca() != null ? armacao.getMarca().getNome() : null,
                armacao.getFornecedor().getRazaoSocial(),
                armacao.getCor(),
                armacao.getMaterial(),
                armacao.getTamanho()
        );
    }

    private LenteResponseDTO toLenteResponseDTO(Lente lente) {
        return new LenteResponseDTO(
                lente.getId(),
                lente.getNome(),
                lente.getValorVenda(),
                lente.getQuantidadeEstoque(),
                lente.getMarca() != null ? lente.getMarca().getNome() : null,
                lente.getFornecedor().getRazaoSocial(),
                lente.getIndiceRefracao(),
                lente.getTratamento(),
                lente.getTipoLente()
        );
    }
}
