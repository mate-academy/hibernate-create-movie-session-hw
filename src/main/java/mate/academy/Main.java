package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        MovieService movieService = (MovieService)
                INJECTOR.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie(
                "Fast and Furious",
                "An action film about street racing, heists, and spies."
        );
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
        CinemaHall vipCinemaHall = new CinemaHall(100, "VIP cinema hall");
        cinemaHallService.add(vipCinemaHall);
        System.out.println(cinemaHallService.get(vipCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService)
                INJECTOR.getInstance(MovieSessionService.class);
        LocalDate movieSessionDate = LocalDate.of(2024, 5, 13);
        LocalTime movieSessionTime = LocalTime.of(16, 30);
        MovieSession movieSession = new MovieSession(
                fastAndFurious,
                vipCinemaHall,
                LocalDateTime.of(movieSessionDate, movieSessionTime)
        );
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(),
                        LocalDate.of(2024, 5, 13)
                ).forEach(System.out::println);
    }
}
