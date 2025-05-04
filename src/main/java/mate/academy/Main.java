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
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall hall = new CinemaHall();
        hall.setCapacity(100);
        hall.setDescription("Main Cinema Hall");
        cinemaHallService.add(hall);
        System.out.println(cinemaHallService.get(hall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(hall);
        movieSession.setShowTime(LocalDateTime
                .of(2024, 11, 10, 15, 0));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        LocalDate searchDate = LocalDate.of(2024, 11, 10);
        List<MovieSession> availableSessions =
                movieSessionService.findAvailableSessions(fastAndFurious.getId(), searchDate);
        availableSessions.forEach(System.out::println);

    }
}
