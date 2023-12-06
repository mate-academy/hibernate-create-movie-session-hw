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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR
                .getInstance(CinemaHallService.class);
        CinemaHall cinemaHallYellow = new CinemaHall();
        cinemaHallYellow.setCapacity(100);
        cinemaHallYellow.setDescriptions("Yellow Hall");
        cinemaHallService.add(cinemaHallYellow);
        System.out.println(cinemaHallService.get(cinemaHallYellow.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHallYellow);
        movieSession.setLocalDateTime(LocalDateTime.now());

        MovieSessionService movieSessionService = (MovieSessionService) INJECTOR
                .getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService.findAvailableSessions(movieSessionService.get(movieSession.getId())
                        .getId(), LocalDate.now()).forEach(System.out::println);

    }
}
