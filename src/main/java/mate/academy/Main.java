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
        Movie pirates = new Movie("Pirates of the Caribbean");
        pirates.setTitle("American fantasy supernatural swashbuckler film");
        movieService.add(pirates);
        Movie potter = new Movie("Harry Potter");
        potter.setTitle("fantasy film");
        movieService.add(potter);
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall black = new CinemaHall(120, "black hall");
        cinemaHallService.add(black);
        System.out.println(cinemaHallService.get(black.getId()));
        CinemaHall purple = new CinemaHall(90, "purple hall");
        cinemaHallService.add(purple);
        CinemaHall pink = new CinemaHall(70, "pink hall");
        cinemaHallService.add(pink);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession yesterday = new MovieSession(fastAndFurious, pink, LocalDateTime.of(
                2023, 2, 14, 15, 40));
        movieSessionService.add(yesterday);
        movieSessionService.get(yesterday.getId());
        MovieSession morning = new MovieSession(potter, purple, LocalDateTime.of(
                2023, 2, 15, 11, 20));
        movieSessionService.add(morning);
        MovieSession night = new MovieSession(fastAndFurious, purple, LocalDateTime.of(
                2023, 2, 15, 21, 50));
        movieSessionService.add(night);
        MovieSession evening = new MovieSession(pirates, black, LocalDateTime.of(
                2023, 2, 15, 18, 30));
        movieSessionService.add(evening);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.of(
                2023, 2, 15)).forEach(System.out::println);
    }
}
