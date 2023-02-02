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
        Injector injector = Injector.getInstance("mate.academy");

        System.out.println("MOVIE DAO--------------------------------");
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        System.out.println(movieService.add(fastAndFurious));
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.getAll());

        System.out.println("CINEMA HALL DAO--------------------------------");
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("Clean hall");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        System.out.println(cinemaHallService.add(cinemaHall));
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        System.out.println(cinemaHallService.getAll());

        System.out.println("MOVIE SESSION DAO--------------------------------");
        LocalDateTime dateTime = LocalDateTime.of(2023, 5, 12, 15, 0);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(dateTime);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        System.out.println(movieSessionService.add(movieSession));
        System.out.println(movieSessionService.get(movieSession.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.from(dateTime)));
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now()));

    }
}
