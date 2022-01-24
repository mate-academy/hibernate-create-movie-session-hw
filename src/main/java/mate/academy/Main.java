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
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        CinemaHallService cinemaHallService = (CinemaHallService) injector.getInstance(
                CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(3);
        cinemaHall.setDescription("3D");
        cinemaHallService.add(cinemaHall);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieService.get(1L));
        movieSession.setShowTime(LocalDateTime.of(2022, 1, 24, 14, 30));
        movieSession.setCinemaHall(cinemaHallService.get(1L));
        MovieSessionService sessionService = (MovieSessionService) injector.getInstance(
                MovieSessionService.class);
        sessionService.add(movieSession);
        List<MovieSession> availableSessions = sessionService.findAvailableSessions(1L,
                LocalDate.of(2022, 1, 24));
        System.out.println(availableSessions);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
    }
}
