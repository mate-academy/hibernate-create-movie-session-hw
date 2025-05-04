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
    private static final Injector INJECTOR = Injector.getInstance("mate");
    private static MovieService movieService = (MovieService)
            INJECTOR.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService = (CinemaHallService)
            INJECTOR.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService = (MovieSessionService)
            INJECTOR.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        System.out.println("_______________________");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie hobbit = new Movie();
        hobbit.setTitle("Hobbit");
        hobbit.setDescription("Film about hobbit.");
        System.out.println("_______________________");
        movieService.add(hobbit);
        System.out.println(movieService.get(hobbit.getId()));
        System.out.println("_______________________");
        movieService.getAll().forEach(System.out::println);

        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(32);
        redHall.setDescription("This is a red hall");
        System.out.println("_______________________");
        cinemaHallService.add(redHall);
        System.out.println(cinemaHallService.get(redHall.getId()));

        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(32);
        blueHall.setDescription("This is a blue hall");
        System.out.println("_______________________");
        cinemaHallService.add(blueHall);
        System.out.println(cinemaHallService.get(blueHall.getId()));
        System.out.println("_______________________");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession hobbitSession = new MovieSession();
        hobbitSession.setMovie(hobbit);
        hobbitSession.setCinemaHall(redHall);
        hobbitSession.setShowTime(LocalDateTime.now());
        System.out.println("_______________________");
        movieSessionService.add(hobbitSession);
        System.out.println(movieSessionService.get(hobbit.getId()));
        System.out.println("_______________________");
        movieSessionService.findAvailableSessions(hobbit.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
