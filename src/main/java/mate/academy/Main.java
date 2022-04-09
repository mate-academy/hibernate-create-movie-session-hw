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

    public static void main(String[] args) {
        final MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie harryPotter = new Movie("Harry potter");
        harryPotter.setDescription("Film about magic world");
        movieService.add(harryPotter);

        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setCapacity(400);
        cinemaHall1.setDescription("Big hall");
        cinemaHallService.add(cinemaHall1);

        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall2.setCapacity(200);
        cinemaHall2.setDescription("Small hall");
        cinemaHallService.add(cinemaHall2);

        MovieSession movieSession18 = new MovieSession();
        movieSession18.setMovie(harryPotter);
        movieSession18.setCinemaHall(cinemaHall1);
        movieSession18.setShowTime(LocalDateTime.of(2022, 4, 9, 18, 00));
        movieSessionService.add(movieSession18);

        MovieSession movieSession20 = new MovieSession();
        movieSession20.setMovie(harryPotter);
        movieSession20.setCinemaHall(cinemaHall1);
        movieSession20.setShowTime(LocalDateTime.of(2022, 4, 9, 19, 00));
        movieSessionService.add(movieSession20);

        System.out.println("1 film check");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("All films check");
        movieService.getAll().forEach(System.out::println);
        System.out.println("1 hall check");
        System.out.println(cinemaHallService.get(cinemaHall1.getId()));
        System.out.println("All halls check");
        System.out.println(cinemaHallService.getAll());
        System.out.println("1 session check");
        System.out.println(movieSessionService.get(movieSession18.getId()));
        System.out.println("All sessions of Harry Potter on 09.04.2022");
        System.out.println(movieSessionService.findAvailableSessions(2L, LocalDate.of(2022, 4, 9)));
    }
}
