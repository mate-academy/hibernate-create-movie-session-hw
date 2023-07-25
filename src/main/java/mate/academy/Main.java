package mate.academy;

import java.time.LocalDate;
import mate.academy.lib.Injector;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        /*Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(5);
        cinemaHall.setDescription("desc");

        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(1L));
        System.out.println(cinemaHallService.getAll());

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(cinemaHall);
        movieSession1.setShowTime(LocalDateTime.now().minusDays(1));

        System.out.println(movieSessionService.add(movieSession1));

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setCinemaHall(cinemaHall);
        movieSession2.setShowTime(LocalDateTime.now());

        System.out.println(movieSessionService.add(movieSession2));

        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(fastAndFurious);
        movieSession3.setCinemaHall(cinemaHall);
        movieSession3.setShowTime(LocalDateTime.now().plusDays(1));

        System.out.println(movieSessionService.add(movieSession3));

        System.out.println(movieSessionService.get(3L));*/

        System.out.println(movieSessionService.findAvailableSessions(5L, LocalDate.now()));
    }
}
