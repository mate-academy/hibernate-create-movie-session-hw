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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie dune = new Movie("Dune");
        dune.setDescription("An action movie about giant worms");
        movieService.add(dune);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService
                = (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
        CinemaHall standardHall = new CinemaHall("Standard cinema hall");
        standardHall.setCapacity(300);
        cinemaHallService.add(standardHall);
        CinemaHall vipHall = new CinemaHall("VIP cinema hall");
        vipHall.setCapacity(20);
        cinemaHallService.add(vipHall);
        System.out.println(cinemaHallService.get(standardHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService
                = (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);
        MovieSession firstMovieSession
                = new MovieSession(fastAndFurious, standardHall, LocalDateTime.now());
        MovieSession secondMovieSession
                = new MovieSession(fastAndFurious, vipHall, LocalDateTime.now().plusHours(3));
        MovieSession thirdMovieSession
                = new MovieSession(dune, standardHall, LocalDateTime.now().minusHours(2));
        MovieSession forthMovieSession
                = new MovieSession(dune, vipHall, LocalDateTime.now().plusDays(1));
        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);
        movieSessionService.add(thirdMovieSession);
        movieSessionService.add(forthMovieSession);
        System.out.println(movieSessionService.get(secondMovieSession.getId()));
        movieSessionService.findAvailableSessions(1L, LocalDate.from(LocalDateTime.now()))
                .forEach(System.out::println);
        movieSessionService.findAvailableSessions(2L, LocalDate.from(LocalDateTime.now()))
                .forEach(System.out::println);
    }
}
