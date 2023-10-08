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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious 2");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies 2.");
        fastAndFurious = movieService.add(fastAndFurious);

        CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR
                .getInstance(CinemaHallService.class);
        CinemaHall modernHall = new CinemaHall();
        modernHall.setCapacity(10);
        modernHall.setDescription("Really modern hall");
        modernHall = cinemaHallService.add(modernHall);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(modernHall);
        movieSession.setShowTime(LocalDateTime.now());

        MovieSessionService movieSessionService = (MovieSessionService) INJECTOR
                .getInstance(MovieSessionService.class);
        movieSession = movieSessionService.add(movieSession);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
