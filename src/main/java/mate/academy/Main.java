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
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(32);
        cinemaHall.setDescription("Small hall");
        cinemaHallService.add(cinemaHall);

        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now().withHour(18).withMinute(0).withSecond(0));

        movieSessionService.add(movieSession);

        System.out.println(movieSessionService.get(movieSession.getId()));

        LocalDate sessionDate = movieSession.getShowTime().toLocalDate();

        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(
                fastAndFurious.getId(),
                sessionDate
        );

        System.out.println("Available movie sessions for " + fastAndFurious.getTitle()
                + " on " + sessionDate + ":");
        availableSessions.forEach(System.out::println);
    }
}