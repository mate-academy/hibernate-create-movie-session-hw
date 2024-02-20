package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        final MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);
        final CinemaService cinemaService = (CinemaService) injector
                .getInstance(CinemaService.class);
        final MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        // Movie block
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        //CinemaHall block
        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("Large and spacious hall");
        cinemaService.add(firstCinemaHall);
        System.out.println(cinemaService.get(firstCinemaHall.getId()));
        cinemaService.getAll().forEach(System.out::println);

        //MovieSession block
        MovieSession eveningSessionOnMay5th = new MovieSession();
        eveningSessionOnMay5th.setCinemaHall(firstCinemaHall);
        eveningSessionOnMay5th.setMovie(fastAndFurious);
        eveningSessionOnMay5th.setShowTime(LocalDateTime.of(2024, 5, 5, 19, 0));
        movieSessionService.add(eveningSessionOnMay5th);
        System.out.println(movieSessionService.get(eveningSessionOnMay5th.getId()));
        Long movieId = fastAndFurious.getId();
        LocalDate date = LocalDate.of(2024, 5, 5);
        System.out.println(movieSessionService.findAvailableSessions(movieId, date));
    }
}
