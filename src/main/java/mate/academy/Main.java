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
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie ameli = new Movie("Ameli");
        ameli.setDescription("Curious");
        movieService.add(ameli);
        System.out.println(movieService.get(ameli.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHallHuge = new CinemaHall(100, "Huge");
        cinemaHallService.add(cinemaHallHuge);
        System.out.println(cinemaHallService.get(cinemaHallHuge.getId()));
        CinemaHall cinemaHallSmall = new CinemaHall(10, "Small");
        cinemaHallService.add(cinemaHallSmall);
        System.out.println(cinemaHallService.get(cinemaHallSmall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession movieSessionAmeliSmallHall = new MovieSession(ameli, cinemaHallSmall,
                LocalDateTime.of(2023, 1, 24, 0, 0, 0));
        movieSessionService.add(movieSessionAmeliSmallHall);
        System.out.println(movieSessionService.get(movieSessionAmeliSmallHall.getId()));
        MovieSession movieSessionAmeliHugeHall = new MovieSession(ameli, cinemaHallHuge,
                LocalDateTime.of(2023, 1, 24, 23, 59, 59));
        movieSessionService.add(movieSessionAmeliHugeHall);
        System.out.println(movieSessionService.get(movieSessionAmeliHugeHall.getId()));
        movieSessionService.findAvailableSessions(ameli.getId(),
                LocalDate.of(2023, 1, 24)).forEach(System.out::println);
    }
}
