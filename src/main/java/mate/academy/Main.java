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

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        Movie interstellar = new Movie("Interstellar");
        interstellar.setDescription("Movie about space");

        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);

        System.out.println("=================Movies=================");
        movieService.add(fastAndFurious);
        movieService.add(interstellar);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall largeCinemaHall = new CinemaHall();
        largeCinemaHall.setCapacity(200);
        largeCinemaHall.setDescription("Large hall");

        CinemaHall smallCinemaHall = new CinemaHall();
        smallCinemaHall.setCapacity(30);
        smallCinemaHall.setDescription("Small hall");

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        System.out.println("=================Cinema halls=================");
        cinemaHallService.add(largeCinemaHall);
        cinemaHallService.add(smallCinemaHall);

        System.out.println(cinemaHallService.get(largeCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstSession = new MovieSession();
        firstSession.setMovie(fastAndFurious);
        firstSession.setCinemaHall(largeCinemaHall);
        firstSession.setShowTime(LocalDateTime.now());

        MovieSession secondSession = new MovieSession();
        secondSession.setMovie(interstellar);
        secondSession.setCinemaHall(smallCinemaHall);
        secondSession.setShowTime(LocalDateTime.now().plusDays(5));

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        System.out.println("=================Movie Sessions=================");
        movieSessionService.add(firstSession);
        movieSessionService.add(secondSession);

        System.out.println(movieSessionService.get(firstSession.getId()));
        movieSessionService.findAvailableSessions(
                interstellar.getId(),
                LocalDate.now().plusDays(5)
        ).forEach(System.out::println);
    }
}
