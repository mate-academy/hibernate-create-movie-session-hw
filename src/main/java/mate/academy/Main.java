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
        Movie taxi = new Movie("Taxi");
        taxi.setDescription("Comedy film about fast taxi driver and police officer");
        movieService.add(taxi);

        CinemaHall vipHall = new CinemaHall();
        vipHall.setDescription("vip");
        vipHall.setCapacity(30);
        hallService.add(vipHall);
        CinemaHall defaultHall = new CinemaHall();
        defaultHall.setDescription("default");
        defaultHall.setCapacity(70);
        hallService.add(defaultHall);

        MovieSession session1 = new MovieSession();
        session1.setCinemaHall(vipHall);
        session1.setMovie(fastAndFurious);
        session1.setShowTime(LocalDateTime.of(2024, 3, 26, 8, 45));
        sessionService.add(session1);
        MovieSession session2 = new MovieSession();
        session2.setCinemaHall(vipHall);
        session2.setMovie(taxi);
        session2.setShowTime(LocalDateTime.of(2024, 3, 26, 20, 38));
        sessionService.add(session2);
        MovieSession session3 = new MovieSession();
        session3.setCinemaHall(defaultHall);
        session3.setMovie(taxi);
        session3.setShowTime(LocalDateTime.of(2024, 3, 26, 18, 15));
        sessionService.add(session3);
        MovieSession session4 = new MovieSession();
        session4.setCinemaHall(vipHall);
        session4.setMovie(fastAndFurious);
        session4.setShowTime(LocalDateTime.of(2024, 3, 25, 15, 25));
        sessionService.add(session4);
        MovieSession session5 = new MovieSession();
        session5.setCinemaHall(vipHall);
        session5.setMovie(fastAndFurious);
        session5.setShowTime(LocalDateTime.of(2024, 3, 27, 20, 0));
        sessionService.add(session5);

        sessionService.findAvailableSessions(2L, LocalDate.of(2024, 3, 26))
                .forEach(System.out::println);
    }
}
