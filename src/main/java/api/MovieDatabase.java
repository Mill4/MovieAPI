package api;

import api.data.Movie;
import api.data.SimplifiedMovie;
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

    public ArrayList<SimplifiedMovie> getAllMoviesAsSimplified() {
        return simplify();
    }

    public Movie getMovieByName(String name) {
        Optional<Movie> existing = movies
                .stream()
                .filter(m -> m.getName().equals(name))
                .findAny();
        if(existing.isPresent()) {
            return existing.get();
        }
        return null;
    }

    public boolean tryAddMovieTo(Movie movie) {
        Optional<Movie> existing = movies
                .stream()
                .filter(m -> m.equals(movie))
                .findAny();
        if(existing.isPresent()) {
            return false;
        }
        System.out.println("Adding new movie to database: " + movie.getName());
        movies.add(movie);
        update();
        return true;
    }

    public boolean tryUpdateMovie(Movie movie) {
        for(Movie m : movies) {
            if(m.equals(movie)) {
                m.copy(movie);
                update();
                return true;
            }
        }
        return false;
    }

    public boolean tryRemoveMovie(String name) {
        boolean res = movies.removeIf(m -> m.getName().equals(name));
        update();
        return res;
    }

    private ArrayList<SimplifiedMovie> simplify() {
        ArrayList<SimplifiedMovie> result = new ArrayList<>();
        for(Movie m : movies) {
            result.add(new SimplifiedMovie(m));
        }
        return result;
    }

    private void update() {
        lock.writeLock().lock();
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            writer.write(getMoviesAsJSON());
            writer.close();
        }
        catch(Exception e) {
            System.out.printf("Failed to update database. " + e);
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
            System.out.println("Failed to convert movie to JSON. " + e);
        }
        return "";
    }

    private void tryInitMovieList() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)), StandardCharsets.UTF_8);
            if(content.length() > 0) {
                ObjectMapper om = new ObjectMapper();
                Movie[] moviesArray = om.readValue(content, Movie[].class);
                movies = new ArrayList<>(Arrays.asList(moviesArray));
            }
        }
        catch (IOException e) {
            System.out.println("Error, cannot initialize database. " + e);
        }
    }

    /*
    * Creates a new database if not yet exist
    * */
    private void tryCreateDatabase() {
        File f = new File(FILE_PATH);
        if(!f.exists()){
            try {
                f.createNewFile();
                databaseCreated = true;
            }
            catch (IOException e) {
                System.out.println("Error, Failed to create database. " + e);
            }
        }
        else {
            databaseCreated = true;
        }
    }
}
