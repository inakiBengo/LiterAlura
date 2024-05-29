package com.challenge.literAlura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataResponse(
        @JsonAlias("count") Integer totalResults,
        List<DataBook> results
) { }
