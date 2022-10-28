package api.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {
    private String name;
    private int year;
    private ArrayList<String> genres;
    private int ageLimit;
    private int rating;
    private ArrayList<Actor> actors;
    private Director director;
    private String synopsis;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        Movie m = (Movie) o;
        if(name.equals(m.name)) {
            return true;
        }
        return false;
    }

    public void copy(Movie other) {
        name = other.name;
        year = other.year;
        genres = other.genres;
        ageLimit = other.ageLimit;
        rating = other.rating;
        actors = other.actors;
        director = other.director;
        synopsis = other.synopsis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
