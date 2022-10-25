package api;

import api.data.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class DatabaseClient {
    private MovieDatabase database;

    public DatabaseClient() {
        database = new MovieDatabase();
    }

    public String getAllMovies() {
        try {
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(database.getAllMoviesAsSimplified());
        }
        catch (Exception e) {
            System.out.println(e);
            return new Response(500, "ERROR", "Unable to fetch movies from database.").toJsonString();
        }
    }

    public String getMovieByName(String name) {
        Movie movie = database.getMovieByName(name);
        if(movie == null) {
            return new Response(400, "ERROR", "Movie does not exist in database.").toJsonString();
        }
        try {
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(movie);
        }
        catch (Exception e) {
            System.out.println(e);
            return new Response(500, "ERROR", "Unable to fetch the movie from database.").toJsonString();
        }
    }

    public Response addMovie(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Movie movie = mapper.readValue(json, Movie.class);
            if(database.tryAddMovieToDB(movie)) {
                return new Response(200, "SUCCESS", "New movie added successfully");
            }
            else {
                return new Response(400, "ERROR", "Movie already exists in database.");
            }
        }
        catch (Exception e) {
            System.out.println(e);
            return new Response(400, "EXCEPTION", "Unable to parse movie from JSON");
        }
    }

    public boolean isSuccessfulInit() {
        return database.isSuccessfulInit();
    }


}
