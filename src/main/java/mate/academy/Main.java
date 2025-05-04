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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR.getInstance(
                CinemaHallService.class);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("VIP hall");
        cinemaHall.setCapacity(24);
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));

        MovieSession movieSessionAfterDate = new MovieSession();
        movieSessionAfterDate.setMovie(fastAndFurious);
        movieSessionAfterDate.setCinemaHall(cinemaHall);
        movieSessionAfterDate.setShowTime(LocalDateTime.of(
                2024, 10, 11, 12,40));

        MovieSession movieSessionBeforeDate = new MovieSession();
        movieSessionBeforeDate.setMovie(fastAndFurious);
        movieSessionBeforeDate.setCinemaHall(cinemaHall);
        movieSessionBeforeDate.setShowTime(LocalDateTime.of(
                2024, 6, 11, 14,40));

        MovieSessionService movieSessionService = (MovieSessionService) INJECTOR.getInstance(
                MovieSessionService.class);
        movieSessionService.add(movieSessionAfterDate);
        movieSessionService.add(movieSessionBeforeDate);

        System.out.println(movieSessionService.findAvailableSessions(
                fastAndFurious.getId(),LocalDate.now()));
    }
}
