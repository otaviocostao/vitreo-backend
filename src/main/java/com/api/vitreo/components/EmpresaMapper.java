package com.api.vitreo.components;

import com.api.vitreo.dto.EnderecoDTO;
import com.api.vitreo.dto.empresa.EmpresaRequestDTO;
import com.api.vitreo.dto.empresa.EmpresaResponseDTO;
import com.api.vitreo.entity.Empresa;
import com.api.vitreo.entity.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EmpresaMapper {

    public Empresa toEntity(EmpresaRequestDTO empresaRequest) {
        if (empresaRequest == null) {
            return null;
        }

        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(empresaRequest.razaoSocial());
        empresa.setNomeFantasia(empresaRequest.nomeFantasia());
        empresa.setCnpj(empresaRequest.cnpj());
        empresa.setInscricaoEstadual(empresaRequest.inscricaoEstadual());
        empresa.setEmail(empresaRequest.email());
        empresa.setTelefoneCelular(empresaRequest.telefoneCelular());
        empresa.setTelefoneFixo(empresaRequest.telefoneFixo());
        empresa.setUrlLogo(empresaRequest.urlLogo());

        if (empresaRequest.endereco() != null) {
            empresa.setEndereco(toEnderecoEntity(empresaRequest.endereco()));
        }
        return empresa;
    }

    public EmpresaResponseDTO toResponseDTO(Empresa empresa) {

        return new EmpresaResponseDTO(
                empresa.getId(),
                empresa.getRazaoSocial(),
                empresa.getNomeFantasia(),
                empresa.getCnpj(),
                empresa.getInscricaoEstadual(),
                empresa.getTelefoneCelular(),
                empresa.getTelefoneFixo(),
                empresa.getEmail(),
                empresa.getUrlLogo(),
                (empresa.getEndereco() != null) ? toEnderecoDTO(empresa.getEndereco()) : null
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