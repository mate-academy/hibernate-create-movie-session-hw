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
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie after = new Movie("After");
        after.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(after);
        movieService.getAll().forEach(System.out::println);
        System.out.println();

        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setCapacity(100);
        cinemaHall1.setDescription("First cinema hall");
        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall1);
        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall2.setCapacity(110);
        cinemaHall2.setDescription("Second cinema hall");
        cinemaHallService.add(cinemaHall2);
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println();

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall1);
        movieSession.setShowTime(LocalDateTime.of(2023, 8, 25, 15, 10));

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(cinemaHall2);
        movieSession1.setShowTime(LocalDateTime.of(2023, 8, 26, 15, 10));
        movieSessionService.add(movieSession1);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(after);
        movieSession2.setCinemaHall(cinemaHall2);
        movieSession2.setShowTime(LocalDateTime.of(2023, 8, 25, 11, 10));
        movieSessionService.add(movieSession2);

        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(after);
        movieSession3.setCinemaHall(cinemaHall2);
        movieSession3.setShowTime(LocalDateTime.of(2023, 8, 25, 15, 10));
        movieSessionService.add(movieSession3);

        movieSessionService.findAvailableSessions(after.getId(),
                LocalDate.of(2023, 8, 25)).forEach(System.out::println);

    }
}
