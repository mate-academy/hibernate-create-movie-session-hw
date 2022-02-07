package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie dayAfterTomorrow = new Movie("The Day After Tomorrow");
        dayAfterTomorrow.setDescription("Loosely based on the theory of “abrupt climate change");
        movieService.add(dayAfterTomorrow);
        System.out.println(movieService.get(dayAfterTomorrow.getId()));
        movieService.getAll().forEach(System.out::println);

        Movie test = new Movie("TEST Movie");
        test.setDescription("testing");
        movieService.add(test);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall blockbuster =
                new CinemaHall("Blockbuster is a modern movie theatre with IMAX technology");
        blockbuster.setCapacity(200);
        cinemaHallService.add(blockbuster);
        System.out.println(cinemaHallService.get(blockbuster.getId()));

        CinemaHall multiplex = new CinemaHall("Multiplex is the largest cinema network in Ukraine");
        multiplex.setCapacity(300);
        cinemaHallService.add(multiplex);
        System.out.println(cinemaHallService.get(multiplex.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession movieSession1 = new MovieSession(movieService.get(1L),
                cinemaHallService.get(1L),
                LocalDateTime.of(2022, Month.FEBRUARY, 7, 10, 0));
        MovieSession movieSession2 = new MovieSession(movieService.get(1L),
                cinemaHallService.get(2L),
                LocalDateTime.of(2022, Month.FEBRUARY, 7, 14, 0));
        MovieSession movieSession3 = new MovieSession(movieService.get(2L),
                cinemaHallService.get(1L),
                LocalDateTime.of(2022, Month.FEBRUARY, 7, 16, 0));
        MovieSession movieSession4 = new MovieSession(movieService.get(2L),
                cinemaHallService.get(2L),
                LocalDateTime.of(2022, Month.FEBRUARY, 7, 18, 0));
        MovieSession testSession = new MovieSession(movieService.get(3L),
                cinemaHallService.get(1L),
                LocalDateTime.of(2022, Month.FEBRUARY, 8, 18, 0));

        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);
        movieSessionService.add(movieSession4);
        movieSessionService.add(testSession);

        System.out.println(movieSessionService.get(movieSession1.getId()));
        System.out.println(movieSessionService.get(movieSession2.getId()));
        System.out.println(movieSessionService.get(movieSession3.getId()));
        System.out.println(movieSessionService.get(movieSession4.getId()));
        System.out.println(movieSessionService.get(testSession.getId()));

        LocalDate date = LocalDate.now();
        System.out.println(movieSessionService.findAvailableSessions(1L, date));
        System.out.println(movieSessionService.findAvailableSessions(2L, date));
        System.out.println(movieSessionService.findAvailableSessions(3L, date));

    }
}
