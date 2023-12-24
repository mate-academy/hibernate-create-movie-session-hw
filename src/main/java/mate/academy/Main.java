package mate.academy;

import java.time.LocalDate;
import java.util.List;
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
        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setName("Hall Red");
        cinemaHallService.add(cinemaHall);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setLocalDate(LocalDate.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        List<MovieSession> availableSessions =
                movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now());
        System.out.println("------------");
        availableSessions.forEach(System.out::println);
        System.out.println("------------");
        System.out.println(movieSessionService.get(movieSession.getId()));
        System.out.println("------------");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("------------");
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
    }
}
