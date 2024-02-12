package com.example.flatservice.service.api;


import com.example.flatservice.core.entity.ParsedFlatEntity;
import com.example.flatservice.exceptions.ParserException;

import java.net.URL;

public interface FlatParser {
    public ParsedFlatEntity parse(String url) throws ParserException;
}
