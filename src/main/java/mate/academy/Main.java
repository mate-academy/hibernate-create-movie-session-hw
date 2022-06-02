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
    private static final MovieService MOVIE_SERVICE = (MovieService)
            injector.getInstance(MovieService.class);
    private static final CinemaHallService CINEMA_HALL_SERVICE = (CinemaHallService)
            injector.getInstance(CinemaHallService.class);

    private static final MovieSessionService MOVIE_SESSION_SERVICE = (MovieSessionService)
            injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MOVIE_SERVICE.add(fastAndFurious);
        System.out.println(MOVIE_SERVICE.get(fastAndFurious.getId()));
        MOVIE_SERVICE.getAll().forEach(System.out::println);

        CinemaHall newBuilding = new CinemaHall();
        newBuilding.setCapacity(7);
        newBuilding.setDescription("Awesome cinema hall, new building");
        CINEMA_HALL_SERVICE.add(newBuilding);
        System.out.println(CINEMA_HALL_SERVICE.get(newBuilding.getId()));
        CINEMA_HALL_SERVICE.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(newBuilding);
        movieSession.setShowTime(LocalDateTime.now());
        MOVIE_SESSION_SERVICE.add(movieSession);
        System.out.println(MOVIE_SESSION_SERVICE.get(movieSession.getId()));
        MOVIE_SESSION_SERVICE.findAvailableSessions(fastAndFurious.getId(), LocalDate.now());
    }
}
