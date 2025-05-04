package mate.academy;

import java.time.LocalDate;
import java.util.List;
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

        //add movie
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        Movie harryPotter = new Movie("Harry Potter");
        harryPotter.setDescription("The chronicle the lives of a young wizard, Harry Potter");
        movieService.add(harryPotter);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        //add two different cinema halls
        CinemaHall hall2d = new CinemaHall();
        hall2d.setCapacity(50);
        hall2d.setDescription("Traditional two-dimensional movies");
        CinemaHall hall3d = new CinemaHall();
        hall3d.setCapacity(80);
        hall3d.setDescription("Three-dimensional movies, giving "
                + "life-like experience to the audience");
        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(hall2d);
        cinemaHallService.add(hall3d);

        //add movie session
        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(hall2d);
        movieSession1.setShowTime(LocalDate.of(2024,05,25));

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(harryPotter);
        movieSession2.setCinemaHall(hall3d);
        movieSession2.setShowTime(LocalDate.of(2024,05,29));

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);

        List<MovieSession> listOfMovies = movieSessionService
                .findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2024,05,25));
        System.out.println("Session in may 25: " + listOfMovies);

        List<MovieSession> listOfMovies1 = movieSessionService
                .findAvailableSessions(harryPotter.getId(),
                LocalDate.of(2024,05,29));
        System.out.println("Session in may 29: " + listOfMovies1);
    }
}
