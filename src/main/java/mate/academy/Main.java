package mate.academy;

import java.time.LocalDate;
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
        Injector injector = Injector.getInstance("mate.academy");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHall oldHall = new CinemaHall();
        oldHall.setCapacity(100);
        oldHall.setDescription("Old central hall");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(oldHall);
        CinemaHall cinemaHallFromDb = cinemaHallService.get(1L);

        MovieSession movieSession = new MovieSession();
        Movie movieFromDb = movieService.get(fastAndFurious.getId());
        movieSession.setMovie(movieFromDb);
        movieSession.setCinemaHall(cinemaHallFromDb);
        movieSession.setShowTime(LocalDateTime.of(2026,4,7,18,0));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(1L));

        List<MovieSession> availableSessions =
                movieSessionService.findAvailableSessions(1L, LocalDate.of(2026, 4, 7));
        System.out.println("Session of movie " + availableSessions);

    }
}
