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

    public static void main(String[] args) {
        System.out.println("Testing MovieService:");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("Testing CinemaHallService:");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final CinemaHall cinemaHall01 = new CinemaHall(); // first hall
        cinemaHall01.setCapacity(100);
        cinemaHall01.setDescription("Description for hall # 1");
        cinemaHallService.add(cinemaHall01);
        System.out.println(cinemaHallService.get(cinemaHall01.getId()));
        final CinemaHall cinemaHall02 = new CinemaHall(); // second hall
        cinemaHall02.setCapacity(200);
        cinemaHall02.setDescription("Description for hall # 2");
        cinemaHallService.add(cinemaHall02);
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("Testing MovieSessionService:");
        final MovieSession movieSession01 = new MovieSession();
        movieSession01.setMovie(fastAndFurious);
        movieSession01.setCinemaHall(cinemaHall01);
        movieSession01.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession01);
        System.out.println(movieSessionService.get(movieSession01.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
