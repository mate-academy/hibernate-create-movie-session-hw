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
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final String DEF_MOVIE_DESC = "An action film about street racing,"
            + " heists, and spies.";
    private static final String DEF_CINEMA_HALL_DESC = "Large hall with comfortable chairs.";
    private static final int NUMBER_OF_ITERATIONS = 10;
    private static final int DEF_CAPACITY = 200;

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            Movie movie = createMovie("Fast and Furious. Part " + (i + 1));
            movieService.add(movie);
            System.out.println(movieService.get(movie.getId()));
        }
        movieService.getAll().forEach(System.out::println);
        Movie movieForSession = movieService.get(1L);

        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            int capacity = DEF_CAPACITY + (i * 10);
            CinemaHall cinemaHall = createCinemaHall(capacity);
            cinemaHallService.add(cinemaHall);
            System.out.println(cinemaHallService.get(cinemaHall.getId()));
        }
        cinemaHallService.getAll().forEach(System.out::println);

        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            CinemaHall cinemaHallForSession = cinemaHallService.get((long) i + 1);
            MovieSession movieSession = createMovieSession(movieForSession,
                    cinemaHallForSession, LocalDateTime.now());
            movieSessionService.add(movieSession);
        }
        movieSessionService.findAvailableSessions(1L,
                LocalDate.from(LocalDateTime.now())).forEach(System.out::println);
    }

    private static Movie createMovie(String title) {
        Movie movie = new Movie(title);
        movie.setDescription(Main.DEF_MOVIE_DESC);
        return movie;
    }

    private static CinemaHall createCinemaHall(int capacity) {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(capacity);
        cinemaHall.setDescription(Main.DEF_CINEMA_HALL_DESC);
        return cinemaHall;
    }

    private static MovieSession createMovieSession(Movie movie,
                                                   CinemaHall cinemaHall,
                                                   LocalDateTime showTime) {
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movie);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(showTime);
        return movieSession;
    }
}
