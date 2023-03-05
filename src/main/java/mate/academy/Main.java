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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall hallNumber5 = new CinemaHall();
        hallNumber5.setCapacity(50);
        hallNumber5.setDescription("New hall for 2D/3D films");
        cinemaHallService.add(hallNumber5);
        CinemaHall hallNumber6 = new CinemaHall();
        hallNumber6.setCapacity(60);
        hallNumber6.setDescription("Old hall for 2D/3D films");
        cinemaHallService.add(hallNumber6);
        MovieSession fastAndFuriousSessionOne = new MovieSession();
        MovieSession fastAndFuriousSessionTwo = new MovieSession();
        fastAndFuriousSessionOne.setMovie(fastAndFurious);
        fastAndFuriousSessionTwo.setMovie(fastAndFurious);
        fastAndFuriousSessionOne.setCinemaHall(hallNumber5);
        fastAndFuriousSessionTwo.setCinemaHall(hallNumber6);
        fastAndFuriousSessionOne.setShowTime(LocalDateTime.of(2023, 3, 5, 15, 40));
        fastAndFuriousSessionTwo.setShowTime(LocalDateTime.of(2023, 3, 5, 20, 20));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSessionOne);
        movieSessionService.add(fastAndFuriousSessionTwo);
        List<MovieSession> availableSessions =
                movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.of(2023, 3, 5));
        availableSessions.forEach(System.out::println);
    }
}
