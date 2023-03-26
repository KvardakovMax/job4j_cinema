package com.cinema.dto;

import com.cinema.model.File;

public class FilmDto {

    private String name;

    private String description;

    private String genre;

    private int year;

    private int minimalAge;

    private int durationInMinute;

    private File poster;

    public FilmDto(String name, String description, String genre, int year, int minimalAge, int durationInMinute, File poster) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.year = year;
        this.minimalAge = minimalAge;
        this.durationInMinute = durationInMinute;
        this.poster = poster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public int getDurationInMinute() {
        return durationInMinute;
    }

    public void setDurationInMinute(int durationInMinute) {
        this.durationInMinute = durationInMinute;
    }

    public File getPoster() {
        return poster;
    }

    public void setPoster(File poster) {
        this.poster = poster;
    }
}
