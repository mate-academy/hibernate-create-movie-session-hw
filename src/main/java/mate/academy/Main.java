package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        CinemaHall cinemaHall = new CinemaHall(40, "Cinema Galactic");
        cinemaHallService.add(cinemaHall);

        Movie djangoMovie = new Movie("Django Unchained");
        djangoMovie.setDescription("Django Unchained is a 2012 American revisionist Western");
        movieService.add(djangoMovie);

        MovieSession movieSession = new MovieSession(djangoMovie, cinemaHall,
                LocalDateTime.parse("2023-12-12T12:00:00", DateTimeFormatter.ISO_DATE_TIME));
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService
                .findAvailableSession(1L, LocalDate.of(2023, 12, 12)));
    }
}
