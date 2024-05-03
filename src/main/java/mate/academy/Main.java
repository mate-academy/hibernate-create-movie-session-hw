package mate.academy;

import static mate.academy.lib.Injector.getInstance;

import java.time.LocalDate;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static final Injector injector =
            getInstance("mate.academy");
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

        Movie harryPotter = new Movie("Harry Potter");
        harryPotter.setDescription("philosopher stone!!!");
        movieService.add(harryPotter);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(harryPotter.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall defaultHall = new CinemaHall();
        defaultHall.setCapacity(50);
        defaultHall.setDescription("Default hall");

        CinemaHall luxuryHall = new CinemaHall();
        luxuryHall.setCapacity(10);
        luxuryHall.setDescription("Luxury hall");

        cinemaHallService.add(defaultHall);
        cinemaHallService.add(luxuryHall);
        System.out.println(cinemaHallService.get(defaultHall.getId()));
        System.out.println(cinemaHallService.get(luxuryHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession fastAndFuriousFirstSession = new MovieSession();
        fastAndFuriousFirstSession.setCinemaHall(defaultHall);
        fastAndFuriousFirstSession.setDate(LocalDate.of(2024, 5, 14));
        fastAndFuriousFirstSession.setMovie(fastAndFurious);
        movieSessionService.add(fastAndFuriousFirstSession);

        MovieSession anotherFastAndFuriousFirstSession = new MovieSession();
        anotherFastAndFuriousFirstSession.setCinemaHall(luxuryHall);
        anotherFastAndFuriousFirstSession.setDate(LocalDate.of(2024, 5, 14));
        anotherFastAndFuriousFirstSession.setMovie(fastAndFurious);
        movieSessionService.add(anotherFastAndFuriousFirstSession);

        MovieSession fastAndFuriousSecondSession = new MovieSession();
        fastAndFuriousSecondSession.setCinemaHall(luxuryHall);
        fastAndFuriousSecondSession.setDate(LocalDate.of(2024, 5, 15));
        fastAndFuriousSecondSession.setMovie(fastAndFurious);
        movieSessionService.add(fastAndFuriousSecondSession);

        MovieSession harryPotterFirstSession = new MovieSession();
        harryPotterFirstSession.setCinemaHall(luxuryHall);
        harryPotterFirstSession.setDate(LocalDate.of(2024, 6, 1));
        harryPotterFirstSession.setMovie(harryPotter);
        movieSessionService.add(harryPotterFirstSession);

        MovieSession harryPotterSecondSession = new MovieSession();
        harryPotterSecondSession.setCinemaHall(luxuryHall);
        harryPotterSecondSession.setDate(LocalDate.of(2024, 6, 2));
        harryPotterSecondSession.setMovie(harryPotter);
        movieSessionService.add(harryPotterSecondSession);

        movieSessionService
                .findAvailableSessions(1L,
                        LocalDate.of(2024, 5, 14))
                .forEach(System.out::println);
    }
}
