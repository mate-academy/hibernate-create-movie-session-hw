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
    private static final int RED_HALL_CAPACITY = 60;
    private static final int BLUE_HALL_CAPACITY = 120;
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        // MovieService
        MovieService movieService = (MovieService)
                injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie hobbit = new Movie("Hobbit");
        hobbit.setDescription("An adventure and fantastic film about kingdoms of middle-east.");
        movieService.add(hobbit);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        // CinemaHallService
        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(RED_HALL_CAPACITY);
        redHall.setDescription("IMAX 2D");
        cinemaHallService.add(redHall);
        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(BLUE_HALL_CAPACITY);
        blueHall.setDescription("IMAX 3D");
        cinemaHallService.add(blueHall);
        System.out.println(cinemaHallService.get(redHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        // MovieSessionService
        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(redHall);
        fastAndFuriousSession.setShowTime(LocalDateTime.now().plusDays(1));
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSession);
        MovieSession hobbitSession = new MovieSession();
        hobbitSession.setMovie(hobbit);
        hobbitSession.setCinemaHall(blueHall);
        hobbitSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(hobbitSession);
        System.out.println(movieSessionService.get(fastAndFuriousSession.getId()));
        movieSessionService.findAvailableSessions(hobbit.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
