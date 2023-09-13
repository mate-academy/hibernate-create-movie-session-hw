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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static final MovieService movieService = (MovieService) INJECTOR
            .getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService) INJECTOR
            .getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR
            .getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie varta = new Movie("Varta");
        varta.setDescription("Documentary");
        movieService.add(varta);
        System.out.println(movieService.get(varta.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(20);
        cinemaHall.setDescription("Small hall");
        cinemaHallService.add(cinemaHall);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieService.get(3L));
        movieSession.setShowTime(LocalDateTime.now());
        movieSession.setCinemaHall(cinemaHall);
        movieSessionService.add(movieSession);

        LocalDate localDate = LocalDate.now().plusDays(2);
        System.out.println(movieSessionService.findAvailableSessions(3L, localDate));
    }
}
