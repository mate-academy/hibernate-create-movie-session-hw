package mate.academy;

import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing,"
                + " heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall greenHall = new CinemaHall();
        greenHall.setCapacity(70);
        greenHall.setDescription("green hall - medium-capacity hall for watching movies");
        cinemaHallService.add(greenHall);
        System.out.println(cinemaHallService.get(greenHall.getId()));
        System.out.println(cinemaHallService.getAll());
        MovieSession focusGroup = new MovieSession();
        focusGroup.setCinemaHall(greenHall);
        focusGroup.setMovie(fastAndFurious);
        LocalDateTime dateOfShowTime =
                LocalDateTime.now();
        focusGroup.setShowTime(dateOfShowTime);
        movieSessionService.add(focusGroup);
        System.out.println(movieSessionService.get(focusGroup.getId()));
        System.out.println(movieSessionService.findAvailableSessions(focusGroup.getId(),
                dateOfShowTime.toLocalDate()));
    }
}
