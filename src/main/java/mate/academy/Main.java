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
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.getAll());
        CinemaHall cinemaTech = new CinemaHall(100,"Hall with cinema Tech+ technology");
        cinemaHallService.add(cinemaTech);
        CinemaHall reLux = new CinemaHall(60,"hall just for VIP");
        cinemaHallService.add(reLux);
        System.out.println(cinemaHallService.getAll());
        MovieSession firstSession
                = new MovieSession(movieService.get(fastAndFurious.getId()),
                cinemaHallService.get(cinemaTech.getId()),
                LocalDateTime.of(2022, 5,10,12,30));
        movieSessionService.add(firstSession);
        MovieSession secondSession
                = new MovieSession(movieService.get(fastAndFurious.getId()),
                cinemaHallService.get(reLux.getId()),
                LocalDateTime.of(2022, 5,11,18,15));
        movieSessionService.add(secondSession);
        System.out.println(movieSessionService.findAvailableSessions(1L,
                LocalDate.of(2022, 5, 10)));
    }
}
