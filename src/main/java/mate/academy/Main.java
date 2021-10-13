package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private static MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        CinemaHall cinemaHall = new CinemaHall(99, "Nice and beauty");
        cinemaHallService.add(cinemaHall);
        CinemaHall newCinemaHall = cinemaHallService.get(2L);
        System.out.println(newCinemaHall);
        Movie movie = movieService.get(2L);
        System.out.println(movie);
        movieService.getAll().stream().forEach(System.out::println);
        LocalTime time = LocalTime.now();
        LocalDate localDate = LocalDate.of(2021, 10, 25);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, time);
        MovieSession movieSession = new MovieSession(movie, cinemaHall, localDateTime);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(3L));
        List<MovieSession> availableSessions
                = movieSessionService.findAvailableSessions(2L, localDate);
        availableSessions.stream().forEach(System.out::println);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
    }
}
