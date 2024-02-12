package com.example.flatservice.service.api;

import com.example.flatservice.core.dto.FlatDTO;
import com.example.flatservice.core.dto.FlatsFilter;
import com.example.flatservice.exceptions.ParserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFlatService {
    Page<FlatDTO> getPage(FlatsFilter filter, Pageable pageable);
    FlatDTO parseFlat(String url) throws ParserException;
}
