package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static void main(String[] args) {
        Injector inject = Injector.getInstance("mate.academy");
        MovieService movieService = (MovieService) inject
                .getInstance(MovieService.class);
        final MovieSessionService movieSessionService = (MovieSessionService) inject
                .getInstance(MovieSessionService.class);
        final CinemaHallService cinemaHallService = (CinemaHallService) inject
                .getInstance(CinemaHallService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("An action film about street racing, heists, and spies.");
        cinemaHall.setCapacity(23);
        cinemaHallService.add(cinemaHall);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        LocalDateTime ofFilmSession = LocalDateTime
                .of(2022, 2, 3, 21, 45);
        movieSession.setLocalDateTime(ofFilmSession);
        movieSessionService.add(movieSession);

        LocalDate of1 = LocalDate
                .of(2022, 2, 3);

        movieSessionService.get(1L);
        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(1L, of1);
        availableSessions.forEach(System.out::println);

    }
}
