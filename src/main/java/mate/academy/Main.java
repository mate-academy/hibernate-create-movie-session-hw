package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        // Movies
        MovieService movieService = (
                MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        // Cinema Halls
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall imax = new CinemaHall();
        imax.setCapacity(150);
        imax.setDescription("IMAX with Laser");
        cinemaHallService.add(imax);
        cinemaHallService.getAll().forEach(System.out::println);

        // Movie Sessions
        LocalDateTime morning = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 00, 00));
        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(imax);
        movieSession1.setShowTime(morning);

        LocalDateTime evening = LocalDateTime.of(LocalDate.now(), LocalTime.of(19, 00, 00));
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setCinemaHall(imax);
        movieSession2.setShowTime(evening);

        LocalDateTime tomorrow =
                LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15, 00, 00));
        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(fastAndFurious);
        movieSession3.setCinemaHall(imax);
        movieSession3.setShowTime(tomorrow);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);

        System.out.println("Sessions for today: ");
        LocalDate today = LocalDate.now();
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),today));
    }
}
