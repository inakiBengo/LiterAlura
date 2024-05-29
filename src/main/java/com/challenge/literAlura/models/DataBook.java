package com.challenge.literAlura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBook (
        String title,
        List<DataAuthor> authors,
        List<String> languages,
        Boolean copyright,
        @JsonAlias("download_count") String downloads
){ }
