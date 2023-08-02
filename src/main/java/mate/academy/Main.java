package mate.academy;

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
        final MovieService movieService = (MovieService) INJECTOR
                .getInstance(MovieService.class);
        final CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR
                .getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService = (MovieSessionService) INJECTOR
                .getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie barbie = new Movie("Barbie");
        barbie.setDescription("American fantasy comedy film");
        movieService.add(barbie);
        System.out.println(movieService.get(barbie.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setCapacity(100);
        cinemaHall1.setDescription("description");
        cinemaHallService.add(cinemaHall1);
        System.out.println(cinemaHallService.get(cinemaHall1.getId()));

        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall1.setCapacity(99);
        cinemaHall1.setDescription("Godzilla");
        cinemaHallService.add(cinemaHall2);
        System.out.println(cinemaHallService.get(cinemaHall2.getId()));

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(cinemaHall1);
        movieSession1.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession1);
        System.out.println(movieSessionService.get(movieSession1.getId()));

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(barbie);
        movieSession2.setCinemaHall(cinemaHall2);
        movieSession2.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession2);
        System.out.println(movieSessionService.get(movieSession2.getId()));
    }
}
