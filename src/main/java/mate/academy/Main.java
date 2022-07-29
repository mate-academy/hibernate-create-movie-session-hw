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
    private static MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        //        movieService.getAll().forEach(System.out::println);
        //        cinemaHallService.getAll().forEach(System.out::println);
        movieSessionService.findAvailableSessions(1L, LocalDate.now()).forEach(System.out::println);
    }


    public static void injectMovie() {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription(
                "An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie terminator = new Movie("Terminator 5");
        fastAndFurious.setDescription("hasta la vista baby");
        movieService.add(terminator);
    }

    public static void injectCinemaHall() {
        CinemaHall lavina = new CinemaHall(500, "Lavina Mall");
        CinemaHall multiplex = new CinemaHall(2000, "multiplex");
        cinemaHallService.add(lavina);
        cinemaHallService.add(multiplex);
    }

    public static void injectMovieSessions() {
                Movie firstMovie = movieService.get(1L);
                Movie secondMovie = movieService.get(2L);
                CinemaHall cinemaHallFromDB = cinemaHallService.get(1L);
                CinemaHall cinemaHallFromDB2 = cinemaHallService.get(2L);
                MovieSession terminatorMorning =
                        new MovieSession(LocalDateTime.now(), firstMovie, cinemaHallFromDB);
                MovieSession terminatorDay =
                        new MovieSession(LocalDateTime.now()
                        .plusHours(3), firstMovie, cinemaHallFromDB);
                MovieSession terminatorMorning2 =
                        new MovieSession(LocalDateTime.now(), firstMovie, cinemaHallFromDB2);
                movieSessionService.add(terminatorMorning);
                movieSessionService.add(terminatorMorning2);
                movieSessionService.add(terminatorDay);
    }
}
