package com.api.vitreo.components;

import com.api.vitreo.dto.ClienteRequestDTO;
import com.api.vitreo.dto.ClienteResponseDTO;
import com.api.vitreo.dto.EnderecoDTO;
import com.api.vitreo.entity.Cliente;
import com.api.vitreo.entity.Endereco;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequestDTO clienteRequestDTO) {
        if (clienteRequestDTO == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setNome(clienteRequestDTO.nome());
        cliente.setSobrenome(clienteRequestDTO.sobrenome());
        cliente.setCpf(clienteRequestDTO.cpf());
        cliente.setTelefone(clienteRequestDTO.telefone());
        cliente.setEmail(clienteRequestDTO.email());
        cliente.setDataNascimento(clienteRequestDTO.dataNascimento());

        if (clienteRequestDTO.endereco() != null) {
            cliente.setEndereco(toEnderecoEntity(clienteRequestDTO.endereco()));
        }

        return cliente;
    }

    public ClienteResponseDTO toResponseDTO(Cliente cliente){
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getSobrenome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getDataNascimento(),
                (cliente.getEndereco() != null) ? toEnderecoDTO(cliente.getEndereco()) : null
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
