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

        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        // Fast and Furious
        Movie fastAndFurious = new Movie("Fast and Furious 2");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie avengers = new Movie("Avengers");
        avengers.setDescription("Comics");
        movieService.add(avengers);

        Movie terminator = new Movie("Terminator");
        terminator.setDescription("A cyborg assassin called \"The Terminator\".");
        movieService.add(terminator);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall hallOne = new CinemaHall();
        hallOne.setDescription("Hall 1");
        hallOne.setCapacity(45);
        cinemaHallService.add(hallOne);

        CinemaHall hallTwo = new CinemaHall();
        hallTwo.setDescription("Hall 2");
        hallTwo.setCapacity(60);
        cinemaHallService.add(hallTwo);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(avengers);
        movieSession1.setCinemaHall(hallTwo);
        movieSession1.setShowTime(LocalDateTime.of(LocalDate.of(2022, 9, 1),
                LocalTime.of(15, 0)));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(terminator);
        movieSession2.setCinemaHall(hallOne);
        movieSession2.setShowTime(LocalDateTime.of(LocalDate.of(2022, 9, 1),
                LocalTime.of(19, 0)));
        movieSessionService.add(movieSession2);

        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(terminator);
        movieSession3.setCinemaHall(hallTwo);
        movieSession3.setShowTime(LocalDateTime.of(LocalDate.of(2022, 9, 1),
                LocalTime.of(14, 0))
        );
        movieSessionService.add(movieSession3);

        System.out.println();
        movieSessionService.findAvailableSessions(terminator.getId(),
                LocalDate.of(2022, 9, 1)).forEach(System.out::println);
    }
}
