package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
            .ofPattern(("dd MM yyyy HH:mm"));

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(1000);
        cinemaHall.setDescription("Hall #10");
        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        MovieSession morningMovieSession = new MovieSession();
        LocalDateTime morningTime = LocalDateTime.parse("01 04 2022 10:00", dateTimeFormatter);
        morningMovieSession.setMovie(fastAndFurious);
        morningMovieSession.setShowTime(morningTime);
        morningMovieSession.setCinemaHall(cinemaHall);
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(morningMovieSession);
        MovieSession eveningMovieSession = new MovieSession();
        LocalDateTime eveningTime = LocalDateTime.parse("01 04 2022 22:00", dateTimeFormatter);
        eveningMovieSession.setMovie(fastAndFurious);
        eveningMovieSession.setShowTime(eveningTime);
        eveningMovieSession.setCinemaHall(cinemaHall);
        movieSessionService.add(eveningMovieSession);
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 4, 1)));
    }
}
