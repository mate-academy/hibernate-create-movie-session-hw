package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("Get movie by id: ");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("Get all movies: ");
        for (Movie movie : movieService.getAll()) {
            System.out.println(movie);
        }
        System.out.println("----------------------------------------------------------------");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall multiplex = new CinemaHall();
        multiplex.setCapacity(120);
        multiplex.setDescription("Awesome cinema, highly recommend!");
        cinemaHallService.add(multiplex);
        System.out.println("Get cinema hall by id: ");
        System.out.println(cinemaHallService.get(multiplex.getId()));
        System.out.println("Get all cinema halls: ");
        for (CinemaHall cinemaHall : cinemaHallService.getAll()) {
            System.out.println(cinemaHall);
        }
        System.out.println("----------------------------------------------------------------");

        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setCinemaHall(multiplex);
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setShowTime(LocalDateTime.of(
                LocalDate.now(), LocalTime.of(14, 0)));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSession);
        System.out.println("Get session by id: ");
        System.out.println(movieSessionService.get(fastAndFuriousSession.getId()));
        System.out.println("Get all sessions for New Year: ");
        List<MovieSession> availableSessionsForNewYear = movieSessionService.findAvailableSessions(
                fastAndFuriousSession.getId(), LocalDate.of(2023, 1, 1));
        if (availableSessionsForNewYear.isEmpty()) {
            System.out.println("There are no sessions for this date!");
        } else {
            for (MovieSession movieSession : availableSessionsForNewYear) {
                System.out.println(movieSession);
            }
        }
        System.out.println("Get all sessions for today: ");
        List<MovieSession> availableSessionsForToday = movieSessionService
                .findAvailableSessions(fastAndFuriousSession.getId(), LocalDate.now());
        for (MovieSession movieSession : availableSessionsForToday) {
            System.out.println(movieSession);
        }
    }
}
