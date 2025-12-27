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
    private static final Injector instance
            = Injector.getInstance("mate.academy");
    private static final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService)
                instance.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService = (CinemaHallService)
                instance.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService = (MovieSessionService)
                instance.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription(
                "An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall hall = new CinemaHall();
        hall.setCapacity(100);
        hall.setDescription("A wonderful cinema hall with cozy atmosphere.");
        cinemaHallService.add(hall);
        System.out.println(cinemaHallService.get(hall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession session = new MovieSession();
        session.setShowTime(LocalDateTime.parse(
                "20.01.2025 20:00", dateTimeFormatter));
        session.setMovie(fastAndFurious);
        session.setCinemaHall(hall);
        movieSessionService.add(session);
        System.out.println(movieSessionService.get(session.getId()));
        movieSessionService.findAvailableSessions(
                fastAndFurious.getId(),
                LocalDate.parse("20.01.2025", dateFormatter))
                .forEach(System.out::println);
    }
}
