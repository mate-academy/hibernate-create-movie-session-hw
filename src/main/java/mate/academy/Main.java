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
    private static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {

        Movie fastAndFurious = new Movie("Fast and Furious");
        Movie fastAndFurious2 = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        fastAndFurious2
                .setDescription("An action film about more street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        movieService.add(fastAndFurious2);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.getAll());

        CinemaHall cinemaHall1 = new CinemaHall();
        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall1.setCapacity(100);
        cinemaHall2.setCapacity(120);
        cinemaHall1.setDescription("Red");
        cinemaHall2.setDescription("Blue");
        cinemaHallService.add(cinemaHall1);
        cinemaHallService.add(cinemaHall2);
        cinemaHallService.get(cinemaHall1.getId());
        System.out.println(cinemaHallService.getAll());

        MovieSession movieSession1 = new MovieSession();
        MovieSession movieSession2 = new MovieSession();
        movieSession1.setCinemaHall(cinemaHall1);
        movieSession2.setCinemaHall(cinemaHall2);
        movieSession1.setMovie(fastAndFurious);
        movieSession2.setMovie(fastAndFurious2);
        movieSession1.setShowTime(LocalDateTime.of(2021, 7, 10, 20, 0));
        movieSession2.setShowTime(LocalDateTime.of(2021, 7, 11, 20, 0));
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        System.out.println(movieSessionService.get(movieSession1.getId()));
        System.out.println(movieSessionService.findAvailableSessions(movieSession2.getId(),
                LocalDate.of(2021, 7, 11)));
        System.out.println(movieSessionService.findAvailableSessions(movieSession2.getId(),
                LocalDate.of(2021, 7, 12)));

    }
}
