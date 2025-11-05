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

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(100);
        blueHall.setDescription("Blue Hall with comfy seats");
        cinemaHallService.add(blueHall);

        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(120);
        redHall.setDescription("Red IMAX Hall");
        cinemaHallService.add(redHall);

        System.out.println(cinemaHallService.get(blueHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(blueHall);
        LocalDateTime sessionTime = LocalDate.now().atTime(19, 30);
        movieSession.setShowTime(sessionTime);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));

        LocalDate today = LocalDate.now();
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), today)
                .forEach(System.out::println);
    }
}
