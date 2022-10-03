package mate.academy;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
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
        System.out.println("Movies");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie titanic = new Movie("Titanic");
        titanic.setDescription("Masterpiece");
        movieService.add(titanic);
        System.out.println(movieService.get(titanic.getId()));

        Movie autumnInNewYork = new Movie("Autumn in New York");
        autumnInNewYork.setDescription("Romantic drama");
        movieService.add(autumnInNewYork);
        System.out.println(movieService.get(autumnInNewYork.getId()));

        movieService.getAll().forEach(System.out::println);

        System.out.println("Cinema Halls");
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setCapacity(100);
        cinemaHall1.setDescription("first floor");
        cinemaHallService.add(cinemaHall1);
        System.out.println(cinemaHallService.get(cinemaHall1.getId()));

        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall2.setCapacity(200);
        cinemaHall2.setDescription("second floor");
        cinemaHallService.add(cinemaHall2);
        System.out.println(cinemaHallService.get(cinemaHall2.getId()));

        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("Movie Sessions");

        MovieSession movieSession1 = new MovieSession();
        LocalDateTime time1 = LocalDateTime.of(2022, Month.OCTOBER, 10, 10, 0, 0);
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(cinemaHall1);
        movieSession1.setShowTime(time1);

        MovieSession movieSession2 = new MovieSession();
        LocalDateTime time2 = LocalDateTime.of(2022, Month.OCTOBER, 12, 10, 0, 0);
        movieSession2.setMovie(titanic);
        movieSession2.setCinemaHall(cinemaHall2);
        movieSession2.setShowTime(time2);

        MovieSession movieSession3 = new MovieSession();
        LocalDateTime time3 = LocalDateTime.of(2022, Month.OCTOBER, 14, 10, 0, 0);
        movieSession3.setMovie(autumnInNewYork);
        movieSession3.setCinemaHall(cinemaHall1);
        movieSession3.setShowTime(time3);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        movieSessionService.add(movieSession1);
        System.out.println(movieSessionService.get(movieSession1.getId()));

        movieSessionService.add(movieSession2);
        System.out.println(movieSessionService.get(movieSession2.getId()));

        movieSessionService.add(movieSession3);
        System.out.println(movieSessionService.get(movieSession3.getId()));

        System.out.println("Find available sessions");
        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(autumnInNewYork.getId(), time3.toLocalDate());
        availableSessions.forEach(System.out::println);
    }
}
