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
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie lordOfTheRings = new Movie("Lord of the Ring");
        lordOfTheRings.setDescription("fantasy movie about fight between good and evil");
        movieService.add(lordOfTheRings);

        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(150);
        blueHall.setDescription("Standard cinema hall");
        cinemaHallService.add(blueHall);

        CinemaHall imax = new CinemaHall();
        imax.setCapacity(200);
        imax.setDescription("MEGA sound, big screen, 3D");
        cinemaHallService.add(imax);

        MovieSession blueHallSession = new MovieSession();
        blueHallSession.setMovie(lordOfTheRings);
        blueHallSession.setCinemaHall(blueHall);
        blueHallSession.setShowTime(LocalDateTime.now());

        MovieSession imaxSession = new MovieSession();
        imaxSession.setMovie(fastAndFurious);
        imaxSession.setCinemaHall(imax);
        imaxSession.setShowTime(LocalDateTime.now());

        movieSessionService.add(blueHallSession);
        movieSessionService.add(imaxSession);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now());
        System.out.println(movieService.getAll());
        System.out.println(cinemaHallService.getAll());
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now()));
    }
}
