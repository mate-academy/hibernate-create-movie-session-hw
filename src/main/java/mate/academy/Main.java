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
        final CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall multiplexUnion = new CinemaHall();
        multiplexUnion.setCapacity(200);
        multiplexUnion.setDescription("Movie theater complex with multiple "
                + "screens within a single complex");
        cinemaHallService.add(multiplexUnion);
        System.out.println(cinemaHallService.getAll());

        MovieSession movieSessionFastAndFurious = new MovieSession();
        movieSessionFastAndFurious.setMovie(fastAndFurious);
        movieSessionFastAndFurious.setCinemaHall(multiplexUnion);
        movieSessionFastAndFurious.setShowTime(LocalDateTime.of(2021, 12, 30, 20, 00));
        movieSessionService.add(movieSessionFastAndFurious);
        System.out.println(movieSessionService.get(fastAndFurious.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2021, 12, 30));
    }
}
