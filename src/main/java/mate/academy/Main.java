package mate.academy;

import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import mate.academy.service.impl.CinemaHallServiceImpl;
import mate.academy.service.impl.MovieServiceImpl;
import mate.academy.service.impl.MovieSessionServiceImpl;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        MovieService movieService = (MovieServiceImpl) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("Find movie by id:");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("Find all movies:");
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallServiceImpl) injector.getInstance(CinemaHallService.class);
        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(250);
        firstHall.setDescription("This is the first hall of theatre");
        cinemaHallService.add(firstHall);
        System.out.println("Find movie hall by id:");
        System.out.println(cinemaHallService.get(firstHall.getId()));
        System.out.println("Find all movie halls:");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession fastAndFuriousFirstSession = new MovieSession();
        fastAndFuriousFirstSession.setMovie(fastAndFurious);
        fastAndFuriousFirstSession.setCinemaHall(firstHall);
        LocalDateTime now = LocalDateTime.now();
        fastAndFuriousFirstSession.setShowTime(now);
        MovieSessionService movieSessionService =
                (MovieSessionServiceImpl) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousFirstSession);
        System.out.println("Find movie session by id:");
        System.out.println(movieSessionService.get(fastAndFuriousFirstSession.getId()));
        System.out.println("Find movie session by id and date:");
        System.out.println(movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), now.toLocalDate()));
    }
}
