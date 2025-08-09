package com.api.vitreo.components;

import com.api.vitreo.dto.EnderecoDTO;
import com.api.vitreo.dto.FornecedorRequestDTO;
import com.api.vitreo.dto.FornecedorResponseDTO;
import com.api.vitreo.entity.Endereco;
import com.api.vitreo.entity.Fornecedor;
import org.springframework.stereotype.Component;

@Component
public class FornecedorMapper {

    public Fornecedor toEntity(FornecedorRequestDTO fornecedorRequest) {
        if (fornecedorRequest == null) {
            return null;
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(fornecedorRequest.id());
        fornecedor.setRazaoSocial(fornecedorRequest.razaoSocial());
        fornecedor.setNomeFantasia(fornecedorRequest.nomeFantasia());
        fornecedor.setCnpj(fornecedorRequest.cnpj());
        fornecedor.setEmail(fornecedorRequest.email());
        fornecedor.setTelefone(fornecedorRequest.telefone());

        if(fornecedorRequest.endereco() != null) {
            fornecedor.setEndereco(toEnderecoEntity(fornecedorRequest.endereco()));
        }
        return fornecedor;
    }

    public FornecedorResponseDTO toResponseDTO(Fornecedor fornecedor) {

        return new FornecedorResponseDTO(
                fornecedor.getId(),
                fornecedor.getRazaoSocial(),
                fornecedor.getNomeFantasia(),
                fornecedor.getCnpj(),
                fornecedor.getInscricaoEstadual(),
                fornecedor.getTelefone(),
                fornecedor.getEmail(),
                (fornecedor.getEndereco() != null) ? toEnderecoDTO(fornecedor.getEndereco()) : null
        );
    }

    private Endereco toEnderecoEntity(EnderecoDTO dto) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());
        return endereco;
    }

    private EnderecoDTO toEnderecoDTO(Endereco entity) {
        return new EnderecoDTO(
                entity.getLogradouro(),
                entity.getNumero(),
                entity.getComplemento(),
                entity.getBairro(),
                entity.getCidade(),
                entity.getEstado(),
                entity.getCep()
        );
    }

    public void updateEnderecoFromDto(Endereco entity, EnderecoDTO dto) {
        if (entity == null || dto == null) {
            return;
        }

        entity.setLogradouro(dto.logradouro());
        entity.setNumero(dto.numero());
        entity.setComplemento(dto.complemento());
        entity.setBairro(dto.bairro());
        entity.setCidade(dto.cidade());
        entity.setEstado(dto.estado());
        entity.setCep(dto.cep());
    }
}
