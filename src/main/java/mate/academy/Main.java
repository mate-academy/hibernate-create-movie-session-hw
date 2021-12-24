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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie terminator88 = new Movie("Terminator 88");
        terminator88.setDescription("An action film about finding meaning in AI's life.");
        movieService.add(terminator88);
        System.out.println(movieService.get(terminator88.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(200);
        cinemaHall.setDescription("Cinema hall");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        CinemaHall cinemaCity = new CinemaHall();
        cinemaCity.setCapacity(400);
        cinemaCity.setDescription("Cinema City");
        CinemaHallService cinemaCityService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaCity);
        System.out.println(cinemaCityService.get(cinemaCity.getId()));
        cinemaCityService.getAll().forEach(System.out::println);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.get(movieSession.getId());
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
        MovieSession movieSmartSession = new MovieSession();
        movieSmartSession.setMovie(terminator88);
        movieSmartSession.setCinemaHall(cinemaCity);
        movieSmartSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionSmartService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionSmartService.add(movieSmartSession);
        movieSessionSmartService.get(movieSmartSession.getId());
        movieSessionService.findAvailableSessions(terminator88.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
