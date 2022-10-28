package api;

import api.data.Movie;
import com.clearspring.analytics.util.Pair;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class DatabaseClient {
    private MovieDatabase database;

    public DatabaseClient() {
        database = new MovieDatabase();
    }

    public Pair<String, Integer> getAllMovies() {
        try {
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            return new Pair(mapper.writeValueAsString(database.getAllMoviesAsSimplified()), 200);
        }
        catch (Exception e) {
            System.out.println(e);
            return new Pair(new Response( "EXCEPTION", "Unable to parse the data from database").toJsonString(), 500);
        }
    }

    public Pair<String, Integer> getMovieByName(String name) {
        Movie movie = database.getMovieByName(name);
        if(movie == null) {
            return new Pair(new Response( "ERROR", "Movie does not exist in the database").toJsonString(), 400);
        }
        try {
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            return new Pair(mapper.writeValueAsString(movie), 200);
        }
        catch (Exception e) {
            System.out.println(e);
            return new Pair(new Response("EXCEPTION", "Unable to parse the data from database").toJsonString(), 500);
        }
    }

    public Pair<String, Integer> addMovie(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Movie movie = mapper.readValue(json, Movie.class);
            if(database.tryAddMovieTo(movie)) {
                return new Pair(new Response("SUCCESS", "New movie added successfully").toJsonString(), 200);
            }
            else {
                return new Pair(new Response("ERROR", "Movie already exists in the database").toJsonString(), 400);
            }
        }
        catch (Exception e) {
            System.out.println(e);
            return new Pair(new Response("EXCEPTION", "Unable to parse movie from JSON").toJsonString(), 500);
        }
    }

    public Pair<String, Integer> updateMovieInformation(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Movie movie = mapper.readValue(json, Movie.class);
            if(database.tryUpdateMovie(movie)) {
                return new Pair(new Response("SUCCESS", "Movie information updated successfully").toJsonString(), 200);
            }
            else {
                return new Pair(new Response("ERROR", "Movie does not exists in the database").toJsonString(), 400);
            }
        }
        catch (Exception e) {
            System.out.println(e);
            return new Pair(new Response("EXCEPTION", "Unable to parse movie from JSON, check formatting").toJsonString(), 400);
        }
    }

    public Pair<String, Integer> deleteMovie(String name) {
        if(database.tryRemoveMovie(name)) {
            return new Pair(new Response("SUCCESS", "Movie deleted successfully").toJsonString(), 200);
        }
        else {
            return new Pair(new Response("ERROR", "Movie does not exists in the database").toJsonString(), 400);
        }
    }

    public boolean isSuccessfulInit() {
        return database.isSuccessfulInit();
    }


}
