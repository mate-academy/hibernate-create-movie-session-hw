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
    private static final Injector instance = Injector.getInstance("mate.academy");
    private static final MovieSessionService movieSessionService = (MovieSessionService)
            instance.getInstance(MovieSessionService.class);
    private static final MovieService movieService = (MovieService)
            instance.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService)
            instance.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("--------------------------------------------------------------------");

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("Description");
        cinemaHall.setCapacity(150);
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("--------------------------------------------------------------------");

        MovieSession movieSession = new MovieSession();
        movieSession.setShowTime(LocalDateTime.of(2022, 9, 29, 14, 50));
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 9, 29)));
    }
}
