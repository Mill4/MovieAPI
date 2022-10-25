package api;

import static spark.Spark.post;
import static spark.Spark.get;

public class Main {
    public static void main(String[] args) {
        DatabaseClient client = new DatabaseClient();
        if(!client.isSuccessfulInit()) {
            System.out.println("Database creation or connection failed. Exiting..");
            System.exit(1);
        }

        /*
        * LIST ALL MOVIES
        * */
        get(Routes.GET_MOVIES_URI, "application/json", (req, res) -> {
            return client.getAllMovies();
        });

        /*
        * GET MOVIE BY NAME
        * */
        get(Routes.GET_MOVIE_BY_NAME_URI, "application/json", (req, res) -> {
            return client.getMovieByName(req.params(":name"));
        });

        /*
        * ADD NEW MOVIE
        * */
        post(Routes.ADD_MOVIE_URI, "application/json", (req, res) -> {
            Response r = client.addMovie(req.body());
            res.status(r.getStatusCode());
            return r.toJsonString();
        });

    }
}