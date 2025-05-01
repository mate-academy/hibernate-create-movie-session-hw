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
        var injector = Injector.getInstance("mate.academy");

        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService hallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService sessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie movie = new Movie("Inception");
        movie.setDescription("Dream heist thriller");
        movieService.add(movie);

        CinemaHall hall = new CinemaHall("Hall 1", 100);
        hallService.add(hall);

        MovieSession s1 = new MovieSession(movie, hall,
                LocalDateTime.of(2025, 5, 2, 18, 30));
        MovieSession s2 = new MovieSession(movie, hall,
                LocalDateTime.of(2025, 5, 2, 21, 00));
        sessionService.add(s1);
        sessionService.add(s2);

        System.out.println("All movies:");
        movieService.getAll().forEach(System.out::println);

        System.out.println("All cinema halls:");
        hallService.getAll().forEach(System.out::println);

        System.out.println("Sessions for Inception on 2025-05-02:");
        sessionService.findAvailableSessions(movie.getId(), LocalDate.of(2025, 5, 2))
                .forEach(System.out::println);
    }
}
