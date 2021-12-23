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
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie buried = new Movie("Fast and Furious");
        fastAndFurious.setDescription(
                "After an attack by a group of Iraqis he wakes to find "
                + "he is buried alive inside a coffin. "
                + "With only a lighter and a cell phone it's a race against "
                + "time to escape this claustrophobic death trap."
        );
        movieService.add(fastAndFurious);
        movieService.add(buried);
        CinemaHall cinemaHall = new CinemaHall(25, "*cinema hall description*");
        cinemaHallService.add(cinemaHall);
        MovieSession fastAndFuriousMovieSession = new MovieSession(
                fastAndFurious, cinemaHall,
                LocalDateTime.of(2021, 8, 8,12, 30));
        MovieSession buriedMovieSession = new MovieSession(
                buried, cinemaHall,
                LocalDateTime.of(2021, 8, 21,22, 0));
        movieSessionService.add(fastAndFuriousMovieSession);
        movieSessionService.add(buriedMovieSession);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        System.out.println(movieService.get(buried.getId()));
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        movieSessionService.finedAvailableSessions(
                fastAndFurious.getId(), LocalDate.of(2021, 8, 21))
                .forEach(System.out::println);
    }
}
