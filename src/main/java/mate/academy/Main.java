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

        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        CinemaHall cinemaHall = new CinemaHall(120, "3D");
        cinemaHallService.add(cinemaHall);
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println(movieService.get(1L));

        CinemaHall cinemaHall2 = new CinemaHall(100, "2D");
        cinemaHallService.add(cinemaHall2);
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println(cinemaHallService.get(1L));

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        movieSessionService.add(movieSession);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setCinemaHall(cinemaHall2);
        movieSession2.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession2);
        movieSessionService.get(movieSession2.getId());

        movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);

    }
}
