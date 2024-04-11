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

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService
                = (CinemaHallService)injector.getInstance(CinemaHallService.class);
        CinemaHall red = new CinemaHall();
        red.setCapacity(100);
        red.setDescription("Big and comfortable");
        cinemaHallService.add(red);
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDateTime time = LocalDateTime.of(2024,04,15,15,35);
        MovieSession movieSession = new MovieSession();
        Movie movieFromDB = movieService.get(fastAndFurious.getId());
        CinemaHall cinemaHallFromDB = cinemaHallService.get(1L);
        movieSession.setMovie(movieFromDB);
        movieSession.setCinemaHall(cinemaHallFromDB);
        movieSession.setShowTime(time);
        MovieSessionService movieSessionService
                = (MovieSessionService)injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.findAvailableSessions(1L,
                LocalDate.of(2024,04,15)));

    }
}
