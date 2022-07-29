package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
//        Movie fastAndFurious = new Movie("Fast and Furious");
//        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
//        movieService.add(fastAndFurious);
//        System.out.println(movieService.get(fastAndFurious.getId()));
//
//        Movie terminator = new Movie("Terminator 5");
//        fastAndFurious.setDescription("hasta la vista baby");
//        movieService.add(terminator);

//        movieService.getAll().forEach(System.out::println);

//        CinemaHall lavina = new CinemaHall(500, "Lavina Mall");
//        CinemaHall multiplex = new CinemaHall(2000, "multiplex");
//        cinemaHallService.add(lavina);
//        cinemaHallService.add(multiplex);
//        cinemaHallService.getAll().forEach(System.out::println);
        Movie firstMovie = movieService.get(1L);
        Movie secondMovie = movieService.get(2L);
        CinemaHall cinemaHallFromDB = cinemaHallService.get(1L);
        CinemaHall cinemaHallFromDB2 = cinemaHallService.get(2L);
        MovieSession terminator_morning = new MovieSession(LocalDateTime.now(), firstMovie, cinemaHallFromDB);
        MovieSession terminator_day = new MovieSession(LocalDateTime.now().plusHours(3), firstMovie, cinemaHallFromDB);
        MovieSession terminator_morning2 = new MovieSession(LocalDateTime.now(), firstMovie, cinemaHallFromDB2);
        movieSessionService.add(terminator_morning);
        movieSessionService.add(terminator_morning2);
        movieSessionService.add(terminator_day);

        movieSessionService.findAvailableSessions(1L, LocalDate.now()).forEach(System.out::println);
    }
}
