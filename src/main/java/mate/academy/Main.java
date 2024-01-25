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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        final MovieSessionService movieSessionService = (MovieSessionService) INJECTOR
                .getInstance(MovieSessionService.class);
        final CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR
                .getInstance(CinemaHallService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll()
                .forEach(System.out::println);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(200);
        cinemaHall.setDescription("Very large hall");
        cinemaHallService.add(cinemaHall);
        for (int i = 1; i < 7; i++) {
            fastAndFurious = new Movie("Fast and Furious " + i);
            movieService.add(fastAndFurious);
            MovieSession movieSession = new MovieSession();
            movieSession.setShowTime(LocalDateTime.now()
                    .plusDays(i));
            movieSession.setMovie(fastAndFurious);
            movieSession.setCinemaHall(cinemaHall);
            movieSessionService.add(movieSession);
        }
        for (int day = 1, id = 2; day < 7; day++, id++) {
            List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(
                    (long) id, LocalDate.now()
                            .plusDays(day));
            System.out.println(availableSessions);
        }
    }
}
