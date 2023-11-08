package br.com.fiap.domain.entity;

import java.util.LinkedHashSet;
import java.util.Set;


public class Movie {

    private Long id;

    private boolean adult;

    private String backdropPath;

    private Set<Genre> genres = new LinkedHashSet<>();

    private String originalLanguage;

    private String originalTitle;

    private String overview;

    private Double popularity;

    private String posterPath;

    private String releaseDate;

    private String title;

    private boolean video;

    private double voteAverage;

    private Integer voteCount;
}