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
import mate.academy.service.impl.CinemaHallServiceImpl;
import mate.academy.service.impl.MovieServiceImpl;
import mate.academy.service.impl.MovieSessionServiceImpl;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService = (MovieServiceImpl)
                                        injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallServiceImpl)
                                        injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionServiceImpl)
                                         injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(150);
        redHall.setDescription("Red cinema hall");

        CinemaHall blackHall = new CinemaHall();
        blackHall.setCapacity(100);
        blackHall.setDescription("Black cinema hall");

        cinemaHallService.add(redHall);
        cinemaHallService.add(blackHall);

        System.out.println(cinemaHallService.getAll());
        System.out.println(cinemaHallService.get(1L));
        System.out.println(cinemaHallService.get(2L));

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(redHall);
        movieSession.setShowTime(LocalDateTime.now());

        MovieSession anotherMovieSession = new MovieSession();
        anotherMovieSession.setMovie(fastAndFurious);
        anotherMovieSession.setCinemaHall(blackHall);
        anotherMovieSession.setShowTime(LocalDateTime.now().plusDays(1));

        movieSessionService.add(movieSession);
        movieSessionService.add(anotherMovieSession);

        System.out.println(movieSessionService.get(1L));
        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.now()));
    }
}
