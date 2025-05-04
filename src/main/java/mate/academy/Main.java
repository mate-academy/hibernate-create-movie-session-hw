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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("\nAdded movies:");
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("Default hall");
        cinemaHall.setCapacity(50);

        CinemaHallService cinemaHallService =
                (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        System.out.println("\nAdded halls:");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession session1 = new MovieSession();
        session1.setCinemaHall(cinemaHall);
        session1.setMovie(fastAndFurious);
        session1.setShowTime(LocalDateTime.now());
        MovieSession session2 = new MovieSession();
        session2.setCinemaHall(cinemaHall);
        session2.setMovie(fastAndFurious);
        session2.setShowTime(LocalDateTime.now().plusDays(1));
        MovieSession session3 = new MovieSession();
        session3.setCinemaHall(cinemaHall);
        session3.setMovie(fastAndFurious);
        session3.setShowTime(LocalDateTime.now().plusHours(3));
        List<MovieSession> movieSessions = List.of(
                session1,
                session2,
                session3
        );
        MovieSessionService movieSessionService =
                (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);
        movieSessions.forEach(movieSessionService::add);
        System.out.println("\nAvailable sessions:");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
