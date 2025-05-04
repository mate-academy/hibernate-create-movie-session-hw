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
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        Movie godfather = new Movie("The GodFather");
        godfather.setDescription("A cult gangster drama of 1972");
        movieService.add(godfather);
        System.out.println(movieService.get(godfather.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHallNumber1 = new CinemaHall();
        cinemaHallNumber1.setCapacity(12);
        cinemaHallNumber1.setDescription("Hall 1");
        cinemaHallService.add(cinemaHallNumber1);
        System.out.println(cinemaHallService.get(cinemaHallNumber1.getId()));

        CinemaHall cinemaHallNumber2 = new CinemaHall();
        cinemaHallNumber2.setCapacity(22);
        cinemaHallNumber2.setDescription("Hall 2");
        cinemaHallService.add(cinemaHallNumber2);
        System.out.println(cinemaHallService.getAll());

        MovieSession movieSessionFastAndFurious = new MovieSession();
        movieSessionFastAndFurious.setMovie(fastAndFurious);
        movieSessionFastAndFurious.setShowTime(LocalDateTime.now());
        movieSessionFastAndFurious.setCinemaHall(cinemaHallNumber1);
        movieSessionService.add(movieSessionFastAndFurious);
        movieSessionService.get(movieSessionFastAndFurious.getId());

        MovieSession movieSessionGodFather = new MovieSession();
        movieSessionGodFather.setMovie(godfather);
        movieSessionGodFather.setShowTime(LocalDateTime.of(2022, 7, 29, 16, 40));
        movieSessionGodFather.setCinemaHall(cinemaHallNumber2);
        movieSessionService.add(movieSessionGodFather);
        System.out.println(movieSessionService.findAvailableSessions(movieSessionGodFather
                .getId(), LocalDate.now()));
        System.out.println(movieSessionService.findAvailableSessions(movieSessionFastAndFurious
                .getId(), LocalDate.now()));
    }
}
