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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie fastAndFurious2 = new Movie("Fast and Furious2");
        fastAndFurious.setDescription("An action film about street racing, heists, and other.");
        movieService.add(fastAndFurious2);
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(150);
        cinemaHall.setDescription("some description");
        cinemaHallService.add(cinemaHall);
        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall.setCapacity(50);
        cinemaHall.setDescription("small hall");
        cinemaHallService.add(cinemaHall2);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        LocalDateTime localDateTime = LocalDateTime.now();
        movieSession.setTime(localDateTime);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        MovieSession vipMovieSession = new MovieSession();
        vipMovieSession.setMovie(fastAndFurious2);
        movieSession.setCinemaHall(cinemaHall2);
        LocalDateTime vipMovieTime = LocalDateTime.of(2022,12, 31, 23, 30);
        movieSession.setTime(vipMovieTime);
        movieSessionService.add(vipMovieSession);
        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.now()));
    }
}
