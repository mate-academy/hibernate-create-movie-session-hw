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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        Movie christmas = new Movie("Christmas Pie of the Dead");
        christmas.setDescription("Skeleton Sam met zombie Sally at Matt's X-mas party");
        movieService.add(christmas);

        CinemaHall brownHall = new CinemaHall();
        brownHall.setDescription("New movie hall in the rightmost wing of our cinema");
        brownHall.setCapacity(1488);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(brownHall);
        CinemaHall blueHall = new CinemaHall();
        blueHall.setDescription("A small hall with sharp-edged hovering "
                + "gjukds and quantum centipedes");
        blueHall.setCapacity(41);
        cinemaHallService.add(blueHall);

        MovieSession ffShow = new MovieSession();
        ffShow.setMovie(fastAndFurious);
        ffShow.setCinemaHall(blueHall);
        ffShow.setShowTime(LocalDateTime.of(2021, 12, 27, 23, 34));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(ffShow);
        MovieSession christmasShow = new MovieSession();
        christmasShow.setMovie(christmas);
        christmasShow.setCinemaHall(brownHall);
        christmasShow.setShowTime(LocalDateTime.of(2021, 12, 29, 18, 55));
        movieSessionService.add(christmasShow);
        System.out.println(movieSessionService.findAvailableSessions(christmas.getId(),
                LocalDate.of(2021, 12, 29)));
    }
}
