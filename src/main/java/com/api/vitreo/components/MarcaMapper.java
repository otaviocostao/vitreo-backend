package com.api.vitreo.components;

import com.api.vitreo.dto.marca.MarcaRequestDTO;
import com.api.vitreo.dto.marca.MarcaResponseDTO;
import com.api.vitreo.entity.Marca;
import org.springframework.stereotype.Component;

@Component
public class MarcaMapper {

    public Marca toEntity(MarcaRequestDTO marcaRequestDTO) {
        Marca marca = new Marca();
        marca.setNome(marcaRequestDTO.nome());
        return marca;
    }

    public MarcaResponseDTO toResponseDTO(Marca marca){
        return new MarcaResponseDTO(marca.getId(), marca.getNome());
    }
}
