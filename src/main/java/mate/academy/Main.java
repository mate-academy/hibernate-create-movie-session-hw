package mate.academy;

import java.time.LocalDateTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.GenericService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(
                        MovieSessionService.class);
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        Movie fastAndFurious = getMovieTested(movieService);
        CinemaHall firstHall = getCinemaHallTested(cinemaHallService);
        LocalDateTime nowTime = LocalDateTime.now();
        MovieSession movieSession = new MovieSession(
                fastAndFurious, firstHall, nowTime
        );
        movieSessionService.add(movieSession);
        movieSession = movieSessionService.get(movieSession.getId());
        System.out.println(movieSession + " added");
        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), nowTime.toLocalDate()
        );
        assert availableSessions.contains(movieSession);
        printLists(movieService, cinemaHallService, movieSessionService);
    }

    private static Movie getMovieTested(MovieService service) {
        Movie movie = new Movie("Fast and Furious");
        movie.setDescription("An action film about street racing, heists, and spies.");
        service.add(movie);
        movie = service.get(movie.getId());
        System.out.println(movie + " added");
        return movie;
    }
    private static CinemaHall getCinemaHallTested(CinemaHallService service) {
        CinemaHall hall = new CinemaHall(100, "Hall #1");
        service.add(hall);
        hall = service.get(hall.getId());
        System.out.println(hall + " added");
        return hall;
    }

    private static void printLists(MovieService movieService,
                                   CinemaHallService cinemaHallService,
                                   MovieSessionService movieSessionService) {
        System.out.println("Lists of entities:\nMovies:");
        movieService.getAll().forEach(System.out::println);
        System.out.println("Cinema halls:");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("Movie sessions");
        movieSessionService.getAll().forEach(System.out::println);
    }

}
