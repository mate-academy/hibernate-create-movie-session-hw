package mate.academy;

import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    static final Injector injector = Injector.getInstance("mate.academy");
    static final MovieSessionService movieSessionService = (MovieSessionService)
            injector.getInstance(MovieSessionService.class);
    static final MovieService movieService = (MovieService)
            injector.getInstance(MovieService.class);
    static final CinemaHallService cinemaHallService = (CinemaHallService)
            injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious",
                "An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        Movie movie = new Movie("Its a movie", "boring");
        movieService.add(movie);
        Movie movieNotBoring = new Movie("Movie!1!1!", "not boring");
        System.out.println("------------------------");
        CinemaHall cinemaHall1 = new CinemaHall(60, "really big one");
        CinemaHall cinemaHallVip = new CinemaHall(5, "VIP");
        cinemaHallService.add(cinemaHall1);
        cinemaHallService.add(cinemaHallVip);
        cinemaHallService.get(cinemaHall1.getId());
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("------------------------");
        LocalDateTime now = LocalDateTime.now();
        MovieSession movieSession = new MovieSession(fastAndFurious, cinemaHall1, now);
        MovieSession movieSession2 = new MovieSession(movie, cinemaHallVip, now);
        movieSessionService.add(movieSession);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                now.toLocalDate()).forEach(System.out::println);
        movieSessionService.get(movieSession.getId());
    }
}
