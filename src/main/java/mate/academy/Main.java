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
        final Injector injector = Injector.getInstance("mate.academy");
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        final CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        CinemaHall hallFirst = new CinemaHall();
        hallFirst.setDescription("A bid great cinema hall.");
        hallFirst.setCapacity(250);
        cinemaHallService.add(hallFirst);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(hallFirst);
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession);
        System.out.println(movieService.get(fastAndFurious.getId()));
        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setShowTime(LocalDateTime.of(2023,1,10,12,12));
        secondMovieSession.setCinemaHall(hallFirst);
        secondMovieSession.setMovie(fastAndFurious);
        movieSessionService.add(secondMovieSession);
        movieService.getAll().forEach(System.out::println);
        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.now()));
        System.out.println(movieSessionService.get(2L));
        System.out.println(cinemaHallService.get(1L));
        cinemaHallService.getAll().forEach(System.out::println);
    }
}
