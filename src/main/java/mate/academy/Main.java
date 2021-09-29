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
    public static void main(String[] args) {

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(500);
        firstCinemaHall.setDescription("large cinema hall");

        MovieSession fastAndFuriosSession = new MovieSession();
        fastAndFuriosSession.setMovie(fastAndFurious);
        fastAndFuriosSession.setCinemaHall(firstCinemaHall);
        fastAndFuriosSession.setShowTime(LocalDateTime.of(2021, 9, 29, 19, 56));

        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(firstCinemaHall);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriosSession);

        System.out.println(cinemaHallService.get(1L));
        System.out.println();
        System.out.println(cinemaHallService.getAll());
        System.out.println();
        System.out.println(movieSessionService.get(1L));
        System.out.println();
        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.now()));
        System.out.println();
        System.out.println(movieService.get(1L));
        System.out.println();
        System.out.println(movieService.get(1L));
        System.out.println();
        movieService.getAll().forEach(System.out::println);
    }
}
