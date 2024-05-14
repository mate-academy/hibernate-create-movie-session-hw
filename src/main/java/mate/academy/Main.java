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

        Movie laLaLand = new Movie("La La Land");
        laLaLand.setDescription("An American drama film musical directed by Damien Chazelle.");

        movieService.add(fastAndFurious);
        movieService.add(laLaLand);

        CinemaHall bigHall = new CinemaHall();
        bigHall.setCapacity(50);
        bigHall.setDescription("A Big Hall");

        CinemaHall smallHall = new CinemaHall();
        smallHall.setCapacity(20);
        smallHall.setDescription("A Small Hall");

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        cinemaHallService.add(bigHall);
        cinemaHallService.add(smallHall);

        MovieSession firstSession = new MovieSession();
        firstSession.setCinemaHall(smallHall);
        firstSession.setMovie(fastAndFurious);
        firstSession.setLocalDateTime(LocalDateTime.now());

        MovieSession secondSession = new MovieSession();
        secondSession.setCinemaHall(bigHall);
        secondSession.setMovie(laLaLand);
        secondSession.setLocalDateTime(LocalDateTime.now().plusHours(3));

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        movieSessionService.add(firstSession);
        movieSessionService.add(secondSession);

        System.out.println(movieService.get(1L));
        System.out.println(movieService.getAll());

        System.out.println(cinemaHallService.get(2L));
        System.out.println(cinemaHallService.getAll());

        System.out.println(movieSessionService.get(1L));
        System.out.println(movieSessionService.findAvailableSessions(2L, LocalDate.now()));

    }
}
