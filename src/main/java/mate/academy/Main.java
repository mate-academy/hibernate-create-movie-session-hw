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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious 1");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall bigHall = new CinemaHall();
        bigHall.setCapacity(150);
        bigHall.setDescription("Big hall with 150 places");
        cinemaHallService.add(bigHall);

        CinemaHall smallHall = new CinemaHall();
        smallHall.setCapacity(40);
        smallHall.setDescription("Small hall with 40 places");
        cinemaHallService.add(smallHall);

        System.out.println(cinemaHallService.get(smallHall.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSession smallSession = new MovieSession();
        smallSession.setMovie(fastAndFurious);
        smallSession.setCinemaHall(smallHall);
        smallSession.setShowTime(LocalDateTime.of(2021, 7, 7, 7, 0));
        movieSessionService.add(smallSession);
        System.out.println(movieSessionService.get(smallSession.getId()));

        MovieSession bigSession = new MovieSession();
        bigSession.setMovie(fastAndFurious);
        bigSession.setCinemaHall(bigHall);
        bigSession.setShowTime(LocalDateTime.of(2021, 7, 7, 16, 0));
        movieSessionService.add(bigSession);
        System.out.println(movieSessionService.get(bigSession.getId()));

        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2021, 7, 7)).forEach(System.out::println);
    }
}
