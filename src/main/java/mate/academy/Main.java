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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        LocalDate localDate = LocalDate.now();

        Movie fastAndFurious = new Movie("Fast and Furious",
                "An action film about street racing, heists, and spies.");
        CinemaHall cinemaHall3D = new CinemaHall(15,
                "3D screen and 8D sound");
        MovieSession movieSession = new MovieSession(fastAndFurious, cinemaHall3D, localDate);

        movieService.add(fastAndFurious);
        cinemaHallService.add(cinemaHall3D);
        movieSessionService.add(movieSession);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.getAll());

        System.out.println(cinemaHallService.get(1L));
        System.out.println(cinemaHallService.getAll());

        System.out.println(movieSessionService.get(1L));
        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.now()));
    }
}
