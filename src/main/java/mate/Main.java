package mate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.lib.Injector;
import mate.model.Movie;
import mate.model.MovieSession;
import mate.service.MovieService;
import mate.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        MovieService movieService = (MovieService)injector
                .getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("__________________________________");
        MovieSessionService movieSessionService = (MovieSessionService)injector
                .getInstance(MovieSessionService.class);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession);
        System.out.println(movieSession);
        System.out.println("____________________________________");
        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.now()));
        System.out.println("____________________________________");
        System.out.println(LocalDate.now());
        LocalDate time = LocalDate.now();
        System.out.println(time.atStartOfDay());
    }
}
