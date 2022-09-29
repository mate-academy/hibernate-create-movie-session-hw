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
        final MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious",
                "An action film about street racing, heists, and spies.");
        Movie secondMovie = new Movie("Second Film", "Touristic film.");
        Movie thirdMovie = new Movie("Third Film", "Action.");
        movieService.add(fastAndFurious);
        movieService.add(secondMovie);
        movieService.add(thirdMovie);

        CinemaHall firstHall = new CinemaHall(50, "Red Hall");
        CinemaHall secondHall = new CinemaHall(100, "Blue Hall");
        CinemaHall thirdHall = new CinemaHall(500, "Green Hall");
        cinemaHallService.add(firstHall);
        cinemaHallService.add(secondHall);
        cinemaHallService.add(thirdHall);

        MovieSession firstSession = new MovieSession();
        firstSession.setCinemaHall(firstHall);
        firstSession.setMovie(fastAndFurious);
        firstSession.setShowTime(LocalDateTime.of(2022, 5, 12, 15, 15, 35));

        MovieSession secondSession = new MovieSession();
        secondSession.setCinemaHall(secondHall);
        secondSession.setMovie(secondMovie);
        secondSession.setShowTime(LocalDateTime.of(2022, 6, 5, 17, 30, 35));

        MovieSession thirdSession = new MovieSession();
        thirdSession.setCinemaHall(thirdHall);
        thirdSession.setMovie(thirdMovie);
        thirdSession.setShowTime(LocalDateTime.of(2022, 10, 17, 22, 30, 45));

        movieSessionService.add(firstSession);
        movieSessionService.add(secondSession);
        movieSessionService.add(thirdSession);

        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.of(2022, 5, 12)));
    }
}
