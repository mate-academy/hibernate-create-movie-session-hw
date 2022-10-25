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

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie movie2 = new Movie("Terminator 2");
        movie2.setDescription("An action film about robot's war against people. Part 2");
        Movie movie3 = new Movie("Terminator 3");
        movie3.setDescription("An action film about robot's war against people. Part 3");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(movie2);
        movieService.add(movie3);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        //********Cinema halls*********
        CinemaHall cinemaHall1 = new CinemaHall(50,"Hall 1");
        CinemaHall cinemaHall2 = new CinemaHall(100,"Hall 2");
        CinemaHall cinemaHall3 = new CinemaHall(150,"Hall 3");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall1);
        cinemaHallService.add(cinemaHall2);
        cinemaHallService.add(cinemaHall3);
        System.out.println(cinemaHallService.get(cinemaHall1.getId()));
        System.out.println(cinemaHallService.getAll());

        //********Movie sessions*********
        MovieSession movieSession1 =
                new MovieSession(fastAndFurious, cinemaHall1,
                        LocalDateTime.of(2022,10,25,11,0,0));
        MovieSession movieSession11 =
                new MovieSession(fastAndFurious, cinemaHall1,
                        LocalDateTime.of(2022,10,26,11,0,0));
        MovieSession movieSession12 =
                new MovieSession(fastAndFurious, cinemaHall1,
                        LocalDateTime.of(2022,10,27,11,0,0));
        MovieSession movieSession2 =
                new MovieSession(fastAndFurious, cinemaHall1,
                        LocalDateTime.of(2022,10,25,13,0,0));
        MovieSession movieSession3 =
                new MovieSession(fastAndFurious, cinemaHall1,
                        LocalDateTime.of(2022,10,25,15,0,0));
        MovieSession movieSession4 =
                new MovieSession(movie2, cinemaHall2,
                        LocalDateTime.of(2022,10,25,11,0,0));
        MovieSession movieSession5 =
                new MovieSession(movie2, cinemaHall2,
                        LocalDateTime.of(2022,10,26,11,0,0));
        MovieSession movieSession6 =
                new MovieSession(movie3, cinemaHall3,
                        LocalDateTime.of(2022,10,25,11,0,0));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession11);
        movieSessionService.add(movieSession12);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);
        movieSessionService.add(movieSession4);
        movieSessionService.add(movieSession5);
        movieSessionService.add(movieSession6);
        System.out.println(movieSessionService.get(movieSession1.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(1L,
                        LocalDate.of(2022, 10, 25)));
    }
}
