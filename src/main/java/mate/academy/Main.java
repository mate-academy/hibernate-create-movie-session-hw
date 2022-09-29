package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final String PACKAGE_NAME = "mate.academy";
    private static final Injector injector = Injector.getInstance(PACKAGE_NAME);

    public static void main(String[] args) {
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall multiplex = new CinemaHall();
        multiplex.setCapacity(200);
        multiplex.setDescription("Mykolaiv City Center Multiplex");
        cinemaHallService.add(multiplex);
        System.out.println(cinemaHallService.get(multiplex.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession forAdults = new MovieSession();
        forAdults.setCinemaHall(multiplex);
        forAdults.setMovie(fastAndFurious);
        forAdults.setShowTime(LocalDateTime.of(
                2022, 10, 10, 22, 30));
        movieSessionService.add(forAdults);
        System.out.println(movieService.get(forAdults.getId()));
        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.of(2022, 10, 10));
        availableSessions.forEach(System.out::println);
        if (!availableSessions.isEmpty()) {
            System.out.println("Session Found: \"" + availableSessions.get(0).getMovie().getTitle()
                    + "\" at " + availableSessions.get(0).getShowTime()
                    + " in " + availableSessions.get(0).getCinemaHall().getDescription());
        }
    }
}
