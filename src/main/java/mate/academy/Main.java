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
    static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(242);
        firstCinemaHall.setDescription("Sad store");
        System.out.println(cinemaHallService.add(firstCinemaHall));
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));
        System.out.println(cinemaHallService.getAll());
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(firstCinemaHall);
        movieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.get(movieSession.getId());
        movieSessionService.findAvailableSessions(movieSession.getId(), LocalDate.now());
    }
}
