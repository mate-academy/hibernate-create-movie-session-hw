package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
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
        Movie fastAndFurious = new Movie("Fast and Furious",
                "An action film about street racing, heists, and spies.");
        Movie barbie = new Movie("Barbie", "I'm just Ken, anywhere else I'd be a ten");
        Movie oppenheimer = new Movie("Oppenheimer", "Shomas Thelby created nuclear bomb");
        Arrays.asList(fastAndFurious, barbie, oppenheimer)
                .forEach(movieService::add);

        movieService.getAll().forEach(System.out::println);
        System.out.println(movieService.get(barbie.getId()));

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall imaxHall = new CinemaHall(250, "IMAX");
        CinemaHall hall3D = new CinemaHall(100, "3D");
        cinemaHallService.add(imaxHall);
        cinemaHallService.add(hall3D);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        List<MovieSession> movieSessions = List.of(
                new MovieSession(fastAndFurious, imaxHall, LocalDateTime.parse("2023-09-26T14:30")),
                new MovieSession(barbie, hall3D, LocalDateTime.parse("2023-09-26T18:30")),
                new MovieSession(oppenheimer, hall3D, LocalDateTime.parse("2023-09-28T12:30")),
                new MovieSession(oppenheimer, imaxHall, LocalDateTime.parse("2023-09-27T12:00")),
                new MovieSession(fastAndFurious, imaxHall, LocalDateTime.parse("2023-09-29T15:45")),
                new MovieSession(barbie, hall3D, LocalDateTime.parse("2023-09-27T16:30")),
                new MovieSession(fastAndFurious, hall3D, LocalDateTime.parse("2023-09-26T20:00")),
                new MovieSession(oppenheimer, imaxHall, LocalDateTime.parse("2023-09-28T18:30")),
                new MovieSession(fastAndFurious, imaxHall, LocalDateTime.parse("2023-09-26T10:30")),
                new MovieSession(oppenheimer, hall3D, LocalDateTime.parse("2023-09-27T21:20")),
                new MovieSession(barbie, hall3D, LocalDateTime.parse("2023-09-26T17:25"))
        );
        movieSessions.forEach(movieSessionService::add);
        movieSessionService.findAvailableSessions(
                oppenheimer.getId(), LocalDate.parse("2023-09-27"))
                .forEach(System.out::println);
    }
}
