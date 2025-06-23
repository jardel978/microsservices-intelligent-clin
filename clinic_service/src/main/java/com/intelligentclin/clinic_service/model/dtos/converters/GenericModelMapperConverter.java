package com.intelligentclin.clinic_service.model.dtos.converters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GenericModelMapperConverter<E, Req, Resp> {

    @Autowired
    private ModelMapper modelMapper;

    public Resp mapEntityToModelResp(E entity, Class<Resp> modelClassDestination) {
        return modelMapper.map(entity, modelClassDestination);
    }

    public E mapModelReqToEntity(Req modelDTO, Class<E> entityClassDestination) {
        return modelMapper.map(modelDTO, entityClassDestination);
    }

    public List<Resp> convertListEntityToResp(List<E> listEntity, Class<Resp> modelClassDestination) {

        List<Resp> listSummaryDTO = new ArrayList<>();

        listEntity.stream().forEach(entity -> {
            Resp modelDTO = mapEntityToModelResp(entity, modelClassDestination);
            listSummaryDTO.add(modelDTO);
        });
        return listSummaryDTO;
    }

}
