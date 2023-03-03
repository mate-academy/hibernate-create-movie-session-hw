package mate.academy;

import java.time.LocalDateTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static void main(String[] args) {
        Injector instance = Injector.getInstance("mate.academy");
        MovieService movieService = (MovieService) instance.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        CinemaHall cinemaHall = new CinemaHall(30, "4DX");
        CinemaHallService cinemaHallService
                = (CinemaHallService) instance.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        MovieSessionService movieSessionService
                = (MovieSessionService) instance.getInstance(MovieSessionService.class);
        MovieSession movieSession
                = new MovieSession(fastAndFurious, cinemaHall, LocalDateTime.now());
        movieSessionService.add(movieSession);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(
                fastAndFurious.getId(),
                movieSession.getShowTime().toLocalDate());
        availableSessions.forEach(System.out::println);
        movieService.getAll().forEach(System.out::println);
    }
}
