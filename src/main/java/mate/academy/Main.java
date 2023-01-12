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
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall blueHall = new CinemaHall(200);
        blueHall.setDescription("Our premium hall that supports IMAX 3D");
        cinemaHallService.add(blueHall);
        System.out.println(cinemaHallService.get(blueHall.getId()));
        CinemaHall redHall = new CinemaHall(500);
        redHall.setDescription("Our standard hall");
        cinemaHallService.add(redHall);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        MovieSession movieSessionOne = new MovieSession(fastAndFurious, blueHall, yesterday);
        MovieSession movieSessionTwo = new MovieSession(fastAndFurious, redHall, today);
        MovieSession movieSessionThree = new MovieSession(fastAndFurious, blueHall, today);
        MovieSession movieSessionFour = new MovieSession(fastAndFurious, redHall, tomorrow);
        movieSessionService.add(movieSessionOne);
        movieSessionService.add(movieSessionTwo);
        movieSessionService.add(movieSessionThree);
        movieSessionService.add(movieSessionFour);
        System.out.println(movieSessionService.get(movieSessionOne.getId()));
        movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), today.toLocalDate())
                .forEach(System.out::println);
    }
}
