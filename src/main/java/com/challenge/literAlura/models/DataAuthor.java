package com.challenge.literAlura.models;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataAuthor(
        String name,
        @JsonAlias("birth_year") Integer birth,
        @JsonAlias("death_year") Integer death
) {
}
