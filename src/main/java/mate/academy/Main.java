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

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall vipHall = new CinemaHall();
        vipHall.setCapacity(90);
        vipHall.setDescription("VIP Hall");
        cinemaHallService.add(vipHall);
        System.out.println(cinemaHallService.get(vipHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession testMovieSession = new MovieSession();
        testMovieSession.setMovie(fastAndFurious);
        testMovieSession.setCinemaHall(vipHall);
        testMovieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(testMovieSession);
        System.out.println(movieSessionService.get(testMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.from(LocalDateTime.now())));
    }
}
