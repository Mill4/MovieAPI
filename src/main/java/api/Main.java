package api;

import static spark.Spark.post;
import static spark.Spark.get;

public class Main {
    public static void main(String[] args) {
        DatabaseClient db = new DatabaseClient();
        if(!db.isSuccessfulInit()) {
            System.out.println("Database creation failed. Exiting..");
            System.exit(1);
        }

        get(Routes.GET_MOVIES_URI, "application/json", (req, res) -> {
            String r = db.getAllMovies();
            if(r != null) {
                return r;
            }
            res.status(500);
            return new Response(500, "ERROR", "Unable to fetch movies from database.").toJsonString();
        });

        post(Routes.ADD_MOVIE_URI, "application/json", (req, res) -> {
            Response r = db.AddMovie(req.body());
            res.status(r.getStatusCode());
            return r.toJsonString();
        });

    }
}