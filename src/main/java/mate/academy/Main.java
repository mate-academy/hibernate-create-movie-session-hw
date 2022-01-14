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
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall red = cinemaHallService.add(
                new CinemaHall(15, "small cinema-hall for best impressions"));
        CinemaHall green = cinemaHallService.add(
                new CinemaHall(50, "new cinema-hall with best equpment"));
        CinemaHall white = cinemaHallService.add(
                new CinemaHall(100, "biggest cinema-hall for total immersion"));
        cinemaHallService.getAll()
                         .forEach(System.out::println);
        System.out.println(cinemaHallService.get(1L));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession movieSession1 = movieSessionService.add(new MovieSession(
                fastAndFurious, red, LocalDateTime.of(2022, 1, 15, 10, 00)));
        MovieSession movieSession2 = movieSessionService.add(new MovieSession(
                fastAndFurious, green, LocalDateTime.of(2022, 1, 15, 12, 30)));
        MovieSession movieSession3 = movieSessionService.add(new MovieSession(
                fastAndFurious, white, LocalDateTime.of(2022, 1, 16, 20, 00)));
        System.out.println(movieSessionService.get(1L));

        movieSessionService.findAvailableSessions(1L, LocalDate.of(2022, 1, 15))
                           .forEach(System.out::println);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll()
                    .forEach(System.out::println);
    }
}
