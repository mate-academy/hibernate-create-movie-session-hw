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
        fastAndFurious = movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall firstHall = new CinemaHall();
        firstHall.setDescription("First hall");
        firstHall.setCapacity(50);
        firstHall = cinemaHallService.add(firstHall);
        System.out.println(movieService.get(firstHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(firstHall);
        movieSession.setShowTime(
                LocalDateTime.of(2022, 12, 6, 12, 30));
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSession = movieSessionService.add(movieSession);
        System.out.println(movieService.get(movieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 12, 6)).forEach(System.out::println);
    }
}
