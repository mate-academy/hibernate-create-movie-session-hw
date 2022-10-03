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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        System.out.println("Step 1. Added movies");
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie strangeThings = new Movie("Strange things");
        strangeThings.setDescription("Show about live a few teenagers in strange town.");
        movieService.add(strangeThings);
        System.out.println(strangeThings);

        System.out.println("Get all");
        movieService.getAll().forEach(System.out::println);

        System.out.println("Step 2. Added cinema hall");
        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(50);
        redHall.setDescription("Hall is red color");
        cinemaHallService.add(redHall);

        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(50);
        blueHall.setDescription("Hall is red color");
        cinemaHallService.add(blueHall);
        System.out.println("Get all");
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("Step 3. Added movie session");
        MovieSession today = new MovieSession();
        today.setCinemaHall(redHall);
        today.setMovie(strangeThings);
        today.setShowTime(LocalDateTime.of(2022, 9, 30, 12, 30));
        movieSessionService.add(today);
        System.out.println(movieSessionService.get(today.getId()));
        System.out.println("Get all");
        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(
                strangeThings.getId(), LocalDate.of(2022, 9, 30));
        availableSessions.forEach(System.out::println);
    }
}
