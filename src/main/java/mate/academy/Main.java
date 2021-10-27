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
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall redHall = new CinemaHall();
        redHall.setDescription("Largest hall");
        redHall.setCapacity(300);
        cinemaHallService.add(redHall);
        System.out.println(cinemaHallService.get(redHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession firstSession = new MovieSession();
        firstSession.setMovie(fastAndFurious);
        firstSession.setCinemaHall(redHall);
        movieSessionService.add(firstSession);
        System.out.println(movieSessionService.get(firstSession.getId()));
        movieSessionService.findAllAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
