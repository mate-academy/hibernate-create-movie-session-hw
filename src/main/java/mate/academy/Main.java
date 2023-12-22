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

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);

        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(100);
        blueHall.setDescription("Blue hall");

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(blueHall);

        MovieSession fridaySession = new MovieSession();
        fridaySession.setMovie(fastAndFurious);
        fridaySession.setCinemaHall(blueHall);
        fridaySession.setLocalDateTime(LocalDateTime
                .of(2023, 12, 22, 12, 30, 0));

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(fridaySession);

        System.out.println("Get movie by id!");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("Get all movies!");
        movieService.getAll().forEach(System.out::println);

        System.out.println("Get cinema hall by id!");
        System.out.println(cinemaHallService.get(blueHall.getId()));
        System.out.println("Get all cinema halls!");
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("Get movie session by id!");
        System.out.println(movieSessionService.get(fridaySession.getId()));
        System.out.println("Get all movie sessions for fridaySession! Date: 2023-12-22");
        movieSessionService.findAvailableSessions(fridaySession.getId(),
                LocalDate.of(2023, 12, 22)).forEach(System.out::println);
        System.out.println("Get all movie sessions for fridaySession! Date: 2011-12-22");
        movieSessionService.findAvailableSessions(fridaySession.getId(),
                LocalDate.of(2011, 12, 22)).forEach(System.out::println);
        System.out.println("Finish!");
    }
}
