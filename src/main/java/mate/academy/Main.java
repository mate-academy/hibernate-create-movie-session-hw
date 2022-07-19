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
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(50);
        cinemaHall.setDescription("Green");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        Movie slowAndFurious = new Movie("Slow and Furious");
        slowAndFurious.setDescription("An action film about street fighting");
        movieService.add(slowAndFurious);
        System.out.println(movieService.get(slowAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(slowAndFurious);
        movieSession2.setCinemaHall(cinemaHall);
        movieSession2.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession2);

        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(slowAndFurious);
        movieSession3.setCinemaHall(cinemaHall);
        movieSession3.setShowTime(LocalDateTime.of(2021, 12, 3, 22, 3));
        movieSessionService.add(movieSession3);

        System.out.println(movieSessionService.get(2L));
        movieSessionService.findAvailableSessions(2L, LocalDate.parse("2021-12-03"))
                .forEach(System.out::println);
    }
}
