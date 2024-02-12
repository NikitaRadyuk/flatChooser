package com.example.flatservice.controller;

import com.example.flatservice.core.dto.FlatDTO;
import com.example.flatservice.core.dto.FlatParseDTO;
import com.example.flatservice.core.dto.FlatsFilter;
import com.example.flatservice.exceptions.ParserException;
import com.example.flatservice.service.api.IFlatService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flats")
public class FlatRestController {
    private final IFlatService service;

    public FlatRestController(IFlatService service) {
        this.service = service;
    }
    @GetMapping
    public Page<FlatDTO> getAllFlatsByFilter(@RequestParam(name = "price_from", required = false) Integer priceFrom,
                                            @RequestParam(name = "price_to", required = false) Integer priceTo,
                                            @RequestParam(name = "bedrooms_from", required = false) Integer bedroomsFrom,
                                            @RequestParam(name = "bedrooms_to", required = false) Integer bedroomsTo,
                                            @RequestParam(name = "area_from", required = false) Integer areaFrom,
                                            @RequestParam(name = "area_to", required = false) Integer areaTo,
                                            @RequestParam(name = "floors", required = false) Integer[] floors,
                                            @RequestParam(name = "page", defaultValue = "1") Integer page,
                                            @RequestParam(name = "size", defaultValue = "20") Integer size){
        Pageable pageable = PageRequest.of(page - 1, size);
        FlatsFilter flatsFilter = new FlatsFilter()
                .setPriceFrom(priceFrom)
                .setPriceTo(priceTo)
                .setBedroomsFrom(bedroomsFrom)
                .setBedroomsTo(bedroomsTo)
                .setAreaFrom(areaFrom)
                .setAreaTo(areaTo)
                .setFloors(floors);
        return this.service.getPage(flatsFilter, pageable);
    }

    @PostMapping("/parse")
    @ResponseBody
    public ResponseEntity parse(@RequestBody FlatParseDTO flatParseDTO){
        try{
            FlatDTO flat = this.service.parseFlat(flatParseDTO.getUrl());
            return new ResponseEntity(flat, HttpStatus.OK);
        } catch (ParserException e) {
            return new ResponseEntity("Не удалось распарсить данные. Проверьте ссылку", HttpStatus.BAD_REQUEST);
        }
    }
}
