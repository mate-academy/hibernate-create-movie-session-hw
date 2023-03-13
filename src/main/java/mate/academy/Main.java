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
    public static void main(String[] args) {
        Injector instance = Injector.getInstance("mate.academy");
        MovieService movieService =
                (MovieService) instance.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) instance.getInstance(CinemaHallService.class);
        CinemaHall gateNamberOne = new CinemaHall(50);
        gateNamberOne.setDescription("Cinema hall for fifty seats");
        cinemaHallService.add(gateNamberOne);
        System.out.println(cinemaHallService.get(gateNamberOne.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService =
                (MovieSessionService) instance.getInstance(MovieSessionService.class);
        MovieSession movieSessionFirst = new MovieSession(fastAndFurious, gateNamberOne);
        movieSessionFirst.setLocalDateTime(LocalDateTime.now());
        movieSessionService.add(movieSessionFirst);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
