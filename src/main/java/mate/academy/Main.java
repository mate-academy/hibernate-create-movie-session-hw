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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie dunkirk = new Movie("Dunkirk");
        dunkirk.setDescription("A film about Dunkirk in 1940 and operation \"Dynamo\".");
        movieService.add(dunkirk);

        CinemaHall cinemaHallOne = new CinemaHall();
        cinemaHallOne.setCapacity(30);
        cinemaHallOne.setDescription("30 people will watch film here");
        cinemaHallService.add(cinemaHallOne);

        CinemaHall cinemaHallTwo = new CinemaHall();
        cinemaHallTwo.setCapacity(10);
        cinemaHallTwo.setDescription("10 people will watch film here");
        cinemaHallService.add(cinemaHallTwo);

        MovieSession movieSessionOne = new MovieSession();
        movieSessionOne.setMovie(dunkirk);
        movieSessionOne.setShowTime(LocalDateTime.of(
                2021, 12, 27, 12, 30));
        movieSessionOne.setCinemaHall(cinemaHallOne);
        movieSessionService.add(movieSessionOne);

        MovieSession movieSessionTwo = new MovieSession();
        movieSessionTwo.setMovie(dunkirk);
        movieSessionTwo.setShowTime(LocalDateTime.of(
                2021, 12, 27, 15, 20));
        movieSessionTwo.setCinemaHall(cinemaHallTwo);
        movieSessionService.add(movieSessionTwo);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println(cinemaHallService.get(cinemaHallOne.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println(movieSessionService.get(movieSessionOne.getId()));
        movieSessionService.findAvailableSessions(dunkirk.getId(), LocalDate.of(
                2021, 12, 27)).forEach(System.out::println);
    }
}
