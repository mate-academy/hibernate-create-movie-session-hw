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
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall awesomeCinemaHall = new CinemaHall();
        awesomeCinemaHall.setCapacity(120);
        awesomeCinemaHall.setDescription("Awesome cinema hall!");
        cinemaHallService.add(awesomeCinemaHall);
        System.out.println(cinemaHallService.get(awesomeCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        MovieSession todayMovieSession = new MovieSession();
        todayMovieSession.setMovie(fastAndFurious);
        todayMovieSession.setCinemaHall(awesomeCinemaHall);
        todayMovieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(todayMovieSession);
        System.out.println(movieSessionService.get(todayMovieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
