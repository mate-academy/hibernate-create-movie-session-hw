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

    public static void main(String[] args) {
        MovieService movieService = (MovieService)INJECTOR.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall olimpic = new CinemaHall();
        olimpic.setCapacity(50);
        olimpic.setDescription("Cinema hall Olimpic has capacity 50");

        CinemaHall supershow = new CinemaHall();
        supershow.setCapacity(100);
        supershow.setDescription("Cinema hall Supershow has capacity 100");

        CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(olimpic);
        cinemaHallService.add(supershow);
        System.out.println(cinemaHallService.get(olimpic.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSession decemberMovieSession = new MovieSession();
        decemberMovieSession.setCinemaHall(olimpic);
        decemberMovieSession.setMovie(fastAndFurious);
        decemberMovieSession.setShowTime(LocalDateTime.now().plusDays(1L));

        MovieSession novemberMovieSession = new MovieSession();
        novemberMovieSession.setCinemaHall(olimpic);
        novemberMovieSession.setMovie(fastAndFurious);
        novemberMovieSession.setShowTime(LocalDateTime.now().plusMonths(1L));
        MovieSessionService movieSessionService = (MovieSessionService) INJECTOR
                .getInstance(MovieSessionService.class);
        movieSessionService.add(decemberMovieSession);
        movieSessionService.add(novemberMovieSession);
        movieSessionService.get(novemberMovieSession.getId());
        movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.now());
    }
}
