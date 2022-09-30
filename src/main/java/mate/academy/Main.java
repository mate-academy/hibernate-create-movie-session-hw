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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setCapacity(100);
        cinemaHall1.setDescription("BUTTERFLY KINOZAL");
        cinemaHallService.add(cinemaHall1);
        System.out.println(cinemaHallService.get(cinemaHall1.getId()));

        MovieSession fastFuriousSession = new MovieSession();
        fastFuriousSession.setCinemaHall(cinemaHall1);
        fastFuriousSession.setMovie(fastAndFurious);
        fastFuriousSession.setShowTime(LocalDateTime.of(2022, 1, 1, 18, 30));

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastFuriousSession);

        System.out.println(movieSessionService.get(fastFuriousSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 1, 1))
                .forEach(System.out::println);
    }
}
