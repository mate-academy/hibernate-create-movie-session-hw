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

        //Terminator
        Movie terminator = new Movie();
        terminator.setTitle("Terminator");
        terminator.setDescription("A cyborg assassin called \"The Terminator\".");

        CinemaHall hallTwo = new CinemaHall();
        hallTwo.setDescription("Hall 2");
        hallTwo.setCapacity(60);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(terminator);
        movieSession1.setCinemaHall(hallTwo);
        movieSession1.setShowTime(LocalDateTime.of(LocalDate.of(2022, 9, 1),
                LocalTime.of(15, 0)));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        Movie avengers = new Movie();
        avengers.setTitle("Avengers");
        avengers.setDescription("Comics");
        movieService.add(avengers);

        CinemaHall hallOne = new CinemaHall();
        hallOne.setDescription("Hall 1");
        hallOne.setCapacity(45);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(terminator);
        movieSession2.setCinemaHall(hallOne);
        movieSession2.setShowTime(LocalDateTime.of(LocalDate.of(2022, 9, 1),
                LocalTime.of(19, 0))
        );
        movieSessionService.add(movieSession2);

        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(terminator);
        movieSession3.setCinemaHall(hallTwo);
        movieSession3.setShowTime(LocalDateTime.of(LocalDate.of(2022, 9, 1),
                LocalTime.of(14, 0))
        );

        movieSessionService.add(movieSession3);
        System.out.println(System.lineSeparator()
                + movieSessionService.findAvailableSessions(terminator.getId(),
                LocalDate.of(2022, 9, 1)));

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        System.out.println(cinemaHallService.getAll());

        System.out.println(movieService.getAll());
    }
}
