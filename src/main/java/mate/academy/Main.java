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
        final MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall imaxHall = new CinemaHall();
        imaxHall.setCapacity(200);
        imaxHall.setDescription("Hall with super screen and extraordinary sound");
        cinemaHallService.add(imaxHall);
        System.out.println(cinemaHallService.get(imaxHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession eveningSession = new MovieSession();
        eveningSession.setCinemaHall(imaxHall);
        eveningSession.setMovie(fastAndFurious);
        eveningSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(eveningSession);
        System.out.println(movieSessionService.get(eveningSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
