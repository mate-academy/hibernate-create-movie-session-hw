package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final DateTimeFormatter dateTimeFormatter
            = DateTimeFormatter.ofPattern(("dd MM yyyy HH:mm"));

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall red = cinemaHallService.add(
                new CinemaHall(40, "small cinema-hall for dating"));
        CinemaHall green = cinemaHallService.add(
                new CinemaHall(70, "new cinema-hall with high technologies"));
        CinemaHall blue = cinemaHallService.add(
                new CinemaHall(120, "biggest cinema-hall for max impression"));
        CinemaHall yellow = cinemaHallService.add(
                new CinemaHall(100, "IMAX 3D cinema-hall for action, baby"));
        cinemaHallService.getAll()
                .forEach(System.out::println);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession movieSession1 = movieSessionService.add(new MovieSession(
                fastAndFurious, red, LocalDateTime.parse("06 05 2022 10:00", dateTimeFormatter)));
        MovieSession movieSession2 = movieSessionService.add(new MovieSession(
                fastAndFurious, green, LocalDateTime.parse("06 05 2022 12:00", dateTimeFormatter)));
        MovieSession movieSession3 = movieSessionService.add(new MovieSession(
                fastAndFurious, blue, LocalDateTime.parse("06 05 2022 14:00", dateTimeFormatter)));
        MovieSession movieSession4 = movieSessionService.add(new MovieSession(
                fastAndFurious, yellow,
                LocalDateTime.parse("06 05 2022 16:00", dateTimeFormatter)));
        System.out.println(movieSessionService.get(fastAndFurious.getId()));

        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.of(2022, 5, 6))
                .forEach(System.out::println);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
    }
}
