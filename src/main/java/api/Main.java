package api;

import com.clearspring.analytics.util.Pair;

import static spark.Spark.post;
import static spark.Spark.get;
import static spark.Spark.put;
import static spark.Spark.delete;

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
            Pair<String, Integer>  result = client.getAllMovies();
            res.status(result.right);
            return result.left;
        });

        /*
        * GET MOVIE BY NAME
        * */
        get(Routes.GET_MOVIE_BY_NAME_URI, "application/json", (req, res) -> {
            Pair<String, Integer>  result =  client.getMovieByName(req.params(":name"));
            res.status(result.right);
            return result.left;
        });

        /*
        * ADD NEW MOVIE
        * */
        post(Routes.ADD_MOVIE_URI, "application/json", (req, res) -> {
            Pair<String, Integer>  result = client.addMovie(req.body());
            res.status(result.right);
            return result.left;
        });

        /*
         * UPDATE MOVIE INFORMATION
         * */
        put(Routes.UPDATE_MOVIE_URI, "application/json", (req, res) -> {
            Pair<String, Integer>  result =  client.updateMovieInformation(req.body());
            res.status(result.right);
            return result.left;
        });

        /*
         * GET MOVIE BY NAME
         * */
        delete(Routes.GET_MOVIE_BY_NAME_URI, "application/json", (req, res) -> {
            Pair<String, Integer>  result =  client.deleteMovie(req.params(":name"));
            res.status(result.right);
            return result.left;
        });

    }
}