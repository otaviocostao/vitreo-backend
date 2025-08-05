package com.api.vitreo.service;

import com.api.vitreo.entity.Produto;
import com.api.vitreo.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;

    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Optional<Produto> findByID(UUID id){
        return produtoRepository.findById(id);
    }

    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }

    public void deleteById(UUID id) {
        produtoRepository.deleteById(id);
    }

    public Produto update(Produto produto) {
        if (produto.getId() == null) {
            throw new IllegalArgumentException("Produto ID must not be null for update");
        }
        return produtoRepository.save(produto);
    }
}
