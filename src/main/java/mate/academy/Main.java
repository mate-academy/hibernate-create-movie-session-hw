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
        // create movies
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie gladiator = new Movie("Gladiator");
        gladiator.setDescription("Gladiator movie");
        movieService.add(gladiator);

        System.out.println(movieService.get(fastAndFurious.getId()));

        System.out.println("Get all movies: ");
        movieService.getAll().forEach(System.out::println);

        // create cinemaHall
        CinemaHallService cinemaHallService =
                (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);

        CinemaHall bestCinemaHall = new CinemaHall();
        bestCinemaHall.setCapacity(77);
        bestCinemaHall.setDescription("Best of the best cinemaHall");
        cinemaHallService.add(bestCinemaHall);

        CinemaHall superCinemaHall = new CinemaHall();
        superCinemaHall.setCapacity(55);
        superCinemaHall.setDescription("Super cinemaHall");
        cinemaHallService.add(superCinemaHall);

        System.out.println(cinemaHallService.get(bestCinemaHall.getId()));

        System.out.println("Get all cinemaHalls: ");
        cinemaHallService.getAll().forEach(System.out::println);

        // create movieSessions
        MovieSessionService movieSessionService =
                (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);
        LocalDateTime fastAndFuriousShowTimeFirst =
                LocalDateTime.of(2023, 11, 20, 10, 0);
        MovieSession fastAndFuriousMovieSessionFirst =
                new MovieSession(fastAndFuriousShowTimeFirst, fastAndFurious, bestCinemaHall);

        LocalDateTime fastAndFuriousShowTimeSecond = LocalDateTime.of(2023, 11, 21, 22, 10);
        MovieSession fastAndFuriousMovieSessionSecond =
                new MovieSession(fastAndFuriousShowTimeSecond, fastAndFurious, bestCinemaHall);

        MovieSession gladiatorMovieSession = new MovieSession(
                LocalDateTime.of(2023, 11, 20, 17, 0),
                gladiator, bestCinemaHall);

        movieSessionService.add(fastAndFuriousMovieSessionFirst);
        movieSessionService.add(fastAndFuriousMovieSessionSecond);
        movieSessionService.add(gladiatorMovieSession);

        System.out.println(movieSessionService.get(fastAndFuriousMovieSessionFirst.getId()));

        System.out.println("Get all movieSessions: ");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()).forEach(System.out::println);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now().plusDays(1)).forEach(System.out::println);
        movieSessionService.findAvailableSessions(gladiator.getId(),
                LocalDate.now()).forEach(System.out::println);
    }

}
