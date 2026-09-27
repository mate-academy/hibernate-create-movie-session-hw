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
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall moviePark = new CinemaHall();
        moviePark.setDescription("A screen set up in a park for public view");
        moviePark.setCapacity(300);
        cinemaHallService.add(moviePark);

        CinemaHall helios = new CinemaHall();
        helios.setDescription("Popular local cinema chain");
        helios.setCapacity(500);
        cinemaHallService.add(helios);

        System.out.println("TEST 1: get cinema hall by id: "
                + cinemaHallService.get(helios.getId()));
        System.out.println("TEST 2: get all cinema halls: ");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("TEST 2 concluded");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("TEST 3: get movie by id: " + movieService.get(fastAndFurious.getId()));
        System.out.println("TEST 4: get all movies: ");
        movieService.getAll().forEach(System.out::println);
        System.out.println("TEST 4 concluded");

        LocalDateTime showTime = LocalDateTime.parse("2020-01-23T13:45:02");
        MovieSession screening = new MovieSession();
        screening.setCinemaHall(helios);
        screening.setMovie(fastAndFurious);
        screening.setShowTime(showTime);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(screening);
        System.out.println("TEST 5: get movie session by id: "
                + movieSessionService.get(screening.getId()));
        LocalDate date = LocalDate.parse("2020-01-23");
        System.out.println("TEST 6: get session available for a movie and input date: "
                + movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), date));

    }
}
