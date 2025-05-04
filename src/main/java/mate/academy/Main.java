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
    private static final LocalDateTime time =
            LocalDateTime.of(2024, 6, 24, 12, 0);

    public static void main(String[] args) {
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        // creating entities
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setNumberOfHall(4);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(time);

        // test
        movieService.add(fastAndFurious);
        System.out.println("Movie by id:" + movieService.get(fastAndFurious.getId()));
        System.out.print("Get all from movie: ");
        movieService.getAll().forEach(System.out::println);

        cinemaHallService.add(cinemaHall);
        System.out.println("Cinema hall by id:" + cinemaHallService.get(fastAndFurious.getId()));
        System.out.print("Get all from cinema hall: ");
        cinemaHallService.getAll().forEach(System.out::println);

        movieSessionService.add(movieSession);
        System.out.println("Movie session by id:"
                + movieSessionService.get(fastAndFurious.getId()));
        System.out.print("Get all from movie session: ");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.from(time)).forEach(System.out::println);
    }
}
