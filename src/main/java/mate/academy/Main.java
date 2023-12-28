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
        MovieService movieService = (MovieService)
                injector.getInstance(MovieService.class);
        // save movie
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie barbie = new Movie("Barbie");
        barbie.setDescription("Barbie suffers a crisis that leads her "
                              + "to question her world and her existence.");
        movieService.add(barbie);
        //print movie
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        // save cinema hall
        CinemaHall threeD = new CinemaHall();
        threeD.setCapacity(100);
        threeD.setDescription("3D");
        cinemaHallService.add(threeD);
        CinemaHall imax = new CinemaHall();
        imax.setCapacity(120);
        imax.setDescription("IMAX");
        cinemaHallService.add(imax);
        // print cinema hall
        System.out.println(cinemaHallService.get(2L));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        // save movie session
        MovieSession movieSession1 = new MovieSession(fastAndFurious, imax,
                LocalDateTime.of(2023, 12, 28, 11, 45));
        movieSessionService.add(movieSession1);
        MovieSession movieSession2 = new MovieSession(barbie, threeD,
                LocalDateTime.of(2023, 12, 29, 13, 15));
        movieSessionService.add(movieSession2);
        MovieSession movieSession3 = new MovieSession(barbie, imax,
                LocalDateTime.of(2023, 12, 29, 17, 15));
        movieSessionService.add(movieSession3);
        // print movie session
        System.out.println(movieSessionService.get(1L));
        movieSessionService.findAvailableSessions(barbie.getId(),
                LocalDate.of(2023, 12, 29)).forEach(System.out::println);
    }
}
