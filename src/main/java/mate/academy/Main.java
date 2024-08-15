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
    private static final int CAPACITY = 100;

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService
                = (MovieService) INJECTOR.getInstance(MovieService.class);
        System.out.println("Add movie: " + movieService.add(fastAndFurious));
        System.out.println("Get movie: " + movieService.get(fastAndFurious.getId()));
        System.out.println("Get all movies: ");
        movieService.getAll().forEach(System.out::println);

        CinemaHall bigCinemaHall = new CinemaHall();
        bigCinemaHall.setCapacity(CAPACITY);
        bigCinemaHall.setDescription("Biggest cinema hall");
        CinemaHallService cinemaHallService
                = (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
        System.out.println("Add cinema hall: " + cinemaHallService.add(bigCinemaHall));
        System.out.println("Get cinema hall: " + cinemaHallService.get(bigCinemaHall.getId()));
        System.out.println("Get all cinema halls: ");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(bigCinemaHall);
        movieSession.setShowTime(LocalDateTime.of(2024, 8, 20, 12, 0));
        MovieSessionService movieSessionService
                = (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);
        System.out.println("Add movie session: " + movieSessionService.add(movieSession));
        System.out.println("Get movie session: " + movieSessionService.get(movieSession.getId()));
        System.out.println("Get all available movie session at " + LocalDate.of(2024, 8, 20));
        movieSessionService
                .findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.of(2024, 8, 20))
                .forEach(System.out::println);
    }
}
