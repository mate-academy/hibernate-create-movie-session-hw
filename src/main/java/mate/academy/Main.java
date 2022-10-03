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
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie interstellar = new Movie("Interstellar");
        interstellar.setDescription("Set in a dystopian future where "
                + "humanity is struggling to survive, "
                + "the film follows a group of astronauts who travel "
                + "through a wormhole near Saturn "
                + "in search of a new home for mankind.");
        movieService.add(fastAndFurious);
        movieService.add(interstellar);
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(100);
        firstHall.setDescription("Imax hall");
        CinemaHall secondHall = new CinemaHall();
        secondHall.setCapacity(50);
        secondHall.setDescription("3D hall");
        CinemaHall thirdHall = new CinemaHall();
        thirdHall.setCapacity(40);
        thirdHall.setDescription("2D hall");

        cinemaHallService.add(firstHall);
        cinemaHallService.add(secondHall);
        cinemaHallService.add(thirdHall);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession morningSession = new MovieSession();
        morningSession.setCinemaHall(thirdHall);
        morningSession.setMovie(fastAndFurious);
        morningSession.setShowTime(LocalDateTime.of(2022, 8,
                11, 9, 0));
        MovieSession daySession = new MovieSession();
        daySession.setCinemaHall(secondHall);
        daySession.setMovie(interstellar);
        daySession.setShowTime(LocalDateTime.of(2022, 9,
                11, 14, 0));
        MovieSession eveningSession = new MovieSession();
        eveningSession.setCinemaHall(firstHall);
        eveningSession.setMovie(interstellar);
        eveningSession.setShowTime(LocalDateTime.of(2022, 9,
                11, 18, 0));

        movieSessionService.add(morningSession);
        movieSessionService.add(daySession);
        movieSessionService.add(eveningSession);

        System.out.println(movieSessionService.get(daySession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(interstellar.getId(),
                LocalDate.now()));
    }
}
