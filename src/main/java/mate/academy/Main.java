package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        System.out.println("Movie with id " + fastAndFurious.getId() + " from DB: ");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("All movies from DB: ");
        movieService.getAll().forEach(System.out::println);
        System.out.println("-------------------------------");

        CinemaHall imaxHall = new CinemaHall();
        imaxHall.setCapacity(150);
        imaxHall.setDescription("IMAX 3D cinema hall, hang out glasses before entering");
        cinemaHallService.add(imaxHall);
        System.out.println("Cinema hall with id " + imaxHall.getId() + " from DB: ");
        System.out.println(cinemaHallService.get(imaxHall.getId()));
        System.out.println("All cinema halls from DB: ");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("-------------------------------");

        int hours = 14;
        int min = 30;
        LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.of(hours, min));
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(imaxHall);
        movieSession.setShowTime(date);
        movieSessionService.add(movieSession);
        System.out.println("Movie session with id " + movieSession.getId() + " from DB: ");
        System.out.println(movieSessionService.get(movieSession.getId()));
        System.out.println("All movie session at " + date + " from DB: ");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), date.toLocalDate())
                .forEach(System.out::println);
    }
}
