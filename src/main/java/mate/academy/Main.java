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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        Movie morbius = new Movie("Morbius");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        morbius.setDescription("It's morbing time!");
        movieService.add(fastAndFurious);
        movieService.add(morbius);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall redHall = new CinemaHall(30, "Red hall");
        CinemaHall blueHall = new CinemaHall(60, "Blue hall");
        cinemaHallService.add(redHall);
        cinemaHallService.add(blueHall);
        System.out.println(cinemaHallService.get(blueHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession firstSession = new MovieSession(morbius, redHall,
                LocalDateTime.parse("2022-06-02T10:15:30"));
        MovieSession secondSession = new MovieSession(fastAndFurious, blueHall,
                LocalDateTime.parse("2022-06-03T10:15:30"));
        MovieSession thirdSession = new MovieSession(morbius, redHall,
                LocalDateTime.parse("2022-06-03T10:17:00"));
        MovieSession forthSession = new MovieSession(fastAndFurious, redHall,
                LocalDateTime.parse("2022-06-03T10:19:00"));
        movieSessionService.add(firstSession);
        movieSessionService.add(secondSession);
        movieSessionService.add(thirdSession);
        movieSessionService.add(forthSession);
        System.out.println(movieSessionService.get(firstSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.parse("2022-06-03")).forEach(System.out::println);
    }
}
