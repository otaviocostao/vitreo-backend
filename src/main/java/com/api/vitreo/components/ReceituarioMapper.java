package com.api.vitreo.components;

import com.api.vitreo.dto.receituario.ReceituarioRequestDTO;
import com.api.vitreo.dto.receituario.ReceituarioResponseDTO;
import com.api.vitreo.dto.receituario.ReceituarioUpdateRequestDTO;
import com.api.vitreo.entity.Receituario;
import org.springframework.stereotype.Component;


@Component
public class ReceituarioMapper {


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

    public Receituario toEntity(ReceituarioRequestDTO dto) {
        Receituario receituario = new Receituario();
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

    public void updateEntityFromDto(Receituario entity, ReceituarioUpdateRequestDTO dto){
        entity.setEsfericoOd(dto.esfericoOd());
        entity.setCilindricoOd(dto.cilindricoOd());
        entity.setEixoOd(dto.eixoOd());
        entity.setEsfericoOe(dto.esfericoOe());
        entity.setCilindricoOe(dto.cilindricoOe());
        entity.setEixoOe(dto.eixoOe());
        entity.setAdicao(dto.adicao());
        entity.setDistanciaPupilar(dto.distanciaPupilar());
        entity.setDnpOd(dto.dnpOd());
        entity.setDnpOe(dto.dnpOe());
        entity.setCentroOpticoOd(dto.centroOpticoOd());
        entity.setCentroOpticoOe(dto.centroOpticoOe());
        entity.setAnguloMaior(dto.anguloMaior());
        entity.setPonteAro(dto.ponteAro());
        entity.setAnguloVertical(dto.anguloVertical());
        entity.setNomeMedico(dto.nomeMedico());
        entity.setCrmMedico(dto.crmMedico());
        entity.setDataReceita(dto.dataReceita());
    }

    public void updateFromDto(ReceituarioRequestDTO dto, Receituario receituario) {
        if (dto == null || receituario == null) {
            return;
        }

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
    }
}
