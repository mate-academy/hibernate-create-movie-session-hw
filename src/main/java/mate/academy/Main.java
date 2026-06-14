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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        CinemaHall hall = new CinemaHall();
        hall.setCapacity(1);
        hall.setDescription("Cool disc");

        MovieSession session = new MovieSession();
        session.setMovie(fastAndFurious);
        session.setCinemaHall(hall);
        session.setShowTime(LocalDateTime.now());

        Injector injector = Injector.getInstance("mate.academy");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieService.add(fastAndFurious);
        cinemaHallService.add(hall);
        movieSessionService.add(session);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(cinemaHallService.get(hall.getId()));
        System.out.println(movieSessionService.get(session.getId()));

        cinemaHallService.getAll().forEach(System.out::println);
        movieService.getAll().forEach(System.out::println);

        movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.now()).forEach(System.out::println);
    }
}
