package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import mate.academy.service.impl.CinemaHallServiceImpl;
import mate.academy.service.impl.MovieServiceImpl;
import mate.academy.service.impl.MovieSessionServiceImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        final Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious",
                "An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie horror = new Movie("Texas Chainsaw",
                "Family friendly film.");
        movieService.add(horror);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        CinemaHall cinemaHallNormal = new CinemaHall(150, "Everyday hall");
        cinemaHallService.add(cinemaHallNormal);
        CinemaHall cinemaHallVip = new CinemaHall(30, "Only for Vip person");
        cinemaHallService.add(cinemaHallVip);
        System.out.println(cinemaHallService.get(cinemaHallVip.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        MovieSession firstSession = new MovieSession(fastAndFurious, cinemaHallVip, LocalDateTime.now());
        movieSessionService.add(firstSession);
        MovieSession secondSession = new MovieSession(horror, cinemaHallNormal, LocalDateTime.now());
        movieSessionService.add(secondSession);
        System.out.println(movieSessionService.get(firstSession.getId()));
        movieSessionService.findAvailableSessions(secondSession.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
