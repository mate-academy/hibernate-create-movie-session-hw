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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall cinemax = new CinemaHall();
        cinemax.setCapacity(200);
        cinemax.setDescription("Little red hall");
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemax);
        System.out.println(cinemaHallService.get(cinemax.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        MovieSession daySession = new MovieSession();
        daySession.setCinemaHall(cinemax);
        daySession.setMovie(fastAndFurious);
        daySession.setShowTime(LocalDateTime
                .of(2022, 04, 10, 14, 00));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(daySession);
        System.out.println(movieSessionService.get(daySession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 04, 10))
                .forEach(System.out::println);
    }
}
