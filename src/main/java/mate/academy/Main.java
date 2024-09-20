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

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        //cascades
        Movie fastAndStupid = new Movie("Fast and Stupid");
        fastAndStupid.setDescription("An action comedy movie about romantic relationships");
        Movie javaMovie = new Movie("MyJavaJourney");
        javaMovie.setDescription("A thriller about setting out on a career in java");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall extra = new CinemaHall(11,"11");
        cinemaHallService.add(extra);
        //cascades

        System.out.println("------cinemaHallService:get1L: " + cinemaHallService.get(1L));
        System.out.println("-------cinemaHallService:getAll(BEFORE CASCADES): "
                + cinemaHallService.getAll());

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(javaMovie);
        CinemaHall cinemaHall1 = new CinemaHall(100, "big");
        movieSession1.setCinemaHall(cinemaHall1);
        movieSession1.setShowTime(LocalDateTime.of(2024,9,19,20,25));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(fastAndStupid);
        CinemaHall cinemaHall2 = new CinemaHall(20, "small");
        movieSession2.setCinemaHall(cinemaHall2);
        movieSession2.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession2);

        System.out.println("-------cinemaHallService:getAll(AFTER CASCADES): "
                + cinemaHallService.getAll());
        System.out.println("--------movieSerivce:get1L: " + movieService.get(1L));
        System.out.println("--------movieSerivce:get2L: " + movieService.get(2L));
        System.out.println("-------Today sessions: "
                + movieSessionService.findAvailableSessions(2L, LocalDate.now()));
        System.out.println("-------Today sessions: "
                + movieSessionService.findAvailableSessions(3L, LocalDate.now()));
        System.out.println("-------Today sessions: "
                + movieSessionService.findAvailableSessions(1L, LocalDate.now()));
        System.out.println("-------movieService:getALL: " + movieService.getAll());
    }
}
