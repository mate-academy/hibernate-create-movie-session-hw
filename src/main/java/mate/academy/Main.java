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
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("_____________________________");

        CinemaHall hall1 = new CinemaHall();
        hall1.setCapacity(20);
        hall1.setDescription("lux");

        CinemaHall hall2 = new CinemaHall();
        hall2.setCapacity(40);
        hall2.setDescription("ordinary");

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(hall1);
        cinemaHallService.add(hall2);
        System.out.println(cinemaHallService.get(hall1.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("_____________________________");

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setCinemaHall(hall2);
        movieSession2.setShowTime(LocalDateTime.now().plusHours(2));

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(hall1);
        movieSession.setShowTime(LocalDateTime.now());

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));

        System.out.println("_____________________________");

        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
