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
        Movie hobbit = new Movie("Hobbit");
        hobbit.setDescription("A fairy tale about small guy, gnomes and one ring...");
        movieService.add(hobbit);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall blockBaster = new CinemaHall();
        blockBaster.setCapacity(500);
        blockBaster.setDescription("Big cinema hall located near Pochaina subway station");
        cinemaHallService.add(blockBaster);

        MovieSession fastAndFuriousBlockbuster = new MovieSession();
        fastAndFuriousBlockbuster.setCinemaHall(blockBaster);
        fastAndFuriousBlockbuster.setMovie(fastAndFurious);
        fastAndFuriousBlockbuster.setShowTime(LocalDateTime.of(2021, 6, 3, 12, 0));
        movieSessionService.add(fastAndFuriousBlockbuster);

        MovieSession hobbitBlockbuster = new MovieSession();
        hobbitBlockbuster.setMovie(hobbit);
        hobbitBlockbuster.setCinemaHall(blockBaster);
        hobbitBlockbuster.setShowTime(LocalDateTime.now());
        movieSessionService.add(hobbitBlockbuster);

        movieSessionService.findAvailableSessions(hobbit.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
