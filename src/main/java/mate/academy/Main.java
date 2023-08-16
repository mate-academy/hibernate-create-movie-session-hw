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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall(500, "Very big cinema hall");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        LocalDateTime dateOfSession = LocalDateTime.of(
                2023, 8, 20, 18,20);
        MovieSession movieSession = new MovieSession(fastAndFurious, cinemaHall, dateOfSession);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        LocalDate desiredDateOfSession = LocalDate.of(2023, 8, 20);
        LocalDate dateWithoutSessions = LocalDate.of(2023, 4, 20);
        movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), desiredDateOfSession).forEach(System.out::println);
        movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), dateWithoutSessions).forEach(System.out::println);
    }
}
