package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie fastAndFurious2 = new Movie("Fast and Furious 2");
        fastAndFurious2.setDescription("An action film about street racing, heists, and spies 2.");
        movieService.add(fastAndFurious2);

        Movie fastAndFurious3 = new Movie("Fast and Furious 3");
        fastAndFurious3.setDescription("An action film about street racing, heists, and spies 3.");
        movieService.add(fastAndFurious3);
        System.out.println("Get movie by id:");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("Get all movies:");
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(200);
        cinemaHall.setDescription("4Dx Cinema Hall");
        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall2.setCapacity(200);
        cinemaHall2.setDescription("Regular Cinema Hall");
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        cinemaHallService.add(cinemaHall2);
        System.out.println("Cinema Hall by id:");
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        System.out.println("Get All Cinema Halls");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setMovie(fastAndFurious);
        movieSession.setLocalDate(LocalDate.from(LocalDateTime.now().plusDays(5)));

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSession.setId(null);
        movieSession.setCinemaHall(cinemaHall2);
        movieSessionService.add(movieSession);
        LocalDate localDate = LocalDate.from(LocalDateTime.now().plusDays(5));
        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(1L, localDate);
        for (var session : availableSessions) {
            System.out.println("Movie "
                    + session.getMovie().getTitle() + " on "
                    + session.getLocalDate() + " at "
                    + session.getCinemaHall().getDescription());
        }
    }
}
