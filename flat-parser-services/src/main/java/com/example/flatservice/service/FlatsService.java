package com.example.flatservice.service;

import com.example.flatservice.core.FlatSpecification;
import com.example.flatservice.core.dto.FlatDTO;
import com.example.flatservice.core.dto.FlatsFilter;
import com.example.flatservice.core.entity.FlatEntity;
import com.example.flatservice.core.entity.ParsedFlatEntity;
import com.example.flatservice.exceptions.ParserException;
import com.example.flatservice.repository.FlatsRepository;
import com.example.flatservice.service.api.FlatParser;
import com.example.flatservice.service.api.IFlatService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FlatsService implements IFlatService {
    private static final String FLAT_FLOOR = "Этаж / этажность";
    private static final String FLAT_AREA = "Площадь общая";
    private static final String FLAT_ROOMS = "Раздельных комнат";
    private static final String FLAT_TERMS = "Условия продажи";

    private final FlatsRepository repository;
    private final FlatParser parser;

    public FlatsService(FlatsRepository repository, FlatParser parser) {
        this.repository = repository;
        this.parser = parser;
    }

    @Override
    public Page<FlatDTO> getPage(FlatsFilter filter, Pageable pageable) {
        Page<FlatEntity> entityPage = this.repository.findAll(FlatSpecification.byFlatsFilter(filter), pageable);
        return entityPage.map(FlatsService::apply);
    }

    private static FlatDTO apply(FlatEntity entity) {
        FlatDTO flatDTO = new FlatDTO();
        flatDTO.setUuid(entity.getUuid());
        flatDTO.setDtCreate(entity.getDtCreate());
        flatDTO.setDtUpdate(entity.getDtUpdate());
        flatDTO.setOfferType(entity.getOfferType());
        flatDTO.setDescription(entity.getDescription());
        flatDTO.setBedrooms(entity.getBedrooms());
        flatDTO.setArea(entity.getArea());
        flatDTO.setPrice(entity.getPrice());
        flatDTO.setFloor(entity.getFloor());
        return flatDTO;
    }

    @Override
    public FlatDTO parseFlat(String url) throws ParserException {
        ParsedFlatEntity flat = parser.parse(url);

        FlatDTO flatDTO = new FlatDTO();
        flatDTO.setUuid(UUID.randomUUID());
        flatDTO.setDtCreate(LocalDateTime.now());
        flatDTO.setDescription(flat.name);
        flatDTO.setPrice(parseInteger(flat.cost));
        flatDTO.setOfferType(flat.properties.get(FLAT_TERMS));
        flatDTO.setBedrooms(flat.properties.get(FLAT_ROOMS));
        flatDTO.setArea(parseInteger(flat.properties.get(FLAT_AREA)));
        flatDTO.setFloor(parseInteger(flat.properties.get(FLAT_FLOOR)));

        saveFlatToDB(flatDTO);

        return flatDTO;
    }

    public FlatEntity saveFlatToDB(FlatDTO flatDTO){
            FlatEntity flatEntity = new FlatEntity();
            flatEntity.setUuid(flatDTO.getUuid());
            flatEntity.setDtCreate(flatDTO.getDtCreate());
            flatEntity.setDtUpdate(flatDTO.getDtUpdate());
            flatEntity.setArea(flatDTO.getArea());
            flatEntity.setBedrooms(flatDTO.getBedrooms());
            flatEntity.setDescription(flatDTO.getDescription());
            flatEntity.setOfferType(flatDTO.getOfferType());
            flatEntity.setFloor(flatDTO.getFloor());
            flatEntity.setPrice(flatDTO.getPrice());
            this.repository.saveAndFlush(flatEntity);
            return flatEntity;
    }

    private Integer parseInteger(String string){
        try {
            return Integer.parseInt(string.split("\s+")[0].replaceAll("[^0-9]", ""));
        }catch (Exception ex){
            return 0;
        }
    }
}
