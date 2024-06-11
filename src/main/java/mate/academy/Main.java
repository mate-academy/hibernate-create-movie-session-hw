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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static final MovieService MOVIE_SERVICE =
            (MovieService) INJECTOR.getInstance(MovieService.class);
    private static final CinemaHallService CINEMA_HALL_SERVICE =
            (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
    private static final MovieSessionService MOVIE_SESSION_SERVICE =
            (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MOVIE_SERVICE.add(fastAndFurious);

        CinemaHall defaultCinemaHall = new CinemaHall();
        defaultCinemaHall.setCapacity(10);
        defaultCinemaHall.setDescription("Standard hall");
        CINEMA_HALL_SERVICE.add(defaultCinemaHall);

        LocalDateTime localDateTime = LocalDateTime.now();
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(defaultCinemaHall);
        movieSession.setShowTime(localDateTime);
        MOVIE_SESSION_SERVICE.add(movieSession);

        System.out.println("Movie service tests\n---------------------");
        System.out.println(MOVIE_SERVICE.get(fastAndFurious.getId()));
        MOVIE_SERVICE.getAll().forEach(System.out::println);
        System.out.println("MovieSession service tests\n---------------------");
        System.out.println(MOVIE_SESSION_SERVICE.get(movieSession.getId()));
        System.out.println(MOVIE_SESSION_SERVICE.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.now())
        );
        System.out.println("CinemaHall service tests\n---------------------");
        System.out.println(CINEMA_HALL_SERVICE.get(defaultCinemaHall.getId()));
        CINEMA_HALL_SERVICE.getAll().forEach(System.out::println);
    }
}
