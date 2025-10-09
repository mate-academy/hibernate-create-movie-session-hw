package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        // MovieService
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("Movie added: " + movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        // CinemaHallService
        CinemaHallService cinemaHallService = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall hall = new CinemaHall();
        hall.setCapacity(150);
        hall.setDescription("Hall with comfortable seats");
        cinemaHallService.add(hall);
        System.out.println("CinemaHall added: " + cinemaHallService.get(hall.getId()));

        // MovieSessionService
        MovieSessionService movieSessionService = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession session = new MovieSession();
        session.setMovie(fastAndFurious);
        session.setCinemaHall(hall);
        session.setShowTime(LocalDateTime.of(2025, 10, 7, 18, 30));
        movieSessionService.add(session);
        System.out.println("MovieSession added: " + movieSessionService.get(session.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.of(2025, 10, 7))
                .forEach(System.out::println);

    }
}
