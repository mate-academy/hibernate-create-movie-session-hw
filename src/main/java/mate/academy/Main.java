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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall brilliantHall = new CinemaHall(75,"Brilliant hall");
        CinemaHall emeraldHall = new CinemaHall(50,"Emerald hall");
        cinemaHallService.add(brilliantHall);
        cinemaHallService.add(emeraldHall);
        cinemaHallService.getAll().forEach(System.out::println);
        LocalDateTime currentTime = LocalDateTime.now();
        MovieSession movieSession = new MovieSession(fastAndFurious,
                brilliantHall,
                currentTime);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService.findAvailableSessions(movieSession.getId(),
                LocalDate.from(currentTime)).forEach(System.out::println);

    }
}
