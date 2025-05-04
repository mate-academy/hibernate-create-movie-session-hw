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
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie tokyoDrift = new Movie("Tokyo drift");
        tokyoDrift.setDescription("Third part of Fast and Furious about racing in Tokyo");
        movieService.add(tokyoDrift);

        System.out.println(movieService.get(tokyoDrift.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall multiplex = new CinemaHall();
        multiplex.setDescription("Multiplex cinema hall");
        multiplex.setCapacity(100);
        cinemaHallService.add(multiplex);

        CinemaHall moviePlanet = new CinemaHall();
        moviePlanet.setDescription("Movie planet");
        moviePlanet.setCapacity(200);
        cinemaHallService.add(moviePlanet);
        cinemaHallService.getAll().forEach(System.out::println);

        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(multiplex);
        movieSession1.setShowTime(LocalDateTime.of(2024, 5, 3, 16, 0));
        movieSessionService.add(movieSession1);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(tokyoDrift);
        movieSession2.setCinemaHall(multiplex);
        movieSession2.setShowTime(LocalDateTime.of(2024, 5, 3, 10, 30));
        movieSessionService.add(movieSession2);

        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(tokyoDrift);
        movieSession3.setCinemaHall(moviePlanet);
        movieSession3.setShowTime(LocalDateTime.of(2024, 5, 6, 17, 30));
        movieSessionService.add(movieSession3);

        System.out.println(movieSessionService.findAvailableSessions(
                tokyoDrift.getId(), LocalDate.of(2024, 5, 3)));
    }
}
