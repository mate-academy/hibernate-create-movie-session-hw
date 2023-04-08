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
    private static final Injector injector
            = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie tetris = new Movie("Tetris");
        tetris.setDescription("A game that you can't stop playing. "
                + "A story that can't be imagined");

        System.out.println("----------------------------");
        System.out.println("Start testing MovieService.");

        System.out.println("----------------------------");
        System.out.println("Add method was called.");
        System.out.println(movieService.add(tetris));
        System.out.println("Done.");

        System.out.println("----------------------------");
        System.out.println("Get by id method was called.");
        System.out.println(movieService.get(tetris.getId()));
        System.out.println("Done.");

        System.out.println("----------------------------");
        System.out.println("GetAll method was called.");
        movieService.getAll().forEach(System.out::println);
        System.out.println("Done.");

        System.out.println("----------------------------");
        System.out.println("All tests finished.");

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall blueHall = new CinemaHall(
                "4DX hall is action and spectacle "
                + "prescribes special effects for every blockbuster or cartoon "
                + "sometimes together with the director "
                + "The effects track is thought out and synchronized to the second and "
                + "controlled by a computer");
        blueHall.setCapacity(50);
        cinemaHallService.add(blueHall);

        CinemaHall yellowHall = new CinemaHall(
                "IMAX hall is a proprietary system"
                + " of high-resolution cameras, film formats, film projectors, "
                + "and theaters known for having very large screens "
                + "and steep stadium seating.");
        yellowHall.setCapacity(100);

        System.out.println("----------------------------");
        System.out.println("Start testing CinemaHallService.");

        System.out.println("----------------------------");
        System.out.println("Add method was called.");
        System.out.println(cinemaHallService.add(yellowHall));
        System.out.println("Done.");

        System.out.println("----------------------------");
        System.out.println("Get by id method was called.");
        System.out.println(cinemaHallService.get(yellowHall.getId()));
        System.out.println("Done.");

        System.out.println("----------------------------");
        System.out.println("GetAll method was called.");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("Done.");

        System.out.println("----------------------------");
        System.out.println("All tests finished.");

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession fastAndFuriousMovieSession = new MovieSession(
                movieService.get(fastAndFurious.getId()),
                cinemaHallService.get(blueHall.getId()),
                LocalDateTime.now().plusDays(2).plusHours(4)
        );
        movieSessionService.add(fastAndFuriousMovieSession);

        MovieSession tetrisMovieSession = new MovieSession(
                movieService.get(tetris.getId()),
                cinemaHallService.get(yellowHall.getId()),
                LocalDateTime.now().plusDays(4).minusHours(8)
        );

        System.out.println("----------------------------");
        System.out.println("Start testing MovieSessionService.");

        System.out.println("----------------------------");
        System.out.println("Add method was called.");
        System.out.println(movieSessionService.add(tetrisMovieSession));
        System.out.println("Done.");

        System.out.println("----------------------------");
        System.out.println("Get by id method was called.");
        System.out.println(movieSessionService.get(tetrisMovieSession.getId()));
        System.out.println("Done.");

        System.out.println("----------------------------");
        System.out.println("FindAvailableSessions method was called.");
        System.out.println(movieSessionService.findAvailableSessions(
                tetris.getId(),
                LocalDate.from(LocalDateTime.now().plusDays(4).minusHours(8))
                ));
        System.out.println("Done.");

        System.out.println("----------------------------");
        System.out.println("All tests finished.");
    }
}
