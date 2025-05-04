package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("Red cinema hall");
        cinemaHall.setCapacity(100);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println();

        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setCinemaHall(cinemaHall);
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setShowTime(LocalDateTime.of(
                LocalDate.now(), LocalTime.of(21, 0)));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSession);
        System.out.println(movieSessionService.get(fastAndFuriousSession.getId()));
        movieSessionService.findAvailableSessions(
                        fastAndFuriousSession.getId(), LocalDate.of(2023, 2, 14))
                .forEach(System.out::println);
        movieSessionService.findAvailableSessions(fastAndFuriousSession.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
