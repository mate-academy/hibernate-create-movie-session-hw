package mate.academy;

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
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists and spies.");
        // movieService.add(fastAndFurious);
        // movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(120);
        cinemaHall.setDescription("Monotonous emerald color hall for awesome time spending");
        cinemaHallService.add(cinemaHall);

        MovieSession movieSession = new MovieSession();
        // movieSession.setMovie(movieService.get(1L));
        // We cannot set a movie that is transient because Cascade.PERSIST isn't specified
        // We can only set with entities that have ID set
        // (more generally entities that have persisted state)
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHallService.get(1L));
        movieSessionService.add(movieSession);
    }
}
