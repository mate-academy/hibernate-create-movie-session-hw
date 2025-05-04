package mate.academy;

import java.time.LocalDate;
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
        CinemaHall cinemaHallBlue = new CinemaHall();
        cinemaHallBlue.setCapacity(35);
        cinemaHallBlue.setDescription("blue room");

        CinemaHall cinemaHallRed = new CinemaHall();
        cinemaHallRed.setCapacity(45);
        cinemaHallRed.setDescription("red room");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        cinemaHallService.add(cinemaHallBlue);
        cinemaHallService.add(cinemaHallRed);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        Movie lordOfTheRing = new Movie("Lord of the Rings");
        lordOfTheRing.setDescription("Is the saga of a group of sometimes reluctant heroes who"
                + " set forth to save their world from consummate evil.");

        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        movieService.add(fastAndFurious);
        movieService.add(lordOfTheRing);

        MovieSession movieSession01 = new MovieSession();
        movieSession01.setShowTime(LocalDate.now());
        movieSession01.setMovie(fastAndFurious);
        movieSession01.setCinemaHall(cinemaHallBlue);

        MovieSession movieSession02 = new MovieSession();
        movieSession02.setShowTime(LocalDate.now());
        movieSession02.setMovie(lordOfTheRing);
        movieSession02.setCinemaHall(cinemaHallRed);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        movieSessionService.add(movieSession01);
        movieSessionService.add(movieSession02);

        System.out.println(movieService.get(fastAndFurious.getId()));

        movieService.getAll().forEach(System.out::println);

        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("find available sessions: "
                + movieSessionService.findAvailableSessions(1L,
                LocalDate.of(2023, 8, 31)));
    }
}
