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
    private static final int IMAX_HALL_CAPACITY = 90;
    private static final int THREE_D_HALL_CAPACITY = 70;

    public static void main(String[] args) {
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie matrix = new Movie("Matrix");
        matrix.setDescription("An action film about parallel reality");
        movieService.add(matrix);

        Movie terminator = new Movie("Terminator");
        terminator.setDescription("Film about good and bad robots, which comes from future");
        movieService.add(terminator);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall imax = new CinemaHall();
        imax.setDescription("IMAX cinema hall");
        imax.setCapacity(IMAX_HALL_CAPACITY);
        cinemaHallService.add(imax);

        CinemaHall threeD = new CinemaHall();
        threeD.setDescription("3d cinema hall");
        threeD.setCapacity(THREE_D_HALL_CAPACITY);
        cinemaHallService.add(threeD);
        System.out.println(cinemaHallService.get(threeD.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstSession = new MovieSession();
        firstSession.setMovie(fastAndFurious);
        firstSession.setCinemaHall(imax);
        firstSession.setShowTime(LocalDateTime.now().plusHours(1));
        movieSessionService.add(firstSession);

        MovieSession secondSession = new MovieSession();
        secondSession.setMovie(matrix);
        secondSession.setCinemaHall(imax);
        secondSession.setShowTime(LocalDateTime.now().plusHours(5));
        movieSessionService.add(secondSession);

        MovieSession thirdSession = new MovieSession();
        thirdSession.setMovie(terminator);
        thirdSession.setCinemaHall(threeD);
        thirdSession.setShowTime(LocalDateTime.now().plusHours(1));
        movieSessionService.add(thirdSession);

        System.out.println(movieSessionService.get(firstSession.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(matrix.getId(), LocalDate.now()));
    }
}
