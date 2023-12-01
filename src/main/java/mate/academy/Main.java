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
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHall aquamarine = new CinemaHall();
        aquamarine.setCapacity(50);
        aquamarine.setDescription("A hall painted in your favourite aquamarine colour.");
        cinemaHallService.add(aquamarine);

        MovieSession aquamarineFastAndFurious = new MovieSession();
        aquamarineFastAndFurious.setMovie(fastAndFurious);
        aquamarineFastAndFurious.setCinemaHall(aquamarine);
        aquamarineFastAndFurious.setShowTime(LocalDateTime.now());
        movieSessionService.add(aquamarineFastAndFurious);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("movie service test");
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println("cinema hall service test");
        System.out.println(cinemaHallService.get(aquamarine.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("movie session service test");
        System.out.println(movieSessionService.get(aquamarineFastAndFurious.getId()));
        System.out.println("FIND AVAILABLE test");
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()));
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
