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
        Injector injector = Injector.getInstance("mate");

        Movie movieHatiko = new Movie("Hatiko");
        Movie movieIt = new Movie("It");
        Movie movieIt2 = new Movie("It 2");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(movieHatiko);
        movieService.add(movieIt);
        movieService.add(movieIt2);
        movieService.get(2L);
        movieService.getAll();

        CinemaHall cinemaHallWithImax = new CinemaHall();
        cinemaHallWithImax.setDescription("IMAX");
        CinemaHall basicCinemaHall = new CinemaHall();
        basicCinemaHall.setDescription("Normal cinema hall");
        CinemaHall cinemaHallWith3D = new CinemaHall();
        cinemaHallWith3D.setDescription("3D included");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHallWithImax);
        cinemaHallService.add(basicCinemaHall);
        cinemaHallService.add(cinemaHallWith3D);
        cinemaHallService.get(3L);
        cinemaHallService.getAll();

        MovieSession movieSessionWithCurrentShowTime = new MovieSession();
        movieSessionWithCurrentShowTime.setShowTime(LocalDateTime.now());
        movieSessionWithCurrentShowTime.setCinemaHall(cinemaHallWithImax);
        movieSessionWithCurrentShowTime.setMovie(movieHatiko);

        MovieSession movieSessionWithPlusMonthShowTime = new MovieSession();
        movieSessionWithPlusMonthShowTime.setShowTime(LocalDateTime.now().plusMonths(1));
        movieSessionWithPlusMonthShowTime.setCinemaHall(basicCinemaHall);
        movieSessionWithPlusMonthShowTime.setMovie(movieIt);

        MovieSession movieSessionWithPlus5MonthsShowTime = new MovieSession();
        movieSessionWithPlus5MonthsShowTime.setShowTime(LocalDateTime.now().plusMonths(5));
        movieSessionWithPlus5MonthsShowTime.setCinemaHall(cinemaHallWith3D);
        movieSessionWithPlus5MonthsShowTime.setMovie(movieIt2);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSessionWithCurrentShowTime);
        movieSessionService.add(movieSessionWithPlusMonthShowTime);
        movieSessionService.add(movieSessionWithPlus5MonthsShowTime);
        movieSessionService.get(2L);
        movieSessionService.findAvailableSessions(movieHatiko.getId(), LocalDate.now());
    }
}
