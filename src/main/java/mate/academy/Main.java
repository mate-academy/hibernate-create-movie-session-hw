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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        final MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService = (
                MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHall imax = new CinemaHall();
        imax.setCapacity(100);
        imax.setDescription("The cinema hall where you can watch "
                + "movies on the biggest screen");
        cinemaHallService.add(imax);

        MovieSession fastAndFuriousSession = new MovieSession(fastAndFurious, imax);
        fastAndFuriousSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(fastAndFuriousSession);

        System.out.println(movieSessionService.get(fastAndFuriousSession.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now()));

    }
}
