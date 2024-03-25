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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService hallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService sessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie tarasBulba = new Movie("Taras Bulba");
        tarasBulba.setDescription("Film about history when father kill his son.");
        movieService.add(tarasBulba);

        CinemaHall redHall = new CinemaHall();
        redHall.setHallName("red");
        redHall.setSeats(350L);
        hallService.add(redHall);
        CinemaHall blueHall = new CinemaHall();
        blueHall.setHallName("blue");
        blueHall.setSeats(430L);
        hallService.add(blueHall);

        MovieSession session1 = new MovieSession();
        session1.setHall(redHall);
        session1.setMovie(fastAndFurious);
        session1.setDateTime(LocalDateTime.of(2024, 3, 23, 13, 30));
        sessionService.add(session1);
        MovieSession session2 = new MovieSession();
        session2.setHall(redHall);
        session2.setMovie(tarasBulba);
        session2.setDateTime(LocalDateTime.of(2024, 3, 22, 12, 30));
        sessionService.add(session2);
        MovieSession session3 = new MovieSession();
        session3.setHall(blueHall);
        session3.setMovie(tarasBulba);
        session3.setDateTime(LocalDateTime.of(2024, 3, 22, 18, 0));
        sessionService.add(session3);
        MovieSession session4 = new MovieSession();
        session4.setHall(redHall);
        session4.setMovie(fastAndFurious);
        session4.setDateTime(LocalDateTime.of(2024, 3, 23, 16, 30));
        sessionService.add(session4);
        MovieSession session5 = new MovieSession();
        session5.setHall(redHall);
        session5.setMovie(fastAndFurious);
        session5.setDateTime(LocalDateTime.of(2024, 3, 23, 20, 0));
        sessionService.add(session5);
        MovieSession session6 = new MovieSession();
        session6.setHall(redHall);
        session6.setMovie(fastAndFurious);
        session6.setDateTime(LocalDateTime.of(2024, 3, 23, 15, 0));
        sessionService.add(session6);

        sessionService.findAvailableSessions(1L, LocalDate.of(2024, 3, 23))
                .forEach(System.out::println);
    }
}
