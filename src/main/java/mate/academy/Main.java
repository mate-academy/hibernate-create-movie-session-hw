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
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("lorem ipsum");
        cinemaHall.setCapacity(100);
        cinemaHallService.add(cinemaHall);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession);
        System.out.println(movieService.get(fastAndFurious.getId()));
        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setShowTime(LocalDateTime
                .of(2023,2,14,21,30));
        secondMovieSession.setCinemaHall(cinemaHall);
        secondMovieSession.setMovie(fastAndFurious);
        movieSessionService.add(secondMovieSession);
        movieService.getAll().forEach(System.out::println);
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now()));
        System.out.println(movieSessionService.get(movieSession.getId()));
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
    }
}
