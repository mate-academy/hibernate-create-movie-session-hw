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
        Movie jamesBond = new Movie("Agent 007");
        jamesBond.setDescription("An action film about spies.");
        movieService.add(jamesBond);
        System.out.println(movieService.get(jamesBond.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall blockbuster = new CinemaHall();
        blockbuster.setDescription("High level hall with Imax, 3D and 2D technologies");
        blockbuster.setCapacity(4000);
        cinemaHallService.add(blockbuster);
        CinemaHall oscar = new CinemaHall();
        oscar.setDescription("Cosy family hall with comfortable chairs");
        oscar.setCapacity(250);
        cinemaHallService.add(oscar);
        CinemaHall multiplex = new CinemaHall();
        multiplex.setDescription("Modern hall with a large selection of popcorn");
        multiplex.setCapacity(1500);
        cinemaHallService.add(multiplex);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(multiplex);
        movieSessionService.findAvailableSessions(1L, LocalDate.of(2021, 11, 1))
                .forEach(System.out::println);
        movieSession.setLocalDateTime(LocalDateTime.of(2021,11,1,10,00));

        System.out.println(cinemaHallService.get(1L));
        cinemaHallService.getAll().forEach(System.out::println);
    }
}
