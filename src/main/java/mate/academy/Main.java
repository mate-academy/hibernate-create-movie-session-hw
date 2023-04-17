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

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall redHall = new CinemaHall(50, "Cozy red cinema hall.");
        cinemaHallService.add(redHall);
        System.out.println(cinemaHallService.get(redHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        MovieSession firstMorningSession = new MovieSession();
        firstMorningSession.setCinemaHall(redHall);
        firstMorningSession.setMovie(fastAndFurious);
        firstMorningSession.setShowTime(LocalDateTime.of(2023, 4, 13, 9, 00));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(firstMorningSession);
        System.out.println(movieSessionService.get(firstMorningSession.getId()));
        movieSessionService.findAvailableSessions(redHall.getId(),
                LocalDate.of(2023, 4, 13)).forEach(System.out::println);
    }
}
