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

        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        System.out.println(movieSessionService.findAvailableSessions(1L,
                LocalDate.parse("2021-12-12")));

        MovieSession movieSession = new MovieSession();
        movieSession.setShowTime(LocalDateTime.of(2021, 12, 12,
                14, 0));
        movieSession.setCinemaHall(cinemaHallService.get(1L));
        movieSession.setMovie(movieService.get(1L));
        movieSessionService.add(movieSession);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setShowTime(LocalDateTime.of(2021, 12, 12,
                16, 0));
        movieSession1.setCinemaHall(cinemaHallService.get(1L));
        movieSession1.setMovie(movieService.get(1L));
        movieSessionService.add(movieSession1);

        CinemaHall multiplex = new CinemaHall();
        multiplex.setDescription("Multiplex");
        multiplex.setCapacity(250);

        CinemaHall planetaKino = new CinemaHall();
        planetaKino.setDescription("Planeta Kino");
        planetaKino.setCapacity(750);

        cinemaHallService.add(multiplex);
        cinemaHallService.add(planetaKino);
        System.out.println(cinemaHallService.get(1L));
        System.out.println(cinemaHallService.getAll());

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
    }
}
