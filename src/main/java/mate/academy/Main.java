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
        System.out.println("...Save movie to DB...");
        movieService.add(fastAndFurious);
        System.out.println("...Get movie from DB...");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("...Get all movies from DB...");
        movieService.getAll().forEach(System.out::println);

        CinemaHall planetaKino = new CinemaHall(300);
        planetaKino.setDescription("The first in Ukraine IMAX kinosal.");
        CinemaHall multiplex = new CinemaHall(150);
        multiplex.setDescription("The most common cinemas in Ukraine");
        System.out.println("...Save cinema hall to DB...");
        cinemaHallService.add(planetaKino);
        cinemaHallService.add(multiplex);
        System.out.println("...Get cinema hall from DB...");
        cinemaHallService.get(planetaKino.getId());
        System.out.println("...Get all cinema halls from DB...");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession fastAndFuriousInPlanetaKino = new MovieSession(
                fastAndFurious, planetaKino, LocalDateTime.of(
                        2021, 5, 15, 4,15));
        System.out.println("...Save movie session to DB...");
        movieSessionService.add(fastAndFuriousInPlanetaKino);
        System.out.println("...Get movie session from DB...");
        movieSessionService.get(fastAndFuriousInPlanetaKino.getId());
        System.out.println("...Get all movie session from DB...");
        movieSessionService.findAvailableMovieSessions(fastAndFuriousInPlanetaKino.getId(),
                LocalDate.of(2021, 5, 15))
                .forEach(System.out::println);
    }
}
