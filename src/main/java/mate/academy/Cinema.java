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

public class Cinema {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie hulk = new Movie();
        hulk.setTitle("Hulk");
        hulk.setDescription("Mighty Avenger");
        movieService.add(hulk);

        movieService.getAll().forEach(System.out::println);

        CinemaHall red = new CinemaHall();
        red.setCapacity(15);
        red.setDescription("Red hall");
        CinemaHall blue = new CinemaHall();
        blue.setCapacity(25);
        blue.setDescription("Blue Hall");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(red);
        cinemaHallService.add(blue);
        cinemaHallService.get(red.getId());
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession premiere = new MovieSession();
        premiere.setMovie(fastAndFurious);
        premiere.setCinemaHall(blue);
        premiere.setShowTime(LocalDateTime.now());

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(hulk);
        movieSession.setCinemaHall(red);
        movieSession.setShowTime(LocalDateTime.now());

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(premiere);
        movieSessionService.add(movieSession);
        movieSessionService.get(premiere.getId());
        movieSessionService.findAvailableSessions(hulk.getId(),
                LocalDate.of(2021, 12, 28)).forEach(System.out::println);
    }
}
