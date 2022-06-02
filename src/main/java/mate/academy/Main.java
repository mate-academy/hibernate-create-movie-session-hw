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

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall simpleCinemaHall = new CinemaHall();
        simpleCinemaHall.setDescription("simple hall");
        simpleCinemaHall.setCapacity(228);
        cinemaHallService.add(simpleCinemaHall);
        System.out.println(cinemaHallService.get(simpleCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession coolMovieSession = new MovieSession();
        coolMovieSession.setShowTime(LocalDateTime.now());
        coolMovieSession.setMovie(fastAndFurious);
        coolMovieSession.setCinemaHall(simpleCinemaHall);
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(coolMovieSession);
        System.out.println(movieSessionService.get(coolMovieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
