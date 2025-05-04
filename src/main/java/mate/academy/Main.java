package mate.academy;

import java.time.LocalDate;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");
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
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("bestCinema");
        cinemaHall.setCapacity(666);
        cinemaHallService.add(cinemaHall);
        System.out.printf(String.valueOf(cinemaHallService.get(cinemaHall.getId())));
        System.out.printf(String.valueOf(cinemaHallService.getAll()));

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setLocalDate(LocalDate.now());
        movieSessionService.add(movieSession);
        System.out.printf(String.valueOf(movieSessionService.get(movieSession.getId())));
        System.out.printf(movieSessionService.findAvailableSessions(movieSession.getId(),
                LocalDate.now()).toString());
    }
}
