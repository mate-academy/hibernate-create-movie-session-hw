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
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie dune = new Movie("Dune");
        dune.setDescription("Sci-Fy");
        movieService.add(fastAndFurious);
        movieService.add(dune);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall bigCinemaHall = new CinemaHall(100, "Big hall");
        CinemaHall smallCinemaHall = new CinemaHall(30, "Small Hall");
        cinemaHallService.add(bigCinemaHall);
        cinemaHallService.add(smallCinemaHall);
        System.out.println(cinemaHallService.get(2L));
        List<CinemaHall> cinemaHalls = cinemaHallService.getAll();
        System.out.println(cinemaHalls);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession todayMorningTimeSession = new MovieSession(dune,
                bigCinemaHall,
                LocalDateTime.of(2023, 05, 03, 9, 00));
        MovieSession todayDayTimeSession = new MovieSession(dune,
                smallCinemaHall,
                LocalDateTime.of(2023, 05, 03, 14, 00));
        MovieSession todayEveningTimeSession = new MovieSession(fastAndFurious,
                bigCinemaHall,
                LocalDateTime.of(2023, 05, 03, 19, 00));
        MovieSession tomorrowSession = new MovieSession(dune,
                smallCinemaHall,
                LocalDateTime.of(2023, 05, 04, 8, 00));
        MovieSession yesterdaySession = new MovieSession(fastAndFurious,
                smallCinemaHall,
                LocalDateTime.of(2023, 05, 02, 15, 00));
        movieSessionService.add(todayMorningTimeSession);
        movieSessionService.add(todayDayTimeSession);
        movieSessionService.add(todayEveningTimeSession);
        movieSessionService.add(tomorrowSession);
        movieSessionService.add(yesterdaySession);
        System.out.println(movieSessionService.get(3L));
        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(
                2L, LocalDate.of(2023, 05, 03));
        System.out.println(availableSessions);
    }
}
