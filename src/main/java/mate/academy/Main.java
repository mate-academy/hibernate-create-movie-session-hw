package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie terminator2 = new Movie("Treminator 2");
        terminator2.setDescription("An action film about fighting between maschines and humans.");
        movieService.add(terminator2);

        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHallOne = new CinemaHall();
        cinemaHallOne.setCapacity(100);
        cinemaHallOne.setDescription("Cinema hall number one have capacity 100 seats");
        cinemaHallService.add(cinemaHallOne);
        System.out.println(cinemaHallService.get(cinemaHallOne.getId()));

        CinemaHall cinemaHallTwo = new CinemaHall();
        cinemaHallTwo.setCapacity((200));
        cinemaHallTwo.setDescription("Cinema hall number two have capacity 200 seats");
        cinemaHallService.add(cinemaHallTwo);

        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(terminator2);
        movieSession1.setCinemaHall(cinemaHallOne);
        movieSession1.setShowTime(LocalDateTime.of(2022,03,31,19,00));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        System.out.println(movieSessionService.get(movieSession1.getId()));

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(terminator2);
        movieSession2.setCinemaHall(cinemaHallTwo);
        movieSession2.setShowTime(LocalDateTime.of(2022,03,31,21,00));
        movieSessionService.add(movieSession2);
        System.out.println(movieSessionService.get(movieSession2.getId()));

        List<MovieSession> availableSessions =
                movieSessionService.findAvailableSessions(terminator2.getId(),
                        LocalDate.of(2022, 03, 31));
        System.out.println(availableSessions);

    }
}
