package api;

import api.data.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;


public class DatabaseClient {
    private MovieDatabase database;

    public DatabaseClient() {
        database = new MovieDatabase();
    }

    public String getAllMovies() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(database.getAllMovies());
        }
        catch (Exception e) {
            return null;
        }
    }

    public Response AddMovie(String json) {
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
            System.out.println(e.toString());
            return new Response(400, "EXCEPTION", "Unable to parse movie from JSON");
        }
    }

    public boolean isSuccessfulInit() {
        return database.isSuccessfulInit();
    }


}
