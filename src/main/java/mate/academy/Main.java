package mate.academy;

import java.time.LocalDate;
import java.util.List;
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
        // Tworzymy serwisy przez wstrzykiwacz
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        // --- Test MovieService ---
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie inception = new Movie("Inception");
        inception.setDescription("A mind-bending thriller about dreams within dreams.");
        movieService.add(inception);

        System.out.println("All movies:");
        movieService.getAll().forEach(System.out::println);

        System.out.println("Get movie by ID:");
        System.out.println(movieService.get(fastAndFurious.getId()));

        // --- Test CinemaHallService ---
        CinemaHall hall1 = new CinemaHall();
        hall1.setCapacity(100);
        hall1.setDescription("Main Hall");
        cinemaHallService.add(hall1);

        CinemaHall hall2 = new CinemaHall();
        hall2.setCapacity(50);
        hall2.setDescription("VIP Hall");
        cinemaHallService.add(hall2);

        System.out.println("All cinema halls:");
        cinemaHallService.getAll().forEach(System.out::println);

        // --- Test MovieSessionService ---
        MovieSession session1 = new MovieSession();
        session1.setMovie(fastAndFurious);
        session1.setCinemaHall(hall1);
        session1.setShowTime(LocalDate.now().plusDays(1).atStartOfDay());
        movieSessionService.add(session1);

        MovieSession session2 = new MovieSession();
        session2.setMovie(inception);
        session2.setCinemaHall(hall2);
        session2.setShowTime(LocalDate.now().plusDays(2).atStartOfDay());
        movieSessionService.add(session2);

        System.out.println("Available sessions for 'Fast and Furious':");
        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(
                fastAndFurious.getId(),
                LocalDate.now().plusDays(1));
        availableSessions.forEach(System.out::println);

        System.out.println("Get cinema hall by ID:");
        CinemaHall retrievedHall = cinemaHallService.get(hall1.getId());
        System.out.println(retrievedHall);

        // --- Test MovieSessionService get(Long id) ---
        System.out.println("Get movie session by ID:");
        MovieSession retrievedSession = movieSessionService.get(session1.getId());
        System.out.println(retrievedSession);
    }
}

