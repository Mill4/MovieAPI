package api;

public class Routes {
    public static final String BASE_URI = "/api/";
    public static final String GET_MOVIES_URI = BASE_URI + "/movies";
    public static final String ADD_MOVIE_URI = BASE_URI + "/movies/add";
    public static final String UPDATE_MOVIE_URI = BASE_URI + "/movies/update";
    public static final String DELETE_MOVIE_BY_NAME_URI = BASE_URI + "/movies/delete/:name";
}
