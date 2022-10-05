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
    private static Injector injector = Injector.getInstance("mate.academy");

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

        CinemaHall red = new CinemaHall();
        red.setCapacity(100);
        red.setDescription("RED");
        cinemaHallService.add(red);

        MovieSession fastAndFuriousInRed20221215 = new MovieSession();
        fastAndFuriousInRed20221215.setMovie(fastAndFurious);
        fastAndFuriousInRed20221215.setCinemaHall(red);
        fastAndFuriousInRed20221215.setLocalDateTime(
                LocalDateTime.of(2022, 12, 15, 12, 30));
        movieSessionService.add(fastAndFuriousInRed20221215);

        MovieSession fastAndFuriousInRed20221216 = new MovieSession();
        fastAndFuriousInRed20221216.setMovie(fastAndFurious);
        fastAndFuriousInRed20221216.setCinemaHall(red);
        fastAndFuriousInRed20221216.setLocalDateTime(
                LocalDateTime.of(2022, 12, 16, 12, 30));
        movieSessionService.add(fastAndFuriousInRed20221216);

        System.out.println(cinemaHallService.get(red.getId()));
        System.out.println(movieSessionService.get(
                fastAndFuriousInRed20221215.getId()));
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println(movieSessionService.findAvailableSessions(
                1L, LocalDate.of(2022, 12, 15)));
    }
}
