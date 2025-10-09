package com.api.vitreo.components;

import com.api.vitreo.dto.fornecedor.FornecedorSimplificadoDTO;
import com.api.vitreo.dto.marca.MarcaSimplificadaDTO;
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
        FornecedorSimplificadoDTO fornecedorDTO = new FornecedorSimplificadoDTO(
                armacao.getFornecedor().getId(),
                armacao.getFornecedor().getRazaoSocial()
        );
        MarcaSimplificadaDTO marcaDTO = armacao.getMarca() != null
                ? new MarcaSimplificadaDTO(armacao.getMarca().getId(), armacao.getMarca().getNome())
                : null;
        return new ArmacaoResponseDTO(
                armacao.getId(),
                armacao.getNome(),
                armacao.getReferencia(),
                armacao.getCodigoBarras(),
                armacao.getCusto(),
                armacao.getMargemLucro(),
                armacao.getValorVenda(),
                armacao.getQuantidadeEstoque(),
                marcaDTO,
                fornecedorDTO,
                armacao.getCor(),
                armacao.getMaterial(),
                armacao.getTamanho()
        );
    }

    private LenteResponseDTO toLenteResponseDTO(Lente lente) {
        FornecedorSimplificadoDTO fornecedorDTO = new FornecedorSimplificadoDTO(
                lente.getFornecedor().getId(),
                lente.getFornecedor().getRazaoSocial()
        );
        MarcaSimplificadaDTO marcaDTO = lente.getMarca() != null
                ? new MarcaSimplificadaDTO(lente.getMarca().getId(), lente.getMarca().getNome())
                : null;

        return new LenteResponseDTO(
                lente.getId(),
                lente.getNome(),
                lente.getReferencia(),
                lente.getCodigoBarras(),
                lente.getCusto(),
                lente.getMargemLucro(),
                lente.getValorVenda(),
                lente.getQuantidadeEstoque(),
                marcaDTO,
                fornecedorDTO,
                lente.getMaterialLente(),
                lente.getTratamento(),
                lente.getTipoLente()
        );
    }
}
