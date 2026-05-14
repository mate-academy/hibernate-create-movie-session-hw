package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionServive;
import mate.academy.service.impl.CinemaHallServiceImpl;
import mate.academy.service.impl.MovieServiceImpl;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        MovieService movieService = (MovieServiceImpl) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallServiceImpl) injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(50);
        cinemaHall.setDescription("Cinema Hall 1");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionServive movieSessionServive = (MovieSessionServive) injector.getInstance(MovieSessionServive.class);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionServive.add(movieSession);
        System.out.println(movieSessionServive.get(movieSession.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
    }
}
