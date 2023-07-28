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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("Main");
        cinemaHall.setDescription("Our biggest cinema hall");
        cinemaHall.setCapacity(250);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieService.get(fastAndFurious.getId()));
        movieSession.setShowTime(LocalDateTime.of(2022, 07, 19, 19, 30, 00));
        movieSession.setCinemaHall(cinemaHallService.get(cinemaHall.getId()));

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(movieService.get(fastAndFurious.getId()));
        secondMovieSession.setShowTime(LocalDateTime.of(2022, 07, 21, 10, 00, 00));
        secondMovieSession.setCinemaHall(cinemaHallService.get(cinemaHall.getId()));

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.add(secondMovieSession);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 07, 21)).forEach(System.out::println);
        System.out.println(movieSessionService.get(movieSession.getId()));
    }
}
