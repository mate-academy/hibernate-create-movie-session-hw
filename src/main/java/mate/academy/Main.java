package mate.academy;

import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final LocalDateTime DECEMBER_31 =
            LocalDateTime.of(2023,12,31,15,30);
    private static final LocalDateTime JANUARY_10 =
            LocalDateTime.of(2024, 1, 10, 11, 45);

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall standardHall = new CinemaHall();
        standardHall.setCapacity(50);
        standardHall.setDescription("Standard hall for non premiere films");
        cinemaHallService.add(standardHall);
        System.out.println(cinemaHallService.get(standardHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession last2023Session = new MovieSession();
        last2023Session.setMovie(fastAndFurious);
        last2023Session.setCinemaHall(standardHall);
        last2023Session.setShowTime(DECEMBER_31);
        MovieSession january10Session = new MovieSession();
        january10Session.setMovie(fastAndFurious);
        january10Session.setCinemaHall(standardHall);
        january10Session.setShowTime(JANUARY_10);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(last2023Session);
        movieSessionService.add(january10Session);

        System.out.println(movieSessionService.get(last2023Session.getId()));
        System.out.println(movieSessionService.get(january10Session.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), JANUARY_10.toLocalDate())
                .forEach(System.out::println);
    }
}
