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
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR
                .getInstance(CinemaHallService.class);
        CinemaHall premiera = new CinemaHall(100, "Premiera Cinema 3D Hall");
        cinemaHallService.add(premiera);
        System.out.println(cinemaHallService.get(premiera.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService msService = (MovieSessionService) INJECTOR
                .getInstance(MovieSessionService.class);
        LocalDateTime localDateTime = LocalDateTime.of(2023, 9, 26, 21, 0);
        MovieSession movieSession = new MovieSession(fastAndFurious, premiera, localDateTime);
        msService.add(movieSession);
        System.out.println(msService.get(movieSession.getId()));
        LocalDate movieDate = LocalDate.of(2023, 9, 26);
        msService.findAvailableSessions(fastAndFurious.getId(), movieDate)
                .forEach(System.out::println);
    }
}
