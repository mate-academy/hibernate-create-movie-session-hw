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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHall riverMall = new CinemaHall();
        riverMall.setCapacity(50);
        riverMall.setDescription("Planeta Kino (River Mall)");
        cinemaHallService.add(riverMall);

        MovieSession movieSession1At16 = new MovieSession();
        movieSession1At16.setShowTime(
                LocalDateTime.of(2022, 7, 1, 16, 0));
        movieSession1At16.setMovie(fastAndFurious);
        movieSession1At16.setCinemaHall(riverMall);
        movieSessionService.add(movieSession1At16);

        MovieSession movieSession1At18 = new MovieSession();
        movieSession1At18.setShowTime(
                LocalDateTime.of(2022, 7, 1, 18, 0));
        movieSession1At18.setMovie(fastAndFurious);
        movieSession1At18.setCinemaHall(riverMall);
        movieSessionService.add(movieSession1At18);

        MovieSession movieSession2At15 = new MovieSession();
        movieSession2At15.setShowTime(
                LocalDateTime.of(2022, 7, 2, 15, 0));
        movieSession2At15.setMovie(fastAndFurious);
        movieSession2At15.setCinemaHall(riverMall);
        movieSessionService.add(movieSession2At15);

        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 7, 1)).forEach(System.out::println);
    }
}
