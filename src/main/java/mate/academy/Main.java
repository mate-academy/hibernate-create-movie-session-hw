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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie harryPotter = new Movie("Harry Potter");
        harryPotter.setDescription("Great film of the fantasy genre about magic");
        movieService.add(fastAndFurious);
        movieService.add(harryPotter);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall basicHall = new CinemaHall();
        basicHall.setCapacity(100);
        basicHall.setDescription("Basic cinema hall");
        CinemaHall largeHall = new CinemaHall();
        largeHall.setCapacity(300);
        largeHall.setDescription("Cinema hall with big capacity");
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(basicHall);
        cinemaHallService.add(largeHall);
        System.out.println(cinemaHallService.get(largeHall.getId()));
        System.out.println(cinemaHallService.getAll());
        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(basicHall);
        fastAndFuriousSession.setShowTime(LocalDateTime.now());
        MovieSession harryPotterSession = new MovieSession();
        harryPotterSession.setMovie(harryPotter);
        harryPotterSession.setCinemaHall(largeHall);
        harryPotterSession.setShowTime(LocalDateTime.now().plusDays(15));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSession);
        movieSessionService.add(harryPotterSession);
        movieSessionService.get(fastAndFuriousSession.getId());
        movieSessionService.findAvailableSessions(harryPotterSession.getId(), LocalDate.now());
    }
}
