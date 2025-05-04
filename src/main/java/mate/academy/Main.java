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
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall bigHall = new CinemaHall();
        bigHall.setCapacity(100);
        bigHall.setDescription("Big green hall");
        CinemaHallService hallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        hallService.add(bigHall);
        System.out.println(hallService.get(bigHall.getId()));
        hallService.getAll().forEach(System.out::println);
        MovieSession firstSession = new MovieSession();
        firstSession.setMovie(movieService.get(fastAndFurious.getId()));
        firstSession.setCinemaHall(hallService.get(bigHall.getId()));
        firstSession.setShowTime(LocalDateTime.of(2023, 8, 10, 8,50));
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstSession);
        System.out.println(movieSessionService.get(firstSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2023, 8, 10)).forEach(System.out::println);
    }
}
