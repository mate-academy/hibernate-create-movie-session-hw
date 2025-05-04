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
    private static MovieService movieService = (MovieService)
            injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService = (CinemaHallService)
            injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService = (MovieSessionService)
            injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie topGun = new Movie("Top Gun");
        topGun.setDescription("Plane flying and big love happening");
        movieService.add(topGun);

        System.out.println();
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(20);
        cinemaHall.setDescription("Red with old chairs");
        cinemaHallService.add(cinemaHall);
        CinemaHall blueCinemaHall = new CinemaHall();
        blueCinemaHall.setCapacity(50);
        blueCinemaHall.setDescription("Same like a red hall, but more chairs");
        cinemaHallService.add(blueCinemaHall);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession);
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(topGun);
        movieSession2.setShowTime(LocalDateTime.now().minusHours(2));
        movieSession2.setCinemaHall(blueCinemaHall);
        movieSessionService.add(movieSession2);

        System.out.println();
        System.out.println("TEST QUERY");
        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.now()));
    }
}
