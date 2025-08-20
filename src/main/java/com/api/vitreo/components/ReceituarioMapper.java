package com.api.vitreo.components;

import com.api.vitreo.dto.receituario.ReceituarioRequestDTO;
import com.api.vitreo.dto.receituario.ReceituarioResponseDTO;
import com.api.vitreo.entity.Cliente;
import com.api.vitreo.entity.Receituario;
import com.api.vitreo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ReceituarioMapper {

    @Autowired
    private ClienteRepository clienteRepository;

    public ReceituarioResponseDTO toResponseDTO(Receituario receituario) {
        return new ReceituarioResponseDTO(
                receituario.getId(),
                receituario.getCliente().getId(),
                receituario.getEsfericoOd(),
                receituario.getCilindricoOd(),
                receituario.getEixoOd(),
                receituario.getEsfericoOe(),
                receituario.getCilindricoOe(),
                receituario.getEixoOe(),
                receituario.getAdicao(),
                receituario.getDistanciaPupilar(),
                receituario.getDnpOd(),
                receituario.getDnpOe(),
                receituario.getCentroOpticoOd(),
                receituario.getCentroOpticoOe(),
                receituario.getAnguloMaior(),
                receituario.getPonteAro(),
                receituario.getAnguloVertical(),
                receituario.getNomeMedico(),
                receituario.getCrmMedico(),
                receituario.getDataReceita()
        );
    }

    public Receituario toEntity(ReceituarioRequestDTO dto, Cliente cliente) {
        Receituario receituario = new Receituario();

        receituario.setCliente(cliente);
        receituario.setEsfericoOd(dto.esfericoOd());
        receituario.setCilindricoOd(dto.cilindricoOd());
        receituario.setEixoOd(dto.eixoOd());
        receituario.setEsfericoOe(dto.esfericoOe());
        receituario.setCilindricoOe(dto.cilindricoOe());
        receituario.setEixoOe(dto.eixoOe());
        receituario.setAdicao(dto.adicao());
        receituario.setDistanciaPupilar(dto.distanciaPupilar());
        receituario.setDnpOd(dto.dnpOd());
        receituario.setDnpOe(dto.dnpOe());
        receituario.setCentroOpticoOd(dto.centroOpticoOd());
        receituario.setCentroOpticoOe(dto.centroOpticoOe());
        receituario.setAnguloMaior(dto.anguloMaior());
        receituario.setPonteAro(dto.ponteAro());
        receituario.setAnguloVertical(dto.anguloVertical());
        receituario.setNomeMedico(dto.nomeMedico());
        receituario.setCrmMedico(dto.crmMedico());
        receituario.setDataReceita(dto.dataReceita());

        return receituario;
    }
}
