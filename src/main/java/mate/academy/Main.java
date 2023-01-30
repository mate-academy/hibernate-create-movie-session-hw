package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieService;
import mate.academy.service.impl.CinemaHallService;
import mate.academy.service.impl.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie paleEyes = new Movie("Pale Eyes");
        paleEyes.setDescription("detective");
        movieService.add(paleEyes);
        System.out.println(movieService.get(paleEyes.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHallTiny = new CinemaHall(20, "tiny");
        cinemaHallService.add(cinemaHallTiny);
        System.out.println(cinemaHallService.get(cinemaHallTiny.getId()));
        CinemaHall cinemaHallMedium = new CinemaHall(60, "medium");
        cinemaHallService.add(cinemaHallMedium);
        System.out.println(cinemaHallService.get(cinemaHallMedium.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession movieSessionPaleEyesTinyHall = new MovieSession(paleEyes, cinemaHallTiny,
                LocalDateTime.of(2023, 1, 29, 0, 0, 0));
        movieSessionService.add(movieSessionPaleEyesTinyHall);
        System.out.println(movieSessionService.get(movieSessionPaleEyesTinyHall.getId()));
        MovieSession movieSessionFastAndFuriousMediumHall =
                new MovieSession(fastAndFurious, cinemaHallMedium,
                LocalDateTime.of(2023, 1, 29, 23, 59, 59));
        movieSessionService.add(movieSessionFastAndFuriousMediumHall);
        System.out.println(movieSessionService.get(movieSessionFastAndFuriousMediumHall.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2023, 1, 29))
                        .forEach(System.out::println);
    }
}
