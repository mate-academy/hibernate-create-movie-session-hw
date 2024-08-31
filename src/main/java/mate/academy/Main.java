package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Inject;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final String separator = System.lineSeparator();

    @Inject
    public static void main(String[] args) {
        Movie movie = new Movie();
        movie.setTitle("First Movie");
        movie.setDescription("Movie with id 1");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        System.out.println(movieService.add(movie));
        System.out.println(movieService.get(1L));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(65);
        cinemaHall.setDescription("Cinema Hall with id 1");
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        System.out.println(separator + cinemaHallService.add(cinemaHall));
        System.out.println(cinemaHallService.get(1L));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movie);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        System.out.println(separator + movieSessionService.add(movieSession));
        System.out.println(movieSessionService.get(1L));
        movieSessionService.findAvailableSession(1L, LocalDate.now()).forEach(System.out::println);
    }
}
