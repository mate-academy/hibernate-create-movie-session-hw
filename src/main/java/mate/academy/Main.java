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
    private static final Injector instance = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(5);
        cinemaHall.setDescription("The Cinema Hall");

        CinemaHallService cinemaHallService = (
                CinemaHallService) instance.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        cinemaHallService.get(1L);
        cinemaHallService.getAll();

        MovieService movieService = (
                MovieService) instance.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        movieService.add(fastAndFurious);
        movieService.get(1L);
        movieService.getAll();

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setMovie(fastAndFurious);
        movieSession.setShowTime(LocalDateTime.now());

        MovieSessionService movieSessionService = (
                MovieSessionService) instance.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.get(1L);
        movieSessionService.findAvailableSessions(1L, LocalDate.now());

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
    }
}
