package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie taxi = new Movie();
        taxi.setTitle("Taxi");
        taxi.setDescription("Real \"Fast and Furious\"");

        CinemaHall fakel = new CinemaHall();
        fakel.setCapacity(25);
        fakel.setDescription("Your worst experience");
        CinemaHall imax = new CinemaHall();
        imax.setCapacity(150);
        imax.setDescription("The largest cinema hall ever");

        MovieService movieService = (MovieService)
                injector.getInstance(MovieService.class);
        System.out.println(movieService.add(fastAndFurious));
        System.out.println(movieService.add(taxi));
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        System.out.println(cinemaHallService.add(fakel));
        System.out.println(cinemaHallService.add(imax));
        System.out.println(cinemaHallService.get(fakel.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession session = new MovieSession();
        session.setMovie(fastAndFurious);
        session.setCinemaHall(imax);
        session.setShowTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)));

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        System.out.println(movieSessionService.add(session));
        session.setMovie(taxi);
        session.setShowTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 0)));
        System.out.println(movieSessionService.add(session));
        session.setCinemaHall(fakel);
        System.out.println(movieSessionService.add(session));
        System.out.println(movieSessionService.get(session.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now()));
    }
}
