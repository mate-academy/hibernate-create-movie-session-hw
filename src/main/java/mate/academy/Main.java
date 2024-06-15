package mate.academy;

import java.time.LocalDate;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println();

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall(200);
        cinemaHall.setDescription("Small cinemaHall");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println();

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        LocalDate firstSession =
                LocalDate.of(2024,3, 10);
        LocalDate secondSession =
                LocalDate.of(2024,3, 11);
        MovieSession movieSessionFirst =
                new MovieSession(firstSession, fastAndFurious, cinemaHall);
        MovieSession movieSessionSecond =
                new MovieSession(secondSession, fastAndFurious, cinemaHall);
        movieSessionService.add(movieSessionFirst);
        movieSessionService.add(movieSessionSecond);
        System.out.println(movieSessionService.get(movieSessionFirst.getId()));
        System.out.println(movieSessionService.get(movieSessionSecond.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), firstSession));
    }
}
