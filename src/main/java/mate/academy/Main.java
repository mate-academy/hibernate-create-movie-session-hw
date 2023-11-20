package mate.academy;

import java.time.LocalDate;
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

        MovieService movieService =
                (MovieService) INJECTOR.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        Movie m = new Movie("m");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        movieService.add(m);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("getAll method");
        movieService.getAll().forEach(System.out::println);

        CinemaHall hall = new CinemaHall();
        hall.setCapacity(100);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("Best cinema hall");
        cinemaHall.setCapacity(500);
        CinemaHallService cinemaHallService =
                (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        cinemaHallService.add(hall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()) + "get method");
        System.out.println("getAll method");
        cinemaHallService.getAll().forEach(System.out::println);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(hall);
        movieSession.setShowTime(LocalDate.of(2023, 11, 19));
        MovieSessionService movieSessionService =
                (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()) + "get method");
        System.out.println("find method");
        movieSessionService.findAvailableSessions(1L,
                LocalDate.of(2023, 11, 19)).forEach(System.out::println);
    }
}
