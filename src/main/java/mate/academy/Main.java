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
        System.out.println(".....Insert movie to DB.....");
        movieService.add(fastAndFurious);
        System.out.println(".....Get movie by id.....");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(".....Get all movies.....");
        movieService.getAll().forEach(System.out::println);

        CinemaHall multiplex = new CinemaHall(500);
        multiplex.setDescription("A multiplex is a cinema with several, "
                + "usually three or more screens; a complex of cinemas.");
        System.out.println(".....Insert cinema hall to DB.....");
        cinemaHallService.add(multiplex);
        System.out.println(".....Get cinema hall by id.....");
        System.out.println(cinemaHallService.get(multiplex.getId()));

        CinemaHall cinemaPlanet = new CinemaHall(1000);
        cinemaPlanet.setDescription("Cinema planet IMAX cinema in Kiev");
        System.out.println(".....Insert cinema hall to DB.....");
        cinemaHallService.add(cinemaPlanet);
        System.out.println(".....Get cinema hall by id.....");
        System.out.println(cinemaHallService.get(cinemaPlanet.getId()));

        System.out.println(".....Get all cinema halls.....");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession fastAndFuriousSessionInMultiplex = new MovieSession(fastAndFurious, multiplex,
                LocalDateTime.of(2021, 6, 4,2,30));
        System.out.println(".....Insert movie session to DB.....");
        movieSessionService.add(fastAndFuriousSessionInMultiplex);
        System.out.println(".....Get movie session by id.....");
        System.out.println(movieSessionService.get(fastAndFuriousSessionInMultiplex.getId()));

        MovieSession fastAndFuriousSessionInCinemaPlanet =
                new MovieSession(fastAndFurious, cinemaPlanet,
                LocalDateTime.of(2021, 6, 5,2,15));
        System.out.println(".....Insert movie session to DB.....");
        movieSessionService.add(fastAndFuriousSessionInCinemaPlanet);
        System.out.println(".....Get movie session by id.....");
        System.out.println(movieSessionService.get(fastAndFuriousSessionInCinemaPlanet.getId()));

        System.out.println(".....Get all movie session from DB.....");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2021, 6, 4))
                .forEach(System.out::println);
    }
}
