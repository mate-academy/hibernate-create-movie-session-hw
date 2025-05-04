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
    private static MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        Movie avatar = new Movie("AVATAR 2");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        avatar.setDescription("A fantastic movie");
        movieService.add(fastAndFurious);
        movieService.add(avatar);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(avatar.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall(56, "Red cinema Hall");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDateTime localDateTime = LocalDateTime.of(2023, 7, 3, 19, 10);
        MovieSession movieSession = new MovieSession(fastAndFurious, cinemaHall, localDateTime);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2023, 7, 3)).forEach(System.out::println);
    }
}
