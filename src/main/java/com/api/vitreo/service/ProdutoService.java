package com.api.vitreo.service;

import com.api.vitreo.components.ProdutoMapper;
import com.api.vitreo.dto.produto.ProdutoRequestDTO;
import com.api.vitreo.dto.produto.ProdutoResponseDTO;
import com.api.vitreo.entity.*;
import com.api.vitreo.enums.TipoProduto;
import com.api.vitreo.exception.BusinessException;
import com.api.vitreo.exception.ResourceNotFoundException;
import com.api.vitreo.repository.FornecedorRepository;
import com.api.vitreo.repository.MarcaRepository;
import com.api.vitreo.repository.ProdutoRepository;
import com.api.vitreo.repository.ProdutoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Transactional
    public ProdutoResponseDTO create(ProdutoRequestDTO produtoRequestDTO) {

        Fornecedor fornecedor = fornecedorRepository.findById(produtoRequestDTO.fornecedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado com o id: " + produtoRequestDTO.fornecedorId()));

        Marca marca = null;
        if (produtoRequestDTO.marcaId() != null) {
            marca = marcaRepository.findById(produtoRequestDTO.marcaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada"));
        }

        Produto novoProduto;
        switch (produtoRequestDTO.tipoProduto()){
            case ARMACAO:
                Armacao armacao = new Armacao();
                armacao.setCor(produtoRequestDTO.cor());
                armacao.setMaterial(produtoRequestDTO.material());
                armacao.setTamanho(produtoRequestDTO.tamanho());
                novoProduto = armacao;
                break;
            case LENTE:
                Lente lente = new Lente();
                lente.setTipoLente(produtoRequestDTO.tipoLente());
                lente.setTratamento(produtoRequestDTO.tratamento());
                lente.setMaterialLente(produtoRequestDTO.materialLente());
                novoProduto = lente;
                break;
            default:
                throw new BusinessException("Tipo de produto inválido: " + produtoRequestDTO.tipoProduto());
        }

        populateCommonFields(novoProduto, produtoRequestDTO, fornecedor, marca);


        Produto produtoSalvo = produtoRepository.save(novoProduto);

        return produtoMapper.toResponseDTO(produtoSalvo);
    }

    @Transactional
    public ProdutoResponseDTO findById(UUID id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));

        return produtoMapper.toResponseDTO(produto);
    }

    @Transactional(readOnly = true)
    public Page<ProdutoResponseDTO> findAll(String nome, TipoProduto tipo, Pageable pageable){
        Specification<Produto> spec = ProdutoSpecification.comFiltros(nome, tipo);
        Page<Produto> produtosPage = produtoRepository.findAll(spec, pageable);

        return produtosPage.map(produtoMapper::toResponseDTO);
    }

    @Transactional
    public void delete(UUID id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o id: " + id));
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }

    @Transactional
    public ProdutoResponseDTO update(UUID id, ProdutoRequestDTO produtoRequestDTO) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));

        if (produtoExistente instanceof Armacao && produtoRequestDTO.tipoProduto() != TipoProduto.ARMACAO) {
            throw new BusinessException("Não é possível alterar o tipo de um produto de Armação para " + produtoRequestDTO.tipoProduto());
        }
        if (produtoExistente instanceof Lente && produtoRequestDTO.tipoProduto() != TipoProduto.LENTE) {
            throw new BusinessException("Não é possível alterar o tipo de um produto de Lente para " + produtoRequestDTO.tipoProduto());
        }

        Fornecedor fornecedor = fornecedorRepository.findById(produtoRequestDTO.fornecedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado com o id: " + produtoRequestDTO.fornecedorId()));

        Marca marca = null;
        if (produtoRequestDTO.marcaId() != null) {
            marca = marcaRepository.findById(produtoRequestDTO.marcaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada com o id: " + produtoRequestDTO.marcaId()));
        }

        populateCommonFields(produtoExistente, produtoRequestDTO, fornecedor, marca);

        switch (produtoRequestDTO.tipoProduto()) {
            case ARMACAO:
                if (produtoExistente instanceof Armacao armacao) {
                    armacao.setCor(produtoRequestDTO.cor());
                    armacao.setMaterial(produtoRequestDTO.material());
                    armacao.setTamanho(produtoRequestDTO.tamanho());
                }
                break;
            case LENTE:
                if (produtoExistente instanceof Lente lente) {
                    lente.setMaterialLente(produtoRequestDTO.materialLente());
                    lente.setTratamento(produtoRequestDTO.tratamento());
                    lente.setTipoLente(produtoRequestDTO.tipoLente());
                }
                break;
        }

        Produto produtoSalvo = produtoRepository.save(produtoExistente);

        return produtoMapper.toResponseDTO(produtoSalvo);
    }

    private void populateCommonFields(Produto produto, ProdutoRequestDTO produtoRequestDTO, Fornecedor fornecedor, Marca marca) {
            produto.setFornecedor(fornecedor);
            produto.setMarca(marca);
            produto.setNome(produtoRequestDTO.nome());
            produto.setReferencia(produtoRequestDTO.referencia());
            produto.setCodigoBarras(produtoRequestDTO.codigoBarras());
            produto.setCusto(produtoRequestDTO.custo());
            produto.setValorVenda(produtoRequestDTO.valorVenda());
            produto.setQuantidadeEstoque(produtoRequestDTO.quantidadeEstoque());
            if (produtoRequestDTO.ativo() != null) {
                produto.setAtivo(produtoRequestDTO.ativo());
            }
    }

}
