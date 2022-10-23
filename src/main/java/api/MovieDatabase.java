package api;

import api.data.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MovieDatabase {
    private final String FILE_PATH = "moviedata.json";
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private boolean databaseCreated;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public MovieDatabase() {
        tryCreateDatabase();
        tryInitMovieList();
    }

    public boolean isSuccessfulInit() {
        return databaseCreated;
    }

    public ArrayList<Movie> getAllMovies() {
        return movies;
    }

    public boolean tryAddMovieToDB(Movie movie) {
        Optional<Movie> existing = movies
                .stream()
                .filter(m -> m.equals(movie))
                .findAny();
        if(existing.isPresent()) {
            return false;
        }
        movies.add(movie);
        update();
        return true;
    }

    private void update() {
        lock.writeLock().lock();
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            writer.write(getMoviesAsJSON());
            writer.close();
        }
        catch(Exception e) {
            System.out.printf("Failed to update database. " + e.toString());
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    private String getMoviesAsJSON() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(movies);
        }
        catch (Exception e) {
            System.out.println("Failed to convert data to JSON. " + e.toString());
        }
        return "";
    }

    private void tryInitMovieList() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)), StandardCharsets.UTF_8);
            if(content.length() > 0) {
                ObjectMapper om = new ObjectMapper();
                Movie[] moviesArray = om.readValue(content, Movie[].class);
                movies = new ArrayList<Movie>(Arrays.asList(moviesArray));
            }
        }
        catch (IOException e) {
            System.out.println("Error, cannot initialize database. " + e.toString());
        }
    }

    /*
    * Creates a new database if does not yet exists
    * */
    private void tryCreateDatabase() {
        File f = new File(FILE_PATH);
        if(!f.exists()){
            try {
                f.createNewFile();
                databaseCreated = true;
            }
            catch (IOException e) {
                System.out.println("Error, Failed to create database. " + e.toString());
            }
        }
        else {
            databaseCreated = true;
        }
    }
}
