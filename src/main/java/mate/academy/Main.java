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
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(20);
        cinemaHall.setDescription("good hall");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(c -> System.out.println(c));

        LocalDateTime firstSessionTime =
                LocalDateTime.of(2026, 8, 15, 11, 30);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setCinemaHall(cinemaHall);
        firstMovieSession.setLocalDateTime(firstSessionTime);

        LocalDateTime secondSessionTime =
                LocalDateTime.of(2026, 8, 15, 12, 30);

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(fastAndFurious);
        secondMovieSession.setCinemaHall(cinemaHall);
        secondMovieSession.setLocalDateTime(secondSessionTime);

        LocalDateTime thirdSessionTime =
                LocalDateTime.of(2026, 8, 15, 13, 30);

        MovieSession thirdMovieSession = new MovieSession();
        thirdMovieSession.setMovie(fastAndFurious);
        thirdMovieSession.setCinemaHall(cinemaHall);
        thirdMovieSession.setLocalDateTime(thirdSessionTime);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);
        movieSessionService.add(thirdMovieSession);

        System.out.println("firstMovieSession by ID: "
                + movieSessionService.get(firstMovieSession.getId()));
        System.out.println("secondMovieSession by ID: "
                + movieSessionService.get(secondMovieSession.getId()));
        System.out.println("thirdMovieSession by ID: "
                + movieSessionService.get(thirdMovieSession.getId()));

        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.of(2026, 8, 15));
        System.out.println(availableSessions);
    }
}
