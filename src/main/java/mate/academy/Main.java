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
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie terminator = new Movie("Terminator");
        terminator.setDescription("Action.");
        movieService.add(terminator);
        System.out.println(movieService.get(terminator.getId()));

        Movie homeAlone = new Movie("Home Alone");
        homeAlone.setDescription("Comedy.");
        movieService.add(homeAlone);
        System.out.println(movieService.get(homeAlone.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("iMAX");
        cinemaHall.setCapacity(50);
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession terminatorMovieSession = new MovieSession();
        terminatorMovieSession.setMovie(terminator);
        terminatorMovieSession.setShowTime(LocalDateTime.now());
        terminatorMovieSession.setCinemaHall(cinemaHall);
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(terminatorMovieSession);
        System.out.println(movieSessionService.get(terminatorMovieSession.getId()));

        MovieSession homeAloneMovieSession = new MovieSession();
        homeAloneMovieSession.setMovie(homeAlone);
        homeAloneMovieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(homeAloneMovieSession);
        System.out.println(movieSessionService.get(homeAloneMovieSession.getId()));
        movieSessionService.findAvailableSessions(homeAloneMovieSession.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
