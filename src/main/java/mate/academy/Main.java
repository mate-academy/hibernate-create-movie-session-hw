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
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        initializeMovies(movieService);
        initializeCinemaHalls(cinemaHallService);
        initializeMovieSessions(cinemaHallService, movieService, movieSessionService);

        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.now()));
    }

    private static void initializeMovies(MovieService movieService) {
        final Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        final Movie fastAndFurious2 = new Movie("Fast and Furious2");
        fastAndFurious.setDescription("An action film about more street racing,"
                + " heists, and spies.");
        movieService.add(fastAndFurious2);

        final Movie fastAndFurious3 = new Movie("Fast and Furious3");
        fastAndFurious.setDescription("An action film about more street racing,"
                + " more heists, and spies.");
        movieService.add(fastAndFurious3);
    }

    private static void initializeCinemaHalls(CinemaHallService cinemaHallService) {
        final CinemaHall normalHall = new CinemaHall();
        normalHall.setCapacity(100);
        normalHall.setDescription("regular hall");
        cinemaHallService.add(normalHall);

        final CinemaHall vipHall = new CinemaHall();
        vipHall.setCapacity(20);
        vipHall.setDescription("VIP hall");
        cinemaHallService.add(vipHall);
    }

    private static void initializeMovieSessions(CinemaHallService cinemaHallService,
                                                MovieService movieService,
                                                MovieSessionService movieSessionService) {

        MovieSession pastShowingPart2 = new MovieSession();
        pastShowingPart2.setCinemaHall(cinemaHallService.get(1L));
        pastShowingPart2.setMovie(movieService.get(1L));
        pastShowingPart2.setShowTime(LocalDateTime.now().minusDays(1));
        movieSessionService.add(pastShowingPart2);

        MovieSession regularShowingPart2 = new MovieSession();
        regularShowingPart2.setCinemaHall(cinemaHallService.get(1L));
        regularShowingPart2.setMovie(movieService.get(1L));
        regularShowingPart2.setShowTime(LocalDateTime.now());
        movieSessionService.add(regularShowingPart2);

        MovieSession vipShowingPart2 = new MovieSession();
        vipShowingPart2.setCinemaHall(cinemaHallService.get(2L));
        vipShowingPart2.setMovie(movieService.get(2L));
        vipShowingPart2.setShowTime(LocalDateTime.now().plusHours(3));
        movieSessionService.add(vipShowingPart2);

        MovieSession futureShowingPart2 = new MovieSession();
        futureShowingPart2.setCinemaHall(cinemaHallService.get(2L));
        futureShowingPart2.setMovie(movieService.get(1L));
        futureShowingPart2.setShowTime(LocalDateTime.now().plusDays(1));
        movieSessionService.add(futureShowingPart2);

        MovieSession futureVipShowingPart3 = new MovieSession();
        futureVipShowingPart3.setCinemaHall(cinemaHallService.get(2L));
        futureVipShowingPart3.setMovie(movieService.get(1L));
        futureVipShowingPart3.setShowTime(LocalDateTime.now().plusDays(1));
        movieSessionService.add(futureVipShowingPart3);
    }
}
