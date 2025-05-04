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

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall redCinemaHall = new CinemaHall();
        redCinemaHall.setCapacity(100);
        redCinemaHall.setDescription("Comfortable cinema hall with quality video and sound.");

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(redCinemaHall);
        System.out.println(cinemaHallService.get(redCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setCinemaHall(redCinemaHall);
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setShowTime(LocalDateTime.parse("2022-11-07 11:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstMovieSession);
        movieSessionService.get(firstMovieSession.getId());
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.parse("2022-11-07", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .forEach(System.out::println);
    }
}
