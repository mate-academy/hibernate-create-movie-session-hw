package mate.academy;

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

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie disturbia = new Movie("Disturbia");
        disturbia.setDescription("A teen living under house arrest "
                + "becomes convinced his neighbor is a serial killer.");
        movieService.add(disturbia);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall firstHall = new CinemaHall(100);
        firstHall.setDescription("hall #1");
        cinemaHallService.add(firstHall);

        CinemaHall secondHall = new CinemaHall(200);
        secondHall.setDescription("hall #2");
        cinemaHallService.add(secondHall);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession movieSession1 =
                new MovieSession(fastAndFurious, firstHall, LocalDateTime.now());

        movieSessionService.add(movieSession1);

        System.out.println(movieService.get(disturbia.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println(cinemaHallService.get(firstHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println(movieSessionService.get(movieSession1.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDateTime.now().toLocalDate()));
    }
}
