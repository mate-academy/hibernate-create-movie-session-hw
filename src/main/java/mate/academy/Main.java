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
    private static Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        final MovieService movieService = (MovieService)
                injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("-----------------");
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall(150, "first cinema hall");
        cinemaHallService.add(firstCinemaHall);
        System.out.println(movieService.get(firstCinemaHall.getId()));
        System.out.println("-----------------");

        MovieSession fastAndFuriousSessionToday = new MovieSession(fastAndFurious,
                firstCinemaHall, LocalDateTime.now());
        MovieSession fastAndFuriousSessionTomorrow = new MovieSession(fastAndFurious,
                firstCinemaHall, LocalDateTime.of(2023, 2, 3, 14, 0));
        movieSessionService.add(fastAndFuriousSessionToday);
        movieSessionService.add(fastAndFuriousSessionTomorrow);
        System.out.println(movieSessionService.get(1L));
        System.out.println(movieSessionService.get(2L));
        System.out.println("-----------------");
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2023,1,22)));
    }
}
