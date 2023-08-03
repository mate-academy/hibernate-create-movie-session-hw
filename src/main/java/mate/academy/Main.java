package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static MovieService movieService;
    private static CinemaHallService cinemaHallService;
    private static MovieSessionService movieSessionService;

    public static void main(String[] args) {
        movieService = (MovieService) injector.getInstance(MovieService.class);
        cinemaHallService = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        movieSessionService = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        initMovies();
        initCinemaHalls();
        initMovieSessions();
        System.out.println("Get all movies ssessions.");
        movieSessionService.findAll().forEach(System.out::println);
        Long movieId = 2L;
        LocalDate onDate = LocalDate.of(2022,8,02);
        System.out.println("Get all movie sessions of "
                + movieService.get(movieId) + " on date " + onDate);
        movieSessionService.findAvailableSessions(movieId,onDate).forEach(System.out::println);
        System.out.println("Get cinema hall with id = 1");
        System.out.println(cinemaHallService.get(1L));
        System.out.println("Get cinema hall with id = 55");
        System.out.println(cinemaHallService.get(55L));
    }

    private static void initMovies() {
        movieService.add(new Movie("Fast and Furious",
                "An action film about street racing, heists, and spies."));
        movieService.add(new Movie("Brave heart", "Movie about brave hero."));
    }

    private static void initCinemaHalls() {
        cinemaHallService.add(new CinemaHall(100, "RED hall"));
        cinemaHallService.add(new CinemaHall(50, "GREEN hall"));
        cinemaHallService.add(new CinemaHall(500, "BIG hall"));
    }

    private static void initMovieSessions() {
        Movie movie;
        CinemaHall cinemaHall;
        LocalDateTime showTime;
        movie = movieService.get(1L);
        cinemaHall = cinemaHallService.get(1L);
        showTime = LocalDateTime.of(2022, 8, 02, 10, 00);
        movieSessionService.add(new MovieSession(movie, cinemaHall, showTime));
        movie = movieService.get(2L);
        cinemaHall = cinemaHallService.get(2L);
        showTime = LocalDateTime.of(2022, 8, 02, 12, 00);
        movieSessionService.add(new MovieSession(movie, cinemaHall, showTime));
        movie = movieService.get(2L);
        cinemaHall = cinemaHallService.get(3L);
        showTime = LocalDateTime.of(2022, 8, 02, 14, 00);
        movieSessionService.add(new MovieSession(movie, cinemaHall, showTime));
        movie = movieService.get(2L);
        cinemaHall = cinemaHallService.get(2L);
        showTime = LocalDateTime.of(2022, 8, 02, 18, 00);
        movieSessionService.add(new MovieSession(movie, cinemaHall, showTime));
    }
}
