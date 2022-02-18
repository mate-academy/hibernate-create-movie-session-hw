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

        Movie fastAndFurious2 = new Movie("Fast and Furious2");
        fastAndFurious2
                .setDescription("Second action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious2);

        System.out.println(movieService.get(fastAndFurious2.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall hallOne = new CinemaHall();
        hallOne.setDescription("Biggest hall in the world");
        hallOne.setCapacity(999);

        CinemaHall hallTwo = new CinemaHall();
        hallTwo.setDescription("Smallest hall in the world");
        hallTwo.setCapacity(1);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        cinemaHallService.add(hallOne);
        cinemaHallService.add(hallTwo);

        System.out.println(cinemaHallService.get(hallTwo.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession daySession = new MovieSession();
        daySession.setCinemaHall(hallOne);
        daySession.setMovie(fastAndFurious);
        daySession.setShowTime(LocalDateTime.now());

        MovieSession tomorrowSession = new MovieSession();
        tomorrowSession.setCinemaHall(hallTwo);
        tomorrowSession.setMovie(fastAndFurious2);
        tomorrowSession.setShowTime(LocalDateTime.now().plusDays(2));

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        movieSessionService.add(daySession);
        movieSessionService.add(tomorrowSession);

        movieSessionService.get(fastAndFurious.getId());
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now()));
    }
}
