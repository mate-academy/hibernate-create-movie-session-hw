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

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinema3D = new CinemaHall();
        cinema3D.setCapacity(60);
        cinema3D.setDescription("Big and comfortable");
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinema3D);
        System.out.println(cinemaHallService.get(cinema3D.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession session = new MovieSession();
        session.setMovie(fastAndFurious);
        session.setCinemaHall(cinema3D);
        session.setShowTime(LocalDateTime.of(2022, 9, 1, 18, 20));
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(session);
        System.out.println(movieSessionService.get(session.getId()));
        movieSessionService.findAvailableSessions(session.getId(),
                LocalDate.of(2022, 9, 1)).forEach(System.out::println);
        movieSessionService.findAvailableSessions(session.getId(),
                LocalDate.of(2022, 10, 1)).forEach(System.out::println);
    }
}
