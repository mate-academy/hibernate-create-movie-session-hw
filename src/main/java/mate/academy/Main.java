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
    private static final Injector
            injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService = (MovieService)
            injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService)
            injector.getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService)
            injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie cocaineBear = new Movie("Cocaine Bear");
        cocaineBear.setDescription("Comedy horror film.");
        movieService.add(cocaineBear);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall superLux = new CinemaHall();
        superLux.setDescription("Increased comfort");
        superLux.setCapacity(20);
        cinemaHallService.add(superLux);

        CinemaHall screenX = new CinemaHall();
        screenX.setDescription("Panoramic screen");
        screenX.setCapacity(65);
        cinemaHallService.add(screenX);

        System.out.println(cinemaHallService.get(superLux.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession weekendMovieSession = new MovieSession();
        weekendMovieSession.setMovie(fastAndFurious);
        weekendMovieSession.setCinemaHall(superLux);
        weekendMovieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(weekendMovieSession);

        MovieSession workingDaysMovieSession = new MovieSession();
        workingDaysMovieSession.setMovie(cocaineBear);
        workingDaysMovieSession.setCinemaHall(screenX);
        workingDaysMovieSession.setShowTime(LocalDateTime.now().plusDays(2));
        movieSessionService.add(workingDaysMovieSession);

        System.out.println(movieSessionService.get(weekendMovieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.from(LocalDateTime.now()));
    }
}
