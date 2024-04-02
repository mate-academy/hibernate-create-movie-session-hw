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
        movieService.getAll().forEach(System.out::println);

        System.out.println("\n\n");

        CinemaHall superLux = new CinemaHall();
        superLux.setCapacity(35);
        superLux.setDescription("New comfortable chairs with service just a button away!");
        cinemaHallService.add(superLux);
        System.out.println(cinemaHallService.get(superLux.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("\n\n");

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(superLux);
        movieSession.setMovie(fastAndFurious);
        movieSession.setShowTime(LocalDate.now());
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService.findAvailableSessions(movieSession.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
