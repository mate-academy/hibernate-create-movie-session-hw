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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie aceVentura = new Movie("Ace Ventura");
        aceVentura.setDescription("Comedy film with Jim Carrey");
        movieService.add(aceVentura);
        System.out.println(movieService.get(aceVentura.getId()));

        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHallOne = new CinemaHall(200);
        cinemaHallOne.setDescription("Cinema hall for comedy films");
        cinemaHallService.add(cinemaHallOne);
        System.out.println(cinemaHallService.get(cinemaHallOne.getId()));

        CinemaHall cinemaHallTwo = new CinemaHall(150);
        cinemaHallTwo.setDescription("Cinema hall for action films");
        cinemaHallService.add(cinemaHallTwo);
        System.out.println(cinemaHallService.get(cinemaHallTwo.getId()));

        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSessionOne = new MovieSession();
        movieSessionOne.setMovie(aceVentura);
        movieSessionOne.setCinemaHall(cinemaHallOne);
        movieSessionOne.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSessionOne);
        System.out.println(movieSessionService.get(movieSessionOne.getId()));

        MovieSession movieSessionTwo = new MovieSession();
        movieSessionTwo.setMovie(fastAndFurious);
        movieSessionTwo.setCinemaHall(cinemaHallTwo);
        movieSessionTwo.setShowTime(LocalDateTime.now().plusHours(4));
        movieSessionService.add(movieSessionTwo);
        System.out.println(movieSessionService.get(movieSessionTwo.getId()));

        movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
