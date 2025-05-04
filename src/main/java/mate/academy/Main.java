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
        Movie fastAndFurious2 = new Movie("Fast and Furious222");
        fastAndFurious2.setDescription("22An action film about street racing,222");
        movieService.add(fastAndFurious);
        movieService.add(fastAndFurious2);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setCapacity(100);
        cinemaHall1.setDescription("dno 100 places");
        cinemaHallService.add(cinemaHall1);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(cinemaHall1);
        movieSession1.setShowTime(LocalDateTime.parse("2024-04-05T15:23:01"));

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        List<MovieSession> msl = movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.parse("2024-04-05"));
        System.out.println(msl + " msl ");
    }
}
