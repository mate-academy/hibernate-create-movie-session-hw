package mate.academy;

import java.time.LocalDate;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final int CINEMA_HALL_CAPACITY = 50;
    private static final int FAST_AND_FURIOUS_MOVIE = 0;
    private static final int THE_GODFATHER_MOVIE = 1;
    private static final int THE_DARK_KNIGHT_MOVIE = 2;
    private static final int CINEMA_HALL_NUMBER_ONE = 0;
    private static final int CINEMA_HALL_NUMBER_TWO = 1;
    private static final int CINEMA_HALL_NUMBER_THREE = 2;
    private static final LocalDate DATE_24_12_25 = LocalDate.of(2024, 12, 25);
    private static final LocalDate DATE_24_12_27 = LocalDate.of(2024, 12, 27);
    private static final int MOVIE_SESSION_ONE = 0;

    public static void main(String[] args) {

        // 1. MovieService realisations (add, get, getAll).
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        List<Movie> movies = createSampleMovies();

        for (Movie movie : movies) {
            movieService.add(movie);
        }

        System.out.println(movieService.get(movies.get(FAST_AND_FURIOUS_MOVIE).getId()));
        movieService.getAll().forEach(System.out::println);

        //2. CinemaHallService realisations (add, get, getAll).
        CinemaHallService cinemaHallService = (CinemaHallService) injector.getInstance(
                CinemaHallService.class);
        List<CinemaHall> cinemaHalls = createSampleCinemaHalls();

        for (CinemaHall cinemaHall : cinemaHalls) {
            cinemaHallService.add(cinemaHall);
        }

        System.out.println(cinemaHallService.get(cinemaHalls.get(CINEMA_HALL_NUMBER_ONE).getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        //3. MovieSessionService realisations (add, get, findAvailableSessions).
        MovieSessionService movieSessionService = (MovieSessionService) injector.getInstance(
                MovieSessionService.class);

        List<MovieSession> movieSessions = List.of(
                new MovieSession(movies.get(FAST_AND_FURIOUS_MOVIE),
                        cinemaHalls.get(CINEMA_HALL_NUMBER_ONE), DATE_24_12_25),
                new MovieSession(movies.get(THE_GODFATHER_MOVIE),
                        cinemaHalls.get(CINEMA_HALL_NUMBER_ONE), DATE_24_12_25),
                new MovieSession(movies.get(FAST_AND_FURIOUS_MOVIE),
                        cinemaHalls.get(CINEMA_HALL_NUMBER_THREE), DATE_24_12_25),
                new MovieSession(movies.get(THE_DARK_KNIGHT_MOVIE),
                        cinemaHalls.get(CINEMA_HALL_NUMBER_TWO), DATE_24_12_27),
                new MovieSession(movies.get(THE_GODFATHER_MOVIE),
                        cinemaHalls.get(CINEMA_HALL_NUMBER_THREE), DATE_24_12_27)

        );

        for (MovieSession movieSession : movieSessions) {
            movieSessionService.add(movieSession);
        }

        System.out.println(movieSessionService.get(movieSessions.get(MOVIE_SESSION_ONE).getId()));
        movieSessionService.findAvailableSessions(movies.get(FAST_AND_FURIOUS_MOVIE).getId(),
                DATE_24_12_25).forEach(System.out::println);

    }

    private static List<Movie> createSampleMovies() {
        return List.of(
                new Movie("Fast and Furious",
                        "An action film about street racing, heists, and spies."),
                new Movie("The Shawshank Redemption", "Hope and freedom."),
                new Movie("The Godfather", "Crime family saga."),
                new Movie("The Dark Knight", "Hero versus villain."),
                new Movie("Pulp Fiction", "Crime and redemption.")
        );
    }

    private static List<CinemaHall> createSampleCinemaHalls() {
        return List.of(
                new CinemaHall(CINEMA_HALL_CAPACITY, "Hall number 1."),
                new CinemaHall(CINEMA_HALL_CAPACITY, "Hall number 2."),
                new CinemaHall(CINEMA_HALL_CAPACITY, "Hall number 3."),
                new CinemaHall(CINEMA_HALL_CAPACITY, "Hall number 4."),
                new CinemaHall(CINEMA_HALL_CAPACITY, "Hall number 5.")
        );
    }
}
