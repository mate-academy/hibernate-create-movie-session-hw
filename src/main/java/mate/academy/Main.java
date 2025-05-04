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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie inception = new Movie("Inception");
        inception.setDescription("A science fiction action film about stealing information by "
                + "entering the subconscious.");
        movieService.add(inception);

        // Adding cinema halls
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall largeHall = new CinemaHall();
        largeHall.setCapacity(70);
        largeHall.setDescription("Large Hall");
        cinemaHallService.add(largeHall);

        CinemaHall smallHall = new CinemaHall();
        smallHall.setCapacity(30);
        smallHall.setDescription("Small Hall");
        cinemaHallService.add(smallHall);

        // Adding movie sessions
        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(largeHall);
        LocalDateTime now = LocalDateTime.now();
        movieSession1.setShowTime(now);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(inception);
        movieSession2.setCinemaHall(smallHall);
        LocalDateTime later = now.plusDays(1);
        movieSession2.setShowTime(later);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);

        // Printing all movies, cinema halls, and movie sessions
        System.out.println("All Movies:");
        movieService.getAll().forEach(System.out::println);

        System.out.println("\nAll Cinema Halls:");
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("\nAll Movie Sessions:");
        movieSessionService.findAvailableSession(
                fastAndFurious.getId(), LocalDate.now()).forEach(System.out::println);
    }
}
