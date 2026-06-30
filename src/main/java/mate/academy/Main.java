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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(100);
        redHall.setDescription("Red Hall - IMAX");
        cinemaHallService.add(redHall);
        System.out.println(cinemaHallService.get(redHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession sessionToday = new MovieSession();
        sessionToday.setMovie(fastAndFurious);
        sessionToday.setCinemaHall(redHall);
        sessionToday.setShowTime(LocalDateTime.of(2026, 6, 30, 19, 0));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(sessionToday);

        MovieSession sessionTomorrow = new MovieSession();
        sessionTomorrow.setMovie(fastAndFurious);
        sessionTomorrow.setCinemaHall(redHall);
        sessionTomorrow.setShowTime(LocalDateTime.of(2026, 7, 1, 12, 0));
        movieSessionService.add(sessionTomorrow);

        System.out.println(movieSessionService.get(sessionToday.getId()));
        System.out.println("Available sessions for movie "
                + fastAndFurious.getTitle() + " on 2026-06-30:");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.of(2026, 6, 30))
                .forEach(System.out::println);
    }
}
