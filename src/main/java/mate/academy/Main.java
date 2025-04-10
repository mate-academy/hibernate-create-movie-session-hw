package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.Movie;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static void main(String[] args) {

        final MovieService movieService = (MovieService) Injector
                .getInstance("mate.academy")
                .getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        Movie ironMan = new Movie("Iron Man");
        ironMan.setDescription("Film about rich man who become superhero");
        movieService.add(ironMan);
        System.out.println(movieService.get(ironMan.getId()));
        movieService.getAll().forEach(System.out::println);

        //CinemaHallService add, get, getAll
        final CinemaHallService cinemaHallService = (CinemaHallService) Injector
                .getInstance("mate.academy")
                .getInstance(CinemaHallService.class);

        System.out.println("=====Cinema Hall=====");

        CinemaHall one = new CinemaHall();
        one.setCapacity(80);
        one.setDescription("cinema hall number one");

        cinemaHallService.add(one);

        CinemaHall two = new CinemaHall();
        two.setCapacity(40);
        two.setDescription("cinema hall number two");

        cinemaHallService.add(two);

        System.out.println("=======================");

        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("=======================");

        System.out.println(cinemaHallService.get(one.getId()));

        final MovieSessionService movieSessionService = (MovieSessionService) Injector
                .getInstance("mate.academy")
                .getInstance(MovieSessionService.class);

        System.out.println("=====Movie Session=====");

        final LocalDateTime filmOne = LocalDateTime.of(2025, 4,10,20,30);
        final LocalDateTime filmTwo = LocalDateTime.of(2025, 4,10,22,30);
        final LocalDate findFilm = LocalDate.of(2025, 4,10);

        MovieSession movieSessionOne = new MovieSession();
        movieSessionOne.setMovie(fastAndFurious);
        movieSessionOne.setCinemaHall(one);
        movieSessionOne.setShowTime(filmOne);

        movieSessionService.add(movieSessionOne);

        MovieSession movieSessionTwo = new MovieSession();
        movieSessionTwo.setMovie(ironMan);
        movieSessionTwo.setCinemaHall(two);
        movieSessionTwo.setShowTime(filmTwo);

        movieSessionService.add(movieSessionTwo);

        System.out.println("=========================");

        System.out.println(movieSessionService.get(movieSessionOne.getId()));

        System.out.println("=========================");

        movieSessionService
                .findAvailableSessions(one.getId(), findFilm)
                .forEach(System.out::println);
    }
}
