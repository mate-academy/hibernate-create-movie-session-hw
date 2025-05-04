package mate.academy.util;

public final class HqlQueries {
    public static final String GET_ALL_MOVIES = "FROM Movie";
    public static final String GET_ALL_CINEMA_HALLS = "FROM CinemaHall";
    public static final String GET_ALL_MOVIE_SESSIONS = """
            FROM MovieSession ms
            JOIN FETCH ms.movie
            JOIN FETCH ms.cinemaHallList ch
            WHERE ms.movie.id = :movieId
            AND ms.date BETWEEN :startOfDay AND :endOfDay
            """;

    private HqlQueries() {
    }
}
