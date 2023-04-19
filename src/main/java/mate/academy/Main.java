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
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        MovieSession movieSession = new MovieSession();
        movieSession.setShowTime(LocalDateTime.of(2023,6, 10, 12, 0));
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(200);
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        cinemaHall.setDescription("Anime");
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSession.setCinemaHall(cinemaHall);
        movieSessionService.add(movieSession);
        System.out.println(cinemaHallService.getAll());
        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.of(2023, 6, 10));
        System.out.println(movieService.get(fastAndFurious.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println(availableSessions);
        movieService.getAll().forEach(System.out::println);
    }
}
