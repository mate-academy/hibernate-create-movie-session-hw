package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println("-------------------------------------------------------------------------------");

        CinemaHall apolloHall = new CinemaHall();
        apolloHall.setCapacity(64);
        apolloHall.setDescription("Cozy hall with super comfy seats, 3DSound and UltraWideScreen");
        cinemaHallService.add(apolloHall);
        System.out.println(cinemaHallService.get(apolloHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("-------------------------------------------------------------------------------");

        MovieSession movieSession = new MovieSession();
        movieSession.setShowTime(LocalDateTime.now());
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(apolloHall);
        movieSessionService.add(movieSession);
        MovieSession movieSessionFromDb = movieSessionService.get(movieSession.getId());
        System.out.println(movieSessionFromDb);
        movieSessionService.findAvailableSessions
                (movieSessionFromDb.getId(), LocalDate.now()).forEach(System.out::println);
    }
}
