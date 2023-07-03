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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription(
                "Screen: The hall has a large screen with a size of 8 meters diagonally.\n"
                        + "Popcorn: The hall has a stand where the audience"
                        + " can buy popcorn and drinks.");
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession session = new MovieSession();
        session.setMovie(fastAndFurious);
        session.setCinemaHall(cinemaHall);
        session.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(session);
        System.out.println(movieSessionService.get(session.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
