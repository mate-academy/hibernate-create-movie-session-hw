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
    private static MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie martian = new Movie("martian");
        martian.setDescription("Science-fantasy film");
        movieService.add(martian);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("Awesome hall!");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(1L));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        LocalDateTime time = LocalDateTime.now();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(time);
        movieSessionService.add(movieSession);

        LocalDateTime timeYesterday = LocalDateTime.of(2023, 4,10,12,20);
        MovieSession anotherMovieSession = new MovieSession();
        anotherMovieSession.setMovie(martian);
        anotherMovieSession.setCinemaHall(cinemaHall);
        anotherMovieSession.setShowTime(timeYesterday);
        movieSessionService.add(anotherMovieSession);
        System.out.println(movieSessionService.get(1L));

        LocalDate localDate = LocalDate.of(2023,4,11);
        System.out.println(movieSessionService.findAvailableSessions(1L, localDate));
    }
}
